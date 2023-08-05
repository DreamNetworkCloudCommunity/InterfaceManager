package fr.joupi.im.utils.command.data;

import com.google.common.collect.Lists;
import fr.joupi.im.utils.command.annotation.Command;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

@Data
public class CommandData {

    private final Object object;
    private final Method method;

    private final Command command;
    private final List<SubCommandData> subCommands = Lists.newLinkedList();

}
