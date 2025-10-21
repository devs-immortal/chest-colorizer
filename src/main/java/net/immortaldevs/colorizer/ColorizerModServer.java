package net.immortaldevs.colorizer;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.immortaldevs.colorizer.network.ClearColorPayload;
import net.immortaldevs.colorizer.network.UpdateColorPayload;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public class ColorizerModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        PayloadTypeRegistry.playS2C().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ClearColorPayload.ID, ClearColorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ClearColorPayload.ID, ClearColorPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(UpdateColorPayload.ID, this::broadcastPayload);
        ServerPlayNetworking.registerGlobalReceiver(ClearColorPayload.ID, this::broadcastPayload);
    }

    private <T extends CustomPayload> void broadcastPayload(T payload, ServerPlayNetworking.Context context) {
        var sender = context.player();
        for (ServerPlayerEntity player : PlayerLookup.world(sender.getWorld())) {
            if (player == sender) continue;
            ServerPlayNetworking.send(player, payload);
        }
    }
}
