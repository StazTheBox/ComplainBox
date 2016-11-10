package stazthebox.sponge.complainBox;


import com.google.common.reflect.TypeToken;
import lombok.val;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.UUID;

public class ComplaintSerializer {

    public static Complaint toComplaint(ConfigurationNode node) throws ObjectMappingException {
        return node.getValue(TypeToken.of(Complaint.class));
    }

    public static ComplaintHandler toHandler(ConfigurationNode node) throws ObjectMappingException {
        ComplaintHandler handler = new ComplaintHandler();
        val map = node.getChildrenMap();
        for (Object obj : map.keySet()) {
            // Convert obj -> UUID
            String uuidStr = String.valueOf(obj);
            UUID uuid = UUID.fromString(uuidStr);

            val nnode = node.getNode(obj); // Get list of complaints node
            val complaints = nnode.getList(TypeToken.of(Complaint.class)); // Convert to List<Complaint>
            handler.getComplaints(uuid).addAll(complaints); // Add all to handler
        }
        return handler;
    }
}
