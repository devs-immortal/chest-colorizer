package net.immortaldevs.colorizer;

import net.fabricmc.api.ClientModInitializer;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ColorizerMod implements ClientModInitializer {
    public static final String MOD_ID = "colorizer";
    public static final Block BARREL_BLOCK = new ColorizedBarrelBlock(Blocks.BARREL.properties());

    @Override
    public void onInitializeClient() {
        ResourceKey<Block> barrelKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, "barrel"));
        Registry.register(BuiltInRegistries.BLOCK, barrelKey, BARREL_BLOCK);
        Config.load();
    }
}
