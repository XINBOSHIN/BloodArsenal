package com.arc.bloodarsenal.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.arc.bloodarsenal.common.BloodArsenal;

public class ItemBloodCookie extends ItemFood {

    public ItemBloodCookie() {
        super(2, 0.3F, false);
        setUnlocalizedName("blood_cookie");
        setTextureName("BloodArsenal:blood_cookie");
        setAlwaysEdible();
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(StatCollector.translateToLocal("tooltip.itemba.blood_cookie"));
    }

    @Override
    public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 2));
        } else {
            super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        }
    }
}
