package com.arc.bloodarsenal.common.items.armor;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.arc.bloodarsenal.common.BloodArsenal;
import com.arc.bloodarsenal.common.items.ModItems;

public class GlassArmor extends ItemArmor {

    public GlassArmor(int armorType) {
        super(BloodArsenal.glassArmor, 1, armorType);
        setMaxDamage(42); // The answer to everything in the universe
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(StatCollector.translateToLocal("tooltip.armor.glassArmor"));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (this != ModItems.glass_leggings) {
            return "BloodArsenal:models/armor/glass_layer_1.png";
        } else {
            return "BloodArsenal:models/armor/glass_layer_2.png";
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }
}
