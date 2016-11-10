package stazthebox.sponge.api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Staz on 11/9/2016.
 */

@Data
public class SimpleConfig {

    private final Path configDir;
    private final String configName;

    private final Path configDest;
    private final Object plugin;

    @Getter
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    @Getter
    @Setter
    private CommentedConfigurationNode root;

    public SimpleConfig(Object plugin, Path configDir, String configName) {
        this.plugin = plugin;
        this.configDir = configDir;
        this.configName = configName;
        this.configDest = configDir.resolve(configName);

        this.loader = HoconConfigurationLoader.builder().setPath(configDest).build();
    }

    public void setupDirectories() throws IOException {
        if (Files.notExists(configDest))
            overwriteFile();
    }

    public boolean overwriteFile() throws IOException {
        val optional = Sponge.getAssetManager().getAsset(plugin, configName);
        if (optional.isPresent()) {
            val asset = optional.get();
            asset.copyToFile(configDest);
            return true;
        } else {
            root = loader.createEmptyNode();
            return false;
        }
    }

    public CommentedConfigurationNode load() throws IOException {
        setupDirectories();
        root = loader.load();
        return root;
    }

    public void save() throws IOException {
        loader.save(root);
    }
}
