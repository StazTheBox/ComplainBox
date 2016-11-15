package stazthebox.sponge.complainBox.command.commands;

import lombok.val;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import stazthebox.sponge.api.CollectionMapUtils;
import stazthebox.sponge.complainBox.ComplainBox;
import stazthebox.sponge.complainBox.TextTemplates;
import stazthebox.sponge.complainBox.command.StazCommand;

/**
 * Created by Staz on 11/15/2016.
 * <p>
 * // /complaints clear [player]
 */
public class CommandClear extends StazCommand {

    // /complaints clear [player]

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        val optional = args.getOne(Text.of("player"));
        if (optional.isPresent()) {
            Player player = (Player) optional.get();
            val edits = ComplainBox.getHandler().clearComplaints(player);
            src.sendMessage(
                    TextTemplates.NUMBER_OF_COMPLAINTS_CLEARED.apply(CollectionMapUtils.kvMap(
                            "number", String.valueOf(edits),
                            "user", player.getName()
                    )).build()
            );
        } else {
            val edits = ComplainBox.getHandler().clear();
            src.sendMessage(
                    TextTemplates.NUMBER_OF_COMPLAINTS_CLEARED_ALL.apply(CollectionMapUtils.kvMap(
                            "number", String.valueOf(edits)
                    )).build()
            );
        }
        return CommandResult.success();
    }

    @Override
    public Object executeReturnTextAlwaysSucceed(CommandSource src, CommandContext args) {
        val optional = args.getOne(Text.of("player"));
        if (optional.isPresent()) {
            Player player = (Player) optional.get();
            val edits = ComplainBox.getHandler().clearComplaints(player);
            return TextTemplates.NUMBER_OF_COMPLAINTS_CLEARED.apply(CollectionMapUtils.kvMap(
                    "number", String.valueOf(edits),
                    "user", player.getName()
            ));
        } else {
            val edits = ComplainBox.getHandler().clear();
            return TextTemplates.NUMBER_OF_COMPLAINTS_CLEARED_ALL.apply(CollectionMapUtils.kvMap(
                    "number", String.valueOf(edits)
            ));
        }
    }

    @Override
    public CommandSpec.Builder modifyBuilder(CommandSpec.Builder builder) {
        return builder.permission("stazthebox.complainBox.complain.clear").
                description(Text.of("Clears the complaints of a player, or all if no player is selected.")).
                arguments(GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.user(Text.of("player")))));
    }
}
