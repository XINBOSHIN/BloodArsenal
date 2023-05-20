package com.arc.bloodarsenal.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.arc.bloodarsenal.common.BloodArsenal;

public class ItemBloodOrange extends ItemFood {

    public ItemBloodOrange() {
        super(2, 0.2F, false);
        setUnlocalizedName("blood_orange");
        setTextureName("BloodArsenal:blood_orange");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(StatCollector.translateToLocal("tooltip.itemba.blood_orange"));
    }
}
