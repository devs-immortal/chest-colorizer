package net.immortaldevs.colorizer;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.immortaldevs.colorizer.network.ClearColorPayload;
import net.immortaldevs.colorizer.network.UpdateColorPayload;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ColorizerModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        PayloadTypeRegistry.playS2C().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ClearColorPayload.ID, ClearColorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ClearColorPayload.ID, ClearColorPayload.CODEC);

        Config.load();

        ServerPlayNetworking.registerGlobalReceiver(UpdateColorPayload.ID, this::handleUpdatePayload);
        ServerPlayNetworking.registerGlobalReceiver(ClearColorPayload.ID, this::handleClearPayload);
    }

    private void handleUpdatePayload(UpdateColorPayload payload, ServerPlayNetworking.Context context) {
        Config.setColor(payload.dimension().getIdAsString(), payload.pos(), payload.color());
        broadcastPayload(payload, context);
    }

    private void handleClearPayload(ClearColorPayload payload, ServerPlayNetworking.Context context) {
        clearStoredColor(payload.dimension().getIdAsString(), payload.pos(), context);
        broadcastPayload(payload, context);
    }

    private <T extends CustomPayload> void broadcastPayload(T payload, ServerPlayNetworking.Context context) {
        var sender = context.player();
        for (ServerPlayerEntity player : PlayerLookup.world(sender.getEntityWorld())) {
            if (player == sender) continue;
            ServerPlayNetworking.send(player, payload);
        }
    }

    private void clearStoredColor(String dimension, BlockPos pos, ServerPlayNetworking.Context context) {
        BlockState state = context.player().getEntityWorld().getBlockState(pos);

        if (state.getBlock() instanceof ChestBlock) {
            Direction chestDirection = state.get(ChestBlock.FACING);
            ChestType chestType = state.get(ChestBlock.CHEST_TYPE);

            Config.removeColor(dimension, pos);

            if (chestType == ChestType.LEFT) {
                BlockPos right = pos.offset(chestDirection.rotateYClockwise());
                Config.removeColor(dimension, right);
            } else if (chestType == ChestType.RIGHT) {
                BlockPos left = pos.offset(chestDirection.rotateYCounterclockwise());
                Config.removeColor(dimension, left);
            }
            return;
        }

        Config.removeColor(dimension, pos);
    }
}
