package net.immortaldevs.colorizer;

import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfigCsvTest {
    @BeforeAll
    static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void deserializeStoresColorsByWorldAndPosition() {
        Config.deserialize("World;1;2;3;red\nWorld;4;5;6;blue\n");

        assertEquals(BlockColor.RED, Config.getColor("World", new BlockPos(1, 2, 3)));
        assertEquals(BlockColor.BLUE, Config.getColor("World", new BlockPos(4, 5, 6)));

        assertNull(Config.getColor("World", new BlockPos(9, 9, 9)));
        assertNull(Config.getColor("OtherWorld", new BlockPos(1, 2, 3)));
    }

    @Test
    void serializeIsTheInverseOfDeserialize() {
        String csv = "World;1;2;3;red\nServerIP;-50;70;500;yellow\n";
        Config.deserialize(csv);

        assertEquals(lines(csv), lines(Config.serialize()));
    }

    @Test
    void malformedLinesAreSkipped() {
        Config.deserialize("World;1;2;3;red\ngarbage;too;few\n\n");

        assertEquals(BlockColor.RED, Config.getColor("World", new BlockPos(1, 2, 3)));
        assertEquals(Set.of("World;1;2;3;red"), lines(Config.serialize()));
    }

    private static Set<String> lines(String csv) {
        return Arrays.stream(csv.split("\n"))
                .filter(l -> !l.isEmpty())
                .collect(Collectors.toSet());
    }
}
