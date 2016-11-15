package stazthebox.sponge.complainBox;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

import java.util.*;

/**
 * Created by Staz on 11/9/2016.
 */


// uuid complaint:time complaint:time complaint:time complaint:time

@NoArgsConstructor
public class ComplaintHandler {

    @Getter
    private final Map<UUID, Set<Complaint>> map = new HashMap<>();

    public void addComplaint(User target, String complaint) {
        getComplaints(target).add(new Complaint(complaint, System.currentTimeMillis()));
    }

    public boolean removeComplaint(User target, Complaint complaint) {
        val optionalList = getOptionalComplaints(target); // Don't make a new set if there aren't any
        if (optionalList.isPresent()) { // Well are there any complaints?
            val set = optionalList.get(); // Get them!
            val res = set.remove(complaint); // Remove them and store the result
            if (set.size() < 1) // Are there any complaints left? If not why are we storing and empty set?
                map.remove(target.getUniqueId());
            return res;
        }
        return false;
    }

    public void checkPlayer(Player target) {
        // TODO
    }

    public void processConfigurateNode(CommentedConfigurationNode node) {
        String uuidStr = node.getKey().toString();
        try {
            val uuid = UUID.fromString(uuidStr);

            val complaints = node.getList(TypeToken.of(Complaint.class));
            getComplaints(uuid).addAll(complaints);

        } catch (IllegalArgumentException | ObjectMappingException e) {
            ComplainBox.getLogger().warn("Improper configuration! Ignoring map for: " + uuidStr, e);
        }
    }

    public Set<Complaint> getComplaints(User user) {
        return getComplaints(user.getUniqueId());
    }

    public Optional<Set<Complaint>> getOptionalComplaints(UUID uuid) {
        return Optional.ofNullable(map.get(uuid));
    }

    public Optional<Set<Complaint>> getOptionalComplaints(User user) {
        return getOptionalComplaints(user.getUniqueId());
    }

    public Set<Complaint> getComplaints(UUID uuid) {
        val set = map.get(uuid);
        if (set == null) {
            map.put(uuid, new HashSet<>());
            return map.get(uuid);
        }
        return set;
    }

    @Override
    public String toString() {
        String fin = "";
        for (UUID uuid : map.keySet()) {
            String t = uuid.toString() + " ";
            for (Complaint c : map.get(uuid)) {
                t += c.toString() + " ";
            }
            fin += t + "\n";
        }
        return fin;
    }

    public int clearComplaints(User user) {
        return clearComplaints(user.getUniqueId());
    }

    public int clearComplaints(UUID uuid) {
        val set = getComplaints(uuid);
        val old = set.size();
        set.clear();
        return old;
    }

    public int clear() {
        int fin = 0;
        for (UUID uuid : map.keySet()) {
            fin += clearComplaints(uuid);
        }
        return fin;
    }
}
