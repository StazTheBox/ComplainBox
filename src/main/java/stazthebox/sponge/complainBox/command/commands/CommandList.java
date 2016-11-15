package stazthebox.sponge.complainBox.command.commands;

import lombok.val;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import stazthebox.sponge.complainBox.ComplainBox;
import stazthebox.sponge.complainBox.Complaint;

import java.util.*;

import static stazthebox.sponge.api.CollectionMapUtils.collection;
import static stazthebox.sponge.api.CollectionMapUtils.kvMap;
import static stazthebox.sponge.api.StringUtils.epochToHuman;
import static stazthebox.sponge.complainBox.TextTemplates.*;

/**
 * Created by Staz on 11/9/2016.
 */
public class CommandList implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<User> optional = args.getOne(Text.of("user"));
        Collection<Text> texts;
        if (optional.isPresent())
            texts = processResultUser(optional.get());
        else
            texts = processResultAll();

        src.sendMessages(texts);

        return CommandResult.success();
    }

    private Set<Text> processResultAll() {
        val list = ComplainBox.getHandler().getMap();
        val service = Sponge.getServiceManager().getRegistration(UserStorageService.class).get().getProvider();
        val response = new HashSet<Text>();

        for (UUID uuid : list.keySet()) {
            val num = list.get(uuid).size();
            val userName = service.get(uuid).get().getName();
            if (num == 1)
                response.add(OVERVIEW_SINGULAR.apply(kvMap("user", userName, "num", String.valueOf(num))).build());
            else
                response.add(OVERVIEW_PLURAL.apply(kvMap("user", userName, "num", String.valueOf(num))).build());
        }

        return response;
    }

    private Collection<Text> processResultUser(User user) {
        // Let's get the players complaints!
        val optionalList = ComplainBox.getHandler().getOptionalComplaints(user);
        if (optionalList.isPresent()) {
            val list = optionalList.get();
            val response = new HashSet<Text>();
            response.add(COMPLAINT_HEADER.apply(kvMap("user", user.getName())).build());
            for (Complaint complaint : list) {
                response.add(
                        COMPLAINT.apply(kvMap("complaint", complaint.getComplaint(),
                                "time", epochToHuman(complaint.getTime()))).build());
            }
            return response;

        } else // What? No Complaints? Good for you! Let's be nice and tell the person asking that you're nice n' clean!
            return collection(NO_COMPLAINT.apply(kvMap("user", user.getName())).build());
    }

}
