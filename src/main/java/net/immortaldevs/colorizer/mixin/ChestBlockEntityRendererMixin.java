package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.accessor.ChestRenderStateAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.immortaldevs.colorizer.ColorManager.getColorizedTextureId;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin<T extends BlockEntity & LidOpenable> {
    @Inject(
            method = "updateRenderState(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/client/render/block/entity/state/ChestBlockEntityRenderState;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V",
            at = @At("TAIL")
    )
    private void storeDimensionInfo(T blockEntity, ChestBlockEntityRenderState renderState, float tickDelta, Vec3d cameraPos, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay, CallbackInfo ci) {
        World world = blockEntity.getWorld();
        if (world != null) {
            Identifier dimension = world.getRegistryKey().getValue();
            ((ChestRenderStateAccessor) renderState).setDimension(dimension);
        }
    }

    @ModifyVariable(
            method = "render(Lnet/minecraft/client/render/block/entity/state/ChestBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
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