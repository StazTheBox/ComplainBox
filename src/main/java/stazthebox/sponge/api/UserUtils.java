package stazthebox.sponge.api;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Staz on 11/9/2016.
 */
public class UserUtils {

    public static Optional<User> toUser(UUID uuid) {
        return Sponge.getServiceManager().provide(UserStorageService.class).get().get(uuid);
    }

    public static String toName(UUID uuid) {
        return toUser(uuid).get().getName();
    }

    public static UserStorageService getUserService() {
        return Sponge.getServiceManager().provide(UserStorageService.class).get();
    }
}
