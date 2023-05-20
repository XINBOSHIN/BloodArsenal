package com.arc.bloodarsenal.common.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionBloodArsenal extends Potion {

    private static final ResourceLocation resourceLocation = new ResourceLocation(
            "bloodarsenal",
            "textures/misc/potions.png");

    public PotionBloodArsenal(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public Potion setIconIndex(int par1, int par2) {
        super.setIconIndex(par1, par2);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
        return super.getStatusIconIndex();
    }
}
