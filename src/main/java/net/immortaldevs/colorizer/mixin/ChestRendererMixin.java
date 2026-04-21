package net.immortaldevs.colorizer.mixin;

import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static net.immortaldevs.colorizer.ColorManager.getColorizedTextureId;

@Mixin(ChestRenderer.class)
public class ChestRendererMixin {
    @ModifyVariable(
            method = "submit(Lnet/minecraft/client/renderer/blockentity/state/ChestRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(
                    value = "STORE",
                    ordinal = 0
            ),
            name = "spriteId"
    )
    private SpriteId modifySpriteIdentifier(SpriteId spriteId, ChestRenderState state) {
        if (state.material != ChestRenderState.ChestMaterialType.REGULAR) {
            return spriteId;
        }

        SpriteId identifier = getColorizedTextureId(state);
        if (identifier == null) {
            return spriteId;
        }

        return identifier;
    }
}