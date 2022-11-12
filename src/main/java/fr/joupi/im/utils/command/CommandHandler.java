package fr.joupi.im.utils.command;

import com.google.common.collect.Lists;
import fr.joupi.im.utils.command.Data.CommandData;
import fr.joupi.im.utils.command.Data.SubCommandData;
import fr.joupi.im.utils.command.annotation.Command;
import fr.joupi.im.utils.command.annotation.SubCommand;
import fr.joupi.im.utils.command.convertor.IConvertor;
import lombok.Getter;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommandHandler {

    private CommandMap commandMap;

    private final String fallbackPrefix;

    private final List<IConvertor<?>> convertors = Lists.newArrayList();

    private final List<CustomCommand> customCommands = Lists.newArrayList();

    public CommandHandler(JavaPlugin plugin, String fallbackPrefix) {
        this.fallbackPrefix = fallbackPrefix;

        try {
            Field field = plugin.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            this.commandMap = (CommandMap) field.get(plugin.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void registerConverters(IConvertor<?>... converters) {
        Arrays.asList(converters).forEach(this::registerConverter);
    }

    public void registerConverter(IConvertor<?> converter) {
        if (converter == null)
            throw new IllegalArgumentException("The converter you attempted to pass is null");

        this.convertors.add(converter);
    }

    public IConvertor<?> getConvertor(Class<?> clazz) {
        for (IConvertor<?> convertor : this.convertors)
            if (convertor.getType().equals(clazz))
                return convertor;

        return null;
    }

    public void registerCommands(Object... objects) {
        Arrays.asList(objects).forEach(this::registerCommand);
    }

    public void registerCommand(Object object) {
        Method[] rawMethods = object.getClass().getMethods();
        List<Method> commandMethods = Arrays.stream(rawMethods).filter(method -> method.getAnnotation(Command.class) != null).collect(Collectors.toList());
        List<Method> subCommandMethods = Arrays.stream(rawMethods).filter(method -> method.getAnnotation(SubCommand.class) != null).collect(Collectors.toList());

        for (Method method : commandMethods) {
            Command command = method.getAnnotation(Command.class);
            CommandData commandData = new CommandData(object, method, command);
            CustomCommand customCommand = new CustomCommand(commandData, this);

            customCommands.add(customCommand);
            this.commandMap.register(fallbackPrefix, customCommand);
        }

        for (Method method : subCommandMethods) {
            SubCommand subCommand = method.getAnnotation(SubCommand.class);

            CustomCommand parentCommand = this.customCommands
                    .stream().filter(customCommand ->
                            customCommand.getCommandData().getCommand().name()
                                    .equalsIgnoreCase(subCommand.parent()) || Arrays.stream(customCommand.getCommandData().getCommand().aliases())
                                    .filter(alias -> subCommand.parent().equalsIgnoreCase(alias))
                                    .findFirst()
                                    .orElse(null) != null).findFirst().orElse(null);

            if (parentCommand == null) {
                System.out.println("Failed to find parent command " + subCommand.parent() + " for command " + subCommand.name());
                return;
            }

            parentCommand.getCommandData().getSubCommands().add(new SubCommandData(object, method, subCommand));
        }
    }

}
