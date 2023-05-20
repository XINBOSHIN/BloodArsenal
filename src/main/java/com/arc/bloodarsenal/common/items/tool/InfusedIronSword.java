package com.arc.bloodarsenal.common.items.tool;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergyItems;

import com.arc.bloodarsenal.common.BloodArsenal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class InfusedIronSword extends ItemSword implements IBindable {

    private int energyUsed;

    public InfusedIronSword() {
        super(BloodArsenal.infusedIron);
        setMaxStackSize(1);
        setUnlocalizedName("blood_infused_sword_iron");
        setTextureName("BloodArsenal:blood_infused_sword_iron");
        setCreativeTab(BloodArsenal.BA_TAB);
        setFull3D();
        setEnergyUsed(75);
    }

    public void setEnergyUsed(int par1) {
        energyUsed = par1;
    }

    public int getEnergyUsed() {
        return energyUsed;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (!(par1ItemStack.stackTagCompound == null)) {
            if (!par1ItemStack.stackTagCompound.getString("ownerName").equals("")) {
                par3List.add("Current owner: " + par1ItemStack.stackTagCompound.getString("ownerName"));
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase,
            EntityLivingBase par3EntityLivingBase) {
        if (par3EntityLivingBase instanceof EntityPlayer) {
            EnergyItems.checkAndSetItemOwner(par1ItemStack, (EntityPlayer) par3EntityLivingBase);

            if (!EnergyItems.syphonBatteries(par1ItemStack, (EntityPlayer) par3EntityLivingBase, getEnergyUsed())) {}
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        EnergyItems.checkAndSetItemOwner(par1ItemStack, par3EntityPlayer);

        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.uncommon;
    }
}
