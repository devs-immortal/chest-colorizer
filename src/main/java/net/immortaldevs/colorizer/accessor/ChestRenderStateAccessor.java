package net.immortaldevs.colorizer.accessor;

import net.minecraft.util.Identifier;

public interface ChestRenderStateAccessor {
    Identifier getDimension();

    void setDimension(Identifier dimensionName);
}
