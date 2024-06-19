package net.immortaldevs.colorizer;

import net.fabricmc.api.ClientModInitializer;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ColorizerMod implements ClientModInitializer {
	public static final String MOD_ID = "colorizer";
	public static final Block BARREL_BLOCK = new ColorizedBarrelBlock(Blocks.BARREL.getSettings());

	@Override
	public void onInitializeClient() {
		Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "barrel"), BARREL_BLOCK);
		Config.load();
	}
}
