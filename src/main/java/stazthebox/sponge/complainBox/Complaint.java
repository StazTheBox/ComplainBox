package stazthebox.sponge.complainBox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;


@AllArgsConstructor
@NoArgsConstructor
@ConfigSerializable
public class Complaint {

    @Setting
    @Getter
    private String complaint;

    @Setting
    @Getter
    private long time;
}
