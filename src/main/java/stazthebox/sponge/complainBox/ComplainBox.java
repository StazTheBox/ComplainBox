package stazthebox.sponge.complainBox;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.val;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import stazthebox.sponge.api.SimpleConfig;
import stazthebox.sponge.complainBox.command.commands.CommandClear;
import stazthebox.sponge.complainBox.command.commands.CommandComplain;
import stazthebox.sponge.complainBox.command.commands.CommandList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Created by Staz on 11/9/2016.
 */

/**
 * preInit - logger, config
 * init - global event handlers
 * postInit - api requests
 * <p>
 * starting - register commands
 */

@Plugin(id = "complainbox", version = "0", description = "None!", authors = {"Staz The Box"}, name = "Complain Box")
public class ComplainBox {

    @Getter
    private static ComplainBox instance;


    // Logging Workaround for "static loggers"
    @Inject
    private void setLogger(Logger logger) {
        ComplainBox.logger = logger;
    }

    @Getter
    private static Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path confDir;

    @Getter
    private SimpleConfig config;

    @Getter
    private static CommentedConfigurationNode configNode;


    @Getter
    private static ComplaintHandler handler = new ComplaintHandler();

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        instance = this;
        config = new SimpleConfig(this, confDir, "config.conf");
        reload();
    }

    @Listener
    public void gameStarting(GameStartingServerEvent event) {

        buildCommands();
    }


    private void buildCommands() {
        val man = Sponge.getCommandManager();

        val complain = new CommandComplain();
        man.register(this, complain.getSpec(), "complain");


        // /complaints
        val list = CommandSpec.builder().
                description(Text.of("List all of the complaints of a player")).
                permission("complainbox.view").
                arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.user(Text.of("user"))))).
                executor(new CommandList()).
                build();

        val complaints = CommandSpec.builder().
                description(Text.of("List all of the complaints of a player")).
                child(list, "list").
                child(complain.getSpec(), "complain").
                child(new CommandClear().getSpec(), "clear").
                build();

        man.register(this, complaints, "complaints");
    }

    @Listener
    public void onStop(GameStoppingEvent event) {

        try {
            saveConfig();
        } catch (IOException e) {
            logger.info("Failed to save configuration!", e);
        }
    }

    public void reload() {
        loadConfig();
        configNode.getNode("complaints").getChildrenMap().values().forEach(handler::processConfigurateNode);

        logger.info("Loading successful. Loaded " +
                handler.getMap().size() + " users who were complained against.");
    }

    public void loadConfig() {
        try {
            configNode = config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() throws IOException {
        val node = configNode.getNode("complaints");
        for (UUID uuid : handler.getMap().keySet()) {
            val userNode = node.getNode(uuid.toString());
            val complaints = handler.getMap().get(uuid);
            for (Complaint complaint : complaints) {
                val complainNode = userNode.getAppendedNode();
                complainNode.getNode("complaint").setValue(complaint.getComplaint());
                complainNode.getNode("time").setValue(complaint.getTime());
            }
        }

        config.getLoader().save(configNode);
    }
}
