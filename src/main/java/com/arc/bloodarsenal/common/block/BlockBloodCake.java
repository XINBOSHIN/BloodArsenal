package com.arc.bloodarsenal.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdibleBlock;
import squeek.applecore.api.food.ItemFoodProxy;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;

import com.arc.bloodarsenal.common.BloodArsenalConfig;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Optional.Interface(iface = "squeek.applecore.api.food.IEdibleBlock", modid = "AppleCore")
public class BlockBloodCake extends Block implements IEdibleBlock {

    private boolean isEdibleAtMaxHunger = false;

    @SideOnly(Side.CLIENT)
    private IIcon cakeTop;

    @SideOnly(Side.CLIENT)
    private IIcon cakeBottom;

    public BlockBloodCake() {
        super(Material.cake);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (float) (1 + l) / 12.0F;
        this.setBlockBounds(f1, 0.0F, f, 1.0F - f, 0.5F, 1.0F - f);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float f = 0.0625F;
        float f1 = 0.5F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (float) (1 + l) / 12.0F;
        float f2 = 0.5F;
        return AxisAlignedBB.getBoundingBox(
                (double) ((float) x + f1),
                (double) y,
                (double) ((float) z + f),
                (double) ((float) (x + 1) - f),
                (double) ((float) y + f2 - f),
                (double) ((float) (z + 1) - f));
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        int l = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        float f1 = (float) (1 + l) / 12.0F;
        float f2 = 0.5F;
        return AxisAlignedBB.getBoundingBox(
                (double) ((float) x + f1),
                (double) y,
                (double) ((float) z + f),
                (double) ((float) (x + 1) - f),
                (double) ((float) y + f2),
                (double) ((float) (z + 1) - f));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int x) {
        return par1 == 1 ? cakeTop : (par1 == 0 ? cakeBottom : (x > 0 && par1 == 4 ? null : blockIcon));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon("BloodArsenal:blood_cake_side");
        cakeTop = iconRegister.registerIcon("BloodArsenal:blood_cake_top");
        cakeBottom = iconRegister.registerIcon("BloodArsenal:blood_cake_bottom");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
            float hitY, float hitZ) {
        eatCake(player);
        return true;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        eatCake(player);
    }

    public void eatCake(EntityPlayer player) {
        String playerEating = player.getCommandSenderName();

        if (player.canEat(isEdibleAtMaxHunger)) {
            if (Loader.isModLoaded("AppleCore")) {
                onEatenCompatibility(new ItemStack(this), player);
            } else {
                player.getFoodStats().addStats(2, 1.5F);
            }
            SoulNetworkHandler.syphonFromNetwork(playerEating, 200);

            if (BloodArsenalConfig.cakeIsLie) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.lie")));
            } else {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.eating")));
            }
        } else if (player.canEat(true)) {
            if (BloodArsenalConfig.cakeIsLie) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.lie")));
            } else {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cake.tooFull")));
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!this.canBlockStay(world, x, y, z)) {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z).getMaterial().isSolid();
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public Item getItem(World world, int x, int y, int z) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return new ItemStack(this);
    }

    // === AppleCore methods ===

    @Override
    public void setEdibleAtMaxHunger(boolean edibleAtMaxHunger) {
        isEdibleAtMaxHunger = edibleAtMaxHunger;
    }

    @Override
    public FoodValues getFoodValues(ItemStack itemStack) {
        return new FoodValues(2, 1.5f);
    }

    @Optional.Method(modid = "AppleCore")
    public void onEatenCompatibility(ItemStack itemStack, EntityPlayer player) {
        player.getFoodStats().func_151686_a(new ItemFoodProxy(this), itemStack);
    }
}
