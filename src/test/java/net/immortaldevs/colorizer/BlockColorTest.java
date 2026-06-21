package net.immortaldevs.colorizer;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.DyeColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockColorTest {
    @BeforeAll
    static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void fromNameResolvesKnownColors() {
        assertEquals(BlockColor.RED, BlockColor.fromName("red"));
        assertEquals(BlockColor.DEFAULT, BlockColor.fromName("default"));
    }

    @Test
    void fromNameReturnsNullForUnknown() {
        assertNull(BlockColor.fromName("unknown"));
    }

    @Test
    void everyDyeColorMapsToABlockColor() {
        for (DyeColor dye : DyeColor.values()) {
            BlockColor color = BlockColor.fromDyeColor(dye);
            assertNotNull(color, "no BlockColor for " + dye);
            assertEquals(dye.getSerializedName(), color.getSerializedName(), "name mismatch for " + dye);
        }
    }

    @Test
    void fromNameRoundTripsEveryColor() {
        for (BlockColor color : BlockColor.values()) {
            assertEquals(color, BlockColor.fromName(color.getSerializedName()));
        }
    }
}
