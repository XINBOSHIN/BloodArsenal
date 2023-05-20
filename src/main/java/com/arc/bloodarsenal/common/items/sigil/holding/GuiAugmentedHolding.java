package com.arc.bloodarsenal.common.items.sigil.holding;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAugmentedHolding extends GuiContainer {

    public GuiAugmentedHolding(EntityPlayer player, InventoryAugmentedHolding inventoryHolding) {
        super(new ContainerAugmentedHolding(player, inventoryHolding));
        xSize = 176;
        ySize = 121;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        // the parameters for drawString are: string, x, y, color
        fontRendererObj.drawString("Sigil of Augmented Holding", 24, 4, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        // draw your Gui here, only thing you need to change is the path
        ResourceLocation test = new ResourceLocation("bloodarsenal", "textures/gui/SigilAugmentedHolding.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(test);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
