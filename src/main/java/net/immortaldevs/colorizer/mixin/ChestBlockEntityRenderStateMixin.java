package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.accessor.ChestRenderStateAccessor;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChestBlockEntityRenderState.class)
public class ChestBlockEntityRenderStateMixin implements ChestRenderStateAccessor {
    @Unique
    private Identifier dimensionName;

    public Identifier getDimension() {
        return this.dimensionName;
    }

    public void setDimension(Identifier dimensionName) {
        this.dimensionName = dimensionName;
    }
}