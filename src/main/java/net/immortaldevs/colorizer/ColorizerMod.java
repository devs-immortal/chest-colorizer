package net.immortaldevs.colorizer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.state.property.EnumProperty;

public class ColorizerMod implements ClientModInitializer {
	public static final EnumProperty<ChestColor> COLOR = EnumProperty.of("color", ChestColor.class);

	@Override
	public void onInitializeClient() {
		Config.load();
	}
}
