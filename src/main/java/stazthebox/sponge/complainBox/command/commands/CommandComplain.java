package stazthebox.sponge.complainBox.command.commands;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import stazthebox.sponge.api.CollectionMapUtils;
import stazthebox.sponge.complainBox.ComplainBox;
import stazthebox.sponge.complainBox.TextTemplates;
import stazthebox.sponge.complainBox.command.StazCommand;

/**
 * Created by Staz on 11/9/2016.
 */

/**
 * /complain <user> <complaint>
 */
public class CommandComplain extends StazCommand {

    @Override
    public Object executeReturnTextAlwaysSucceed(CommandSource src, CommandContext args) {
        User player = args.<User>getOne(Text.of("user")).get();
        String complaint = args.<String>getOne(Text.of("complaint")).get();
        ComplainBox.getHandler().addComplaint(player, complaint);
        return TextTemplates.COMPLAINT_SUCCSESSFULL.apply(CollectionMapUtils.kvMap(
                "user", player.getName(),
                "number", String.valueOf(ComplainBox.getHandler().getComplaints(player).size())));
    }

    @Override
    public CommandSpec.Builder modifyBuilder(CommandSpec.Builder builder) {
        return builder.description(Text.of("Complain about a player")).
                permission("complainbox.complain").
                arguments(
                        GenericArguments.onlyOne(GenericArguments.user(Text.of("user"))),
                        GenericArguments.remainingJoinedStrings(Text.of("complaint")));
    }
}
