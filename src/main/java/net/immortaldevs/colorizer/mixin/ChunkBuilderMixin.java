package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.ChestColor;
import net.immortaldevs.colorizer.ColorizedBarrelBlock;
import net.immortaldevs.colorizer.ColorizedChest;
import net.immortaldevs.colorizer.ColorizerMod;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public class ChunkBuilderMixin {
    @Redirect(
            method = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask;render(FFFLnet/minecraft/client/render/chunk/BlockBufferBuilderStorage;)Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk$RebuildTask$RenderData;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkRendererRegion;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;")
    )
    public BlockState modifyBlockState(ChunkRendererRegion instance, BlockPos pos) {
        BlockState state = instance.getBlockState(pos);
        if (state.getBlock() == Blocks.BARREL) {
            ChestColor color = ColorizedChest.getColor(pos);
            return ColorizerMod.BARREL_BLOCK.getDefaultState()
                    .with(ColorizedBarrelBlock.COLOR, color)
                    .with(BarrelBlock.FACING, state.get(BarrelBlock.FACING))
                    .with(BarrelBlock.OPEN, state.get(BarrelBlock.OPEN));
        }
        return state;
    }
}