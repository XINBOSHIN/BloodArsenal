package com.arc.bloodarsenal.common.items.orb;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.items.EnergyBattery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TransparentOrb extends EnergyBattery {

    IIcon icons[] = new IIcon[45];

    public TransparentOrb(int damage) {
        super(damage);
        orbLevel = 6;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean something) {
        list.add(StatCollector.translateToLocal("tooltip.energybattery.desc"));

        if (itemStack.getTagCompound() != null) {
            list.add(
                    StatCollector.translateToLocal("tooltip.owner.currentowner") + " "
                            + itemStack.getTagCompound().getString("ownerName"));
            list.add(
                    StatCollector.translateToLocal("tooltip.energybattery.currentLP") + " "
                            + this.getCurrentEssence(itemStack));
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if (itemStack.getTagCompound() == null) {
            return;
        }

        int maxEssence = SoulNetworkHandler
                .getMaximumForOrbTier(SoulNetworkHandler.getCurrentMaxOrb(SoulNetworkHandler.getOwnerName(itemStack)));
        int section = maxEssence / 44;
        int currentEssence = SoulNetworkHandler.getCurrentEssence(SoulNetworkHandler.getOwnerName(itemStack));

        if (currentEssence > 0) {
            int metaToBe = (currentEssence / section);
            itemStack.setItemDamage(metaToBe);
        } else {
            itemStack.setItemDamage(0);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        int i = stack.getItemDamage() + 1;

        if (i != stack.getItemDamage() && i < icons.length) {
            i = stack.getItemDamage() + 1;
            return icons[i - 1];
        } else if (i <= icons.length) {
            return icons[icons.length - 1];
        } else {
            return itemIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("BloodArsenal:orb/orb_0");
        icons[0] = iconRegister.registerIcon("BloodArsenal:orb/orb_0");

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon("BloodArsenal:orb/orb_" + i);
        }
    }
}
