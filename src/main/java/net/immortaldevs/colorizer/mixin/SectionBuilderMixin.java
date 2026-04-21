package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.BlockColor;
import net.immortaldevs.colorizer.ColorManager;
import net.immortaldevs.colorizer.ColorizerMod;
import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SectionCompiler.class)
public class SectionBuilderMixin {
    @Redirect(
            method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderSectionRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/RenderSectionRegion;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;")
    )
    public BlockState modifyBlockState(RenderSectionRegion instance, BlockPos pos) {
        BlockState state = instance.getBlockState(pos);
        if (state.getBlock() == Blocks.BARREL) {
            BlockColor color = ColorManager.getColor(pos);
            return ColorizerMod.BARREL_BLOCK.defaultBlockState()
                    .setValue(ColorizedBarrelBlock.COLOR, color)
                    .setValue(BarrelBlock.FACING, state.getValue(BarrelBlock.FACING))
                    .setValue(BarrelBlock.OPEN, state.getValue(BarrelBlock.OPEN));
        }
        return state;
    }
}