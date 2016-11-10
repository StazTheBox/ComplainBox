package stazthebox.sponge.complainBox.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import stazthebox.sponge.complainBox.ComplainBox;

/**
 * Created by Staz on 11/9/2016.
 */

/**
 * /complain <user> <complaint>
 */
public class CommandComplain implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        User player = args.<User>getOne(Text.of("user")).get();
        String complaint = args.<String>getOne(Text.of("complaint")).get();
        ComplainBox.getInstance().getHandler().addComplaint(player, complaint);
        return CommandResult.success();
    }
}
