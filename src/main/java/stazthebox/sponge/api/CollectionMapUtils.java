package stazthebox.sponge.api;

import lombok.val;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Staz on 11/9/2016.
 */

public class CollectionMapUtils {

    public static <T> Collection<T> collection(T... objs) {
        val map = new HashSet<T>();
        for (T obj : objs) {
            map.add(obj);
        }
        return map;
    }

    public static <T> HashMap<T, T> kvMap(T... kvPairs) {
        HashMap<T, T> map = new HashMap<>();
        for (int i = 0; i < kvPairs.length; i += 2) {
            map.put(kvPairs[i], kvPairs[i + 1]);
        }
        return map;
    }

}
