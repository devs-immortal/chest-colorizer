package net.immortaldevs.colorizer.mixin;

import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static net.immortaldevs.colorizer.ColorManager.getColorizedTextureId;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin {
    @ModifyVariable(
            method = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;render(Lnet/minecraft/client/render/block/entity/state/ChestBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            ordinal = 0
    )
    private SpriteIdentifier modifySpriteIdentifier(SpriteIdentifier original, ChestBlockEntityRenderState chestRenderState) {
        if (chestRenderState.variant != ChestBlockEntityRenderState.Variant.REGULAR) {
            return original;
        }

        SpriteIdentifier identifier = getColorizedTextureId(chestRenderState);
        if (identifier == null) {
            return original;
        }

        return identifier;
    }
}