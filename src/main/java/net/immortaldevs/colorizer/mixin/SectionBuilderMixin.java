package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.BlockColor;
import net.immortaldevs.colorizer.ColorManager;
import net.immortaldevs.colorizer.ColorizerMod;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.chunk.SectionBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SectionBuilder.class)
public class SectionBuilderMixin {
    @Redirect(
            method = "build(Lnet/minecraft/util/math/ChunkSectionPos;Lnet/minecraft/client/render/chunk/ChunkRendererRegion;Lcom/mojang/blaze3d/systems/VertexSorter;Lnet/minecraft/client/render/chunk/BlockBufferAllocatorStorage;)Lnet/minecraft/client/render/chunk/SectionBuilder$RenderData;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkRendererRegion;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;")
    )
    public BlockState modifyBlockState(ChunkRendererRegion instance, BlockPos pos) {
        BlockState state = instance.getBlockState(pos);
        if (state.getBlock() == Blocks.BARREL) {
            RegistryEntry<DimensionType> dimension = instance.world.getDimensionEntry();
            BlockColor color = ColorManager.getColor(dimension, pos);
            return ColorizerMod.BARREL_BLOCK.getDefaultState()
                    .with(ColorizedBarrelBlock.COLOR, color)
                    .with(BarrelBlock.FACING, state.get(BarrelBlock.FACING))
                    .with(BarrelBlock.OPEN, state.get(BarrelBlock.OPEN));
        }
        return state;
    }
}