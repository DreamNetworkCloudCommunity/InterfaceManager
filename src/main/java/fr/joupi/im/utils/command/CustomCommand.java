package fr.joupi.im.utils.command;

import fr.joupi.im.utils.command.Data.CommandData;
import fr.joupi.im.utils.command.Data.SubCommandData;
import fr.joupi.im.utils.command.annotation.Optional;
import fr.joupi.im.utils.command.convertor.IConvertor;
import fr.joupi.im.utils.Utils;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public class CustomCommand extends Command {

    private final CommandData commandData;

    private final CommandHandler commandHandler;

    public CustomCommand(CommandData commandData, CommandHandler commandHandler) {
        super(commandData.getCommand().name());

        this.commandData = commandData;
        this.commandHandler = commandHandler;

        fr.joupi.im.utils.command.annotation.Command command = commandData.getCommand();

        if (command.aliases().length > 0)
            this.setAliases(Arrays.asList(command.aliases()));

        Arrays.stream(command.permission().split(",")).collect(Collectors.toList()).forEach(this::setPermission);
    }

    @SneakyThrows
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] arguments) {
        Object object;
        Method method;
        String[] args;
        String permission;

        SubCommandData subCommand = null;

        if (arguments.length >= 1 && this.commandData.getSubCommands().stream()
                .filter(subCommandData -> subCommandData.getDSubCommand().name().equalsIgnoreCase(arguments[0]) || Arrays.stream(arguments).filter(alias -> arguments[0].equalsIgnoreCase(alias)).findAny().orElse(null) != null)
                .findAny().orElse(null) != null) {


            subCommand = commandData.getSubCommands().stream().filter(subCommandData -> subCommandData.getDSubCommand().name().equalsIgnoreCase(arguments[0]) || Arrays.stream(subCommandData.getDSubCommand().aliases()).filter(alias -> arguments[0].equalsIgnoreCase(alias)).findAny().orElse(null) != null).findFirst().orElse(null);

            if (subCommand != null) {
                object = subCommand.getObject();
                method = subCommand.getMethod();
                permission = subCommand.getDSubCommand().permission();
                args = Arrays.copyOfRange(arguments, 1, arguments.length);

            } else {
                args = Arrays.copyOfRange(arguments, 1, arguments.length);
                object = commandData.getObject();
                method = commandData.getMethod();
                permission = commandData.getCommand().permission();
            }

        } else {
            args = arguments;
            object = commandData.getObject();
            method = commandData.getMethod();
            permission = commandData.getCommand().permission();
        }

        Parameter[] parameters = method.getParameters();

        if (!(commandSender instanceof Player) && parameters[0].getType().equals(Player.class)) {
            Utils.sendMessages(commandSender,"&cOnly players may execute this command.");
            return true;
        }

        for (String perm : permission.split(",")) {
            if (!perm.isEmpty() && !commandSender.hasPermission(perm)) {
                Utils.sendMessages(commandSender, "&cNo permission.");
                return true;
            }
        }

        if (parameters.length >= 1 && !parameters[0].getType().isArray()) {
            Parameter[] rangedCopy = Arrays.copyOfRange(parameters, 1, parameters.length);
            Object[] objects = new Object[rangedCopy.length];

            if (args.length < rangedCopy.length) {
                if (subCommand == null)
                    Utils.sendMessages(commandSender, "&cUsage: /" + this.getLabel() + " " + Arrays.stream(rangedCopy).map(parameter -> "<" + (parameter.isAnnotationPresent(fr.joupi.im.utils.command.annotation.Parameter.class) ? parameter.getAnnotation(fr.joupi.im.utils.command.annotation.Parameter.class).name() : "arg") + ">").collect(Collectors.joining(" ")));
                else
                    Utils.sendMessages(commandSender, "&cUsage: /" + this.getLabel() + " " + subCommand.getDSubCommand().name() + " " + Arrays.stream(rangedCopy).map(parameter -> "<" + (parameter.isAnnotationPresent(fr.joupi.im.utils.command.annotation.Parameter.class) ? parameter.getAnnotation(fr.joupi.im.utils.command.annotation.Parameter.class).name() : "arg") + ">").collect(Collectors.joining(" ")));
                return true;
            }

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < rangedCopy.length; i++) {
                Parameter parameter = rangedCopy[i];
                Object obj = null;

                IConvertor<?> converter = this.commandHandler.getConvertor(parameter.getType());

                if ((rangedCopy.length == i + 1) && rangedCopy[rangedCopy.length - 1].getType().equals(String.class) && args.length >= i && commandData.getCommand().appendStrings()) {
                    String[] appendArgs = Arrays.copyOfRange(args,i, args.length);
                    for (String append : appendArgs)
                        stringBuilder.append(append);
                }
                else {

                    if (converter == null)
                        throw new IllegalArgumentException("Unable to find converter for " + parameter.getType().getName());

                    if (parameter.isAnnotationPresent(Optional.class))
                        obj = converter.getFromString(commandSender, parameter.getAnnotation(Optional.class).value().replace("me", commandSender.getName()));
                    else
                        obj = converter.getFromString(commandSender, args[i]);
                }
                objects[i] = stringBuilder.toString().isEmpty() ? obj : stringBuilder.toString();
            }

            objects = ArrayUtils.add(objects, 0, parameters[0].getType().cast(commandSender));
            method.invoke(object, objects);
        } else if (parameters.length == 1 && parameters[0].getType().isArray())
            method.invoke(object, commandSender, args);
        else
            method.invoke(object, commandSender);
        return false;
    }
}