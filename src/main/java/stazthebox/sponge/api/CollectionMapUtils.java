package stazthebox.sponge.api;

import lombok.val;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Staz on 11/9/2016.
 */

public class CollectionMapUtils {

    @SafeVarargs
    public static <T> Collection<T> collection(T... objs) {
        val set = new HashSet<T>();
        Collections.addAll(set, objs);
        return set;
    }

    @SafeVarargs
    public static <T> HashMap<T, T> kvMap(T... kvPairs) {
        HashMap<T, T> map = new HashMap<>();
        for (int i = 0; i < kvPairs.length; i += 2) {
            map.put(kvPairs[i], kvPairs[i + 1]);
        }
        return map;
    }

}
