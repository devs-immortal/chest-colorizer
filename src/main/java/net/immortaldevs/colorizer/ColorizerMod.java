package net.immortaldevs.colorizer;

import net.fabricmc.api.ClientModInitializer;

public class ColorizerMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Config.load();
	}
}
