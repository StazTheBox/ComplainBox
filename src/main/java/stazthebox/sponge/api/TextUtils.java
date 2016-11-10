package stazthebox.sponge.api;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;

/**
 * Created by Staz on 11/9/2016.
 */
public class TextUtils {

    public static TextTemplate.Arg.Builder arg(String name, Object... objs) {
        TextTemplate.Arg.Builder temp = TextTemplate.arg(name);
        for (Object obj : objs) {
            if (obj instanceof TextColor)
                temp.color((TextColor) obj);
            else if (obj instanceof TextStyle)
                temp.style((TextStyle) obj);
            else if (obj instanceof Boolean)
                temp.optional((Boolean) obj);
            else if (obj instanceof TextFormat)
                temp.format((TextFormat) obj);
            else if (obj instanceof Text)
                temp.defaultValue((Text) obj);
        }
        return temp;
    }
}
