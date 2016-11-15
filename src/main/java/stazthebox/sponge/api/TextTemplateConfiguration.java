package stazthebox.sponge.api;

import lombok.Getter;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextFormat;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Staz on 11/15/2016.
 * <p>
 * TODO
 */
public class TextTemplateConfiguration extends SimpleConfig {
    @Getter
    private Map<String, TextTemplate> templates;

    private TextTemplate prefix, suffix;
    private TextFormat defaultArg, defaultConst;

    public TextTemplateConfiguration(Object plugin, Path configDir) {
        super(plugin, configDir, "text.conf");
    }

    private TextTemplate build(String text) {
        ArrayList<Object> list = new ArrayList<>();

        /*
        0 - text
        1 - textInArg
        2 - textInVar
        3 - ignoreNext
        4 - ignoreNextInArg
        5 - ignoreNextInVar
         */
        int status = 0;
        String curPart = "";
        for (char t : text.toCharArray()) {
            if (status < 3) {
                if (t == '\\')
                    status += 3;
                else if (t == '{') {
                    list.add(curPart);
                    status = 1;
                    curPart = "";
                } else if (t == '}') {
                    list.add(TextTemplate.arg(curPart));
                    status = 0;
                    curPart = "";
                } else if (t == '[') {
                    list.add(curPart);
                    status = 2;
                    curPart = "";
                } else if (t == ']') {
                    list.add(getTemplate(curPart));
                    status = 0;
                    curPart = "";
                } else {
                    curPart += t;
                }
            } else
                curPart += t;
        }

        return TextTemplate.of(prefix, defaultConst, list, suffix);
    }

    public TextTemplate getTemplate(String name) {
        return build(getRoot().getNode((Object[]) name.split(".")).getString());
    }

    public Optional<TextTemplate> getOptionalTemplate(String name) {
        return Optional.ofNullable(getTemplate(name));
    }

}
