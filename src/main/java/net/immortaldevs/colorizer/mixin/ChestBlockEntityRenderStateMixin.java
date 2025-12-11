package net.immortaldevs.colorizer.mixin;

import net.immortaldevs.colorizer.accessor.ChestRenderStateAccessor;
import net.minecraft.client.render.block.entity.state.ChestBlockEntityRenderState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChestBlockEntityRenderState.class)
public class ChestBlockEntityRenderStateMixin implements ChestRenderStateAccessor {
    @Unique
    private RegistryEntry<DimensionType> dimensionName;

    public RegistryEntry<DimensionType> getDimension() {
        return this.dimensionName;
    }

    public void setDimension(RegistryEntry<DimensionType> dimensionName) {
        this.dimensionName = dimensionName;
    }
}