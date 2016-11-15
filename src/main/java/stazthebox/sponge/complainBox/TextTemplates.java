package stazthebox.sponge.complainBox;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyles;

import static org.spongepowered.api.text.TextTemplate.of;
import static org.spongepowered.api.text.format.TextColors.DARK_AQUA;
import static org.spongepowered.api.text.format.TextStyles.ITALIC;
import static stazthebox.sponge.api.TextUtils.arg;

/**
 * Created by Staz on 11/9/2016.
 */
public class TextTemplates {
    public static final Text PREFIX = Text.of(Text.builder("ComplainBox: ").style(ITALIC).color(DARK_AQUA).build(), "");
    public static final TextFormat
            DATA = TextFormat.of(TextColors.RED, TextStyles.BOLD),
            CONSTANT = TextFormat.of(TextColors.BLUE, TextStyles.NONE);

    public static final TextTemplate OVERVIEW_PLURAL = of(PREFIX,
            arg("user", DATA), CONSTANT, " has ",
            arg("num", DATA), CONSTANT, " complaints!"),

    OVERVIEW_SINGULAR = of(PREFIX,
            arg("user", DATA), CONSTANT, " has ", DATA, "one", CONSTANT, " complaint!"),

    COMPLAINT_HEADER = of(PREFIX, CONSTANT, "Complaints Against ", arg("user", DATA), CONSTANT, ":"),

    COMPLAINT = of(PREFIX, arg("complaint", DATA), CONSTANT, " Argued At: ",
            arg("time", DATA)),

    NO_COMPLAINT = of(PREFIX, CONSTANT, "There are currently no complaints against ", arg("user", DATA)),

    NUMBER_OF_COMPLAINTS_CLEARED = of(PREFIX, CONSTANT, "You have cleared ", arg("number", DATA), CONSTANT,
            " of complaints from user ", arg("user", DATA), CONSTANT, "."),

    NUMBER_OF_COMPLAINTS_CLEARED_ALL = of(PREFIX, CONSTANT, "You have cleared ", arg("number", DATA), CONSTANT,
            " complaints."),
            COMPLAINT_SUCCSESSFULL = of(PREFIX, CONSTANT, "Complaint Successfull. ",
                    arg("user", DATA), CONSTANT, " now has");
}
