package stazthebox.sponge.complainBox.command.commands;

import lombok.val;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import stazthebox.sponge.api.CollectionMapUtils;
import stazthebox.sponge.complainBox.command.StazCommand;

/**
 * Created by Staz on 11/15/2016.
 */
public class CommandComplainBox extends StazCommand {
    @Override
    public Object executeReturnTextAlwaysSucceed(CommandSource src, CommandContext args) {
        val choice = args.getOne(Text.of("choices"));
        val player = args.getOne(Text.of("player"));
        if (choice.get() == "enabled") {

        } else { // disabled

        }
        return null;
    }

    @Override
    public CommandSpec.Builder modifyBuilder(CommandSpec.Builder builder) {
        return builder.arguments(
                GenericArguments.onlyOne(GenericArguments.choices(Text.of("choices"),
                        CollectionMapUtils.kvMap("enable", "", "disable", ""))));
    }
}
