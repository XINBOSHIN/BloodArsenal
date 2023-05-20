package com.arc.bloodarsenal.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBloodStainedPackedIce extends Block {

    public BlockBloodStainedPackedIce() {
        super(Material.packedIce);
        slipperiness = 0.99F;
        setHardness(1.0F);
        setResistance(3.0F);
        setStepSound(soundTypeGlass);
    }

    public int quantityDropped(Random par1) {
        return 0;
    }
}
