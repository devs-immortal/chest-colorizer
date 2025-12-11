package net.immortaldevs.colorizer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.immortaldevs.colorizer.network.ClearColorPayload;
import net.immortaldevs.colorizer.network.UpdateColorPayload;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ColorizerMod implements ClientModInitializer {
    public static final String MOD_ID = "colorizer";
    public static final Block BARREL_BLOCK = new ColorizedBarrelBlock(Blocks.BARREL.getSettings());

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playS2C().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UpdateColorPayload.ID, UpdateColorPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ClearColorPayload.ID, ClearColorPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ClearColorPayload.ID, ClearColorPayload.CODEC);

        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "barrel"), BARREL_BLOCK);

        Config.load();

        ClientPlayNetworking.registerGlobalReceiver(UpdateColorPayload.ID, (payload, ctx) -> {
            World world = ctx.player().getEntityWorld();
            BlockPos pos = payload.pos();

            BlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() instanceof BarrelBlock) {
                blockState = blockState.with(BarrelBlock.OPEN, true);
                world.setBlockState(pos, blockState);

                ColorManager.updateColor(payload.dimension(), pos, payload.color());

                blockState = blockState.with(BarrelBlock.OPEN, false);
                world.setBlockState(pos, blockState);
                return;
            }

            ColorManager.updateColor(payload.dimension(), payload.pos(), payload.color());
        });
        ClientPlayNetworking.registerGlobalReceiver(ClearColorPayload.ID, (payload, ctx) -> {
            World world = ctx.player().getEntityWorld();
            RegistryEntry<DimensionType> dimension = payload.dimension();
            BlockPos pos = payload.pos();

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ChestBlockEntity) {
                BlockState state = blockEntity.getCachedState();
                ColorManager.clearColor(dimension, pos, state);
                return;
            }

            BlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() instanceof BarrelBlock) {
                blockState = blockState.with(BarrelBlock.OPEN, true);
                world.setBlockState(pos, blockState);

                ColorManager.clearColor(dimension, pos);

                blockState = blockState.with(BarrelBlock.OPEN, false);
                world.setBlockState(pos, blockState);
            }
        });
    }
}
