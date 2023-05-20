package com.arc.bloodarsenal.common.items.sigil.holding;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import WayofTime.alchemicalWizardry.common.items.EnergyItems;

import com.arc.bloodarsenal.common.BloodArsenal;

public class SigilAugmentedHolding extends EnergyItems {

    private static int invSize = 8;

    private static final String NBT_CURRENT_SIGIL = "CurrentSigil";

    public SigilAugmentedHolding() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (!(stack.getTagCompound() == null)) {
            ItemStack[] inv = getInternalInventory(stack);

            if (inv == null) {
                return this.itemIcon;
            }

            ItemStack item = getCurrentSigil(stack);

            if (item != null) {
                return item.getIconIndex();
            }
        }

        return this.itemIcon;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(StatCollector.translateToLocal("tooltip.sigil.augmentedHolding"));

        if (!(par1ItemStack.getTagCompound() == null)) {
            par3List.add(
                    StatCollector.translateToLocal("tooltip.owner.currentowner") + " "
                            + par1ItemStack.getTagCompound().getString("ownerName"));
            ItemStack[] inv = getInternalInventory(par1ItemStack);

            if (inv == null) {
                return;
            }

            int currentSlot = getCurrentItem(par1ItemStack);
            ItemStack item = inv[currentSlot];

            if (item != null) {
                par3List.add(StatCollector.translateToLocal("tooltip.item.currentitem") + " " + item.getDisplayName());
            }

            for (int i = 0; i <= invSize; i++) {
                if (inv[i] != null) {
                    par3List.add(
                            StatCollector.translateToLocal("tooltip.item.iteminslot") + " "
                                    + (i + 1)
                                    + ": "
                                    + inv[i].getDisplayName());
                }
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4,
            int par5, int par6, int par7, float par8, float par9, float par10) {
        if (checkAndSetItemOwner(par1ItemStack, par2EntityPlayer)) {
            int currentSlot = getCurrentItem(par1ItemStack);
            ItemStack[] inv = getInternalInventory(par1ItemStack);

            if (inv == null) {
                return false;
            }

            ItemStack itemUsed = inv[currentSlot];

            if (itemUsed == null) {
                return false;
            }

            boolean bool = itemUsed.getItem()
                    .onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);

            saveInventory(par1ItemStack, inv);

            return bool;
        }

        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (checkAndSetItemOwner(par1ItemStack, par3EntityPlayer)) {
            if (par3EntityPlayer.isSneaking()) {
                InventoryAugmentedHolding.setUUID(par1ItemStack);
                par3EntityPlayer.openGui(
                        BloodArsenal.instance,
                        0,
                        par3EntityPlayer.worldObj,
                        (int) par3EntityPlayer.posX,
                        (int) par3EntityPlayer.posY,
                        (int) par3EntityPlayer.posZ);
                return par1ItemStack;
            }

            int currentSlot = getCurrentItem(par1ItemStack);
            ItemStack[] inv = getInternalInventory(par1ItemStack);

            if (inv == null) {
                return par1ItemStack;
            }

            ItemStack itemUsed = inv[currentSlot];

            if (itemUsed == null) {
                return par1ItemStack;
            }

            itemUsed.getItem().onItemRightClick(itemUsed, par2World, par3EntityPlayer);
            saveInventory(par1ItemStack, inv);
        }

        return par1ItemStack;
    }

    public static int next(int mode) {
        int index = mode + 1;

        if (index >= invSize + 1) {
            index = 0;
        }

        return index;
    }

    public static int prev(int mode) {
        int index = mode - 1;

        if (index < 0) {
            index = invSize;
        }

        return index;
    }

    private static void initModeTag(ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.stackTagCompound = new NBTTagCompound();
            itemStack.stackTagCompound.setInteger(NBT_CURRENT_SIGIL, invSize);
        }
    }

    public static ItemStack getCurrentSigil(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof SigilAugmentedHolding) {
            ItemStack[] itemStacks = getInternalInventory(itemStack);
            int currentSlot = getCurrentItem(itemStack);
            if (itemStacks != null) {
                return itemStacks[currentSlot];
            }
        }

        return null;
    }

    public static int getCurrentItem(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() instanceof SigilAugmentedHolding) {
            initModeTag(itemStack);
            int currentSigil = itemStack.stackTagCompound.getInteger(NBT_CURRENT_SIGIL);
            currentSigil = MathHelper.clamp_int(currentSigil, 0, invSize);
            return currentSigil;
        }

        return 8;
    }

    public static ItemStack[] getInternalInventory(ItemStack itemStack) {
        initModeTag(itemStack);
        NBTTagCompound tagCompound = itemStack.getTagCompound();

        if (tagCompound == null) {
            return null;
        }

        ItemStack[] inv = new ItemStack[9];
        NBTTagList tagList = tagCompound.getTagList(InventoryAugmentedHolding.NBT_ITEMS, 10);

        if (tagList == null) {
            return null;
        }

        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            int slot = tag.getByte("Slot");

            if (slot >= 0 && slot <= invSize) {
                inv[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        return inv;
    }

    public void saveInventory(ItemStack itemStack, ItemStack[] inventory) {
        NBTTagCompound itemTag = itemStack.getTagCompound();

        if (itemTag == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i <= invSize; i++) {
            if (inventory[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        itemTag.setTag(InventoryAugmentedHolding.NBT_ITEMS, itemList);
    }

    public static void cycleSigil(ItemStack itemStack, int mode) {
        if (itemStack != null && itemStack.getItem() instanceof SigilAugmentedHolding) {
            initModeTag(itemStack);
            itemStack.stackTagCompound.setInteger(NBT_CURRENT_SIGIL, mode);
        }
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (!(par1ItemStack.getTagCompound() == null)) {
            this.tickInternalInventory(par1ItemStack, par2World, par3Entity, par4, par5);
        }
    }

    public void tickInternalInventory(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4,
            boolean par5) {
        ItemStack[] inv = getInternalInventory(par1ItemStack);

        if (inv == null) {
            return;
        }

        for (int i = 0; i < invSize; i++) {
            if (inv[i] == null) {
                continue;
            }

            inv[i].getItem().onUpdate(inv[i], par2World, par3Entity, par4, par5);
        }
    }
}
