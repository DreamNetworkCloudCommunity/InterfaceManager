package fr.joupi.im.utils.command.data;

import fr.joupi.im.utils.command.annotation.SubCommand;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public class SubCommandData {

    private final Object object;
    private final Method method;

    private final SubCommand dSubCommand;

}

