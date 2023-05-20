package com.arc.bloodarsenal.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.arc.bloodarsenal.common.items.sigil.holding.ContainerAugmentedHolding;
import com.arc.bloodarsenal.common.items.sigil.holding.GuiAugmentedHolding;
import com.arc.bloodarsenal.common.items.sigil.holding.InventoryAugmentedHolding;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case 0:
                return new ContainerAugmentedHolding(player, new InventoryAugmentedHolding(player.getHeldItem()));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case 0:
                return new GuiAugmentedHolding(player, new InventoryAugmentedHolding(player.getHeldItem()));
        }

        return null;
    }
}
