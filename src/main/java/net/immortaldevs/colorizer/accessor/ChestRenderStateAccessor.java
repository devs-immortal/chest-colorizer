package net.immortaldevs.colorizer.accessor;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.dimension.DimensionType;

public interface ChestRenderStateAccessor {
    RegistryEntry<DimensionType> getDimension();

    void setDimension(RegistryEntry<DimensionType> dimensionName);
}
