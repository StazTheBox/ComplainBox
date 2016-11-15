package stazthebox.sponge.complainBox.command;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.Collection;

/**
 * Created by Staz on 11/15/2016.
 */
public abstract class StazCommand implements CommandExecutor {

    @Setter
    @Getter
    private CommandResult result;

    @SuppressWarnings("unchecked")
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Object obj = executeReturnTextAlwaysSucceed(src, args);

        if (obj instanceof Collection) {
            val set = (Collection) obj;
            val setO = set.iterator().next();
            if (setO instanceof Text.Builder) {
                set.forEach(o -> src.sendMessage(
                        ((Text.Builder) o).build()));
            } else if (setO instanceof Text)
                set.forEach(o -> src.sendMessage((Text) setO));

        } else if (obj instanceof Text.Builder)
            src.sendMessage(((Text.Builder) obj).build());
        else if (obj instanceof Text)
            src.sendMessage((Text) obj);

        return CommandResult.success();
    }

    public abstract Object executeReturnTextAlwaysSucceed(CommandSource src, CommandContext args);

    public CommandSpec getSpec() {
        return getBuilder().build();
    }

    public CommandSpec.Builder getBuilder() {
        return modifyBuilder(CommandSpec.builder()).executor(this);
    }

    public abstract CommandSpec.Builder modifyBuilder(CommandSpec.Builder builder);
}
