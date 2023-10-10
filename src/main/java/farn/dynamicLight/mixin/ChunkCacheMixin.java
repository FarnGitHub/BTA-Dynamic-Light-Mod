package farn.dynamicLight.mixin;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCache;
import org.spongepowered.asm.mixin.Shadow;
import farn.dynamicLight.util.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ChunkCache.class, remap = false)
public class ChunkCacheMixin {

    @Shadow
    World worldObj;

    public ChunkCache chunkCache = (ChunkCache)(Object)this;

    /**
     * @author AtomicStryker
     * @reason farnfarn02
     */
    @Overwrite
    public float getBrightness(int i, int j, int k, int l)
    {
        float lc = LightCache.cache.getLightValue(i, j, k);
        if(lc > l)
        {
            return lc;
        }

        int lightValue = chunkCache.getLightValue(i, j, k);
        float torchLight = PlayerTorchArray.getLightBrightness(worldObj, i, j, k);
        if(lightValue < torchLight)
        {
            int floorValue = (int)java.lang.Math.floor(torchLight);
            if(floorValue==15)
            {
                return worldObj.getWorldType().getBrightnessRamp()[15];
            }
            else
            {
                int ceilValue = (int)java.lang.Math.ceil(torchLight);
                float lerpValue = torchLight-floorValue;
                return (1.0f-lerpValue)*worldObj.getWorldType().getBrightnessRamp()[floorValue]+lerpValue*worldObj.getWorldType().getBrightnessRamp()[ceilValue];
            }
        }

        lc = worldObj.getWorldType().getBrightnessRamp()[lightValue];
        LightCache.cache.setLightValue(i, j, k, lc);
        return lc;
    }

    /**
     * @author AtomicStryker
     * @reason farnfarn02
     */
    @Overwrite
    public float getLightBrightness(int i, int j, int k)
    {
        float lc = LightCache.cache.getLightValue(i, j, k);
        if(lc >= 0)
        {
            return lc;
        }

        int lightValue = chunkCache.getLightValue(i, j, k);
        float torchLight = PlayerTorchArray.getLightBrightness(worldObj, i, j, k);
        if(lightValue < torchLight)
        {
            int floorValue = (int)java.lang.Math.floor(torchLight);
            if(floorValue==15)
            {
                return worldObj.getWorldType().getBrightnessRamp()[15];
            }
            else
            {
                int ceilValue = (int)java.lang.Math.ceil(torchLight);
                float lerpValue = torchLight-floorValue;
                return (1.0f-lerpValue)*worldObj.getWorldType().getBrightnessRamp()[floorValue]+lerpValue*worldObj.getWorldType().getBrightnessRamp()[ceilValue];
            }
        }

        lc = worldObj.getWorldType().getBrightnessRamp()[lightValue];
        LightCache.cache.setLightValue(i, j, k, lc);
        return lc;
    }

}
