package stazthebox.sponge.api;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Staz on 11/9/2016.
 */
public class StringUtils {

    public static String epochToHuman(long time) {
        return epochToHuman(time, "MMM d, yy h:mm a");
    }

    public static String epochToHuman(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }
}
