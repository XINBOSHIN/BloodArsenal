package com.arc.bloodarsenal.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.arc.bloodarsenal.common.BloodArsenal;

public class ItemJuiceAndCookies extends ItemFood {

    public ItemJuiceAndCookies() {
        super(6, 0.6F, false);
        setMaxStackSize(1);
        setUnlocalizedName("juice_and_cookies");
        setTextureName("BloodArsenal:juice_and_cookies");
        setAlwaysEdible();
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("Eat after losing");
        par3List.add("some blood");
    }

    @Override
    public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 4));
        } else {
            super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        }
    }
}
