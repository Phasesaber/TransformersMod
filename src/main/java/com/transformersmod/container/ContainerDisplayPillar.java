package com.transformersmod.container;

import com.transformersmod.tileentity.TileEntityDisplayPillar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerDisplayPillar extends ContainerBasic
{
	private TileEntityDisplayPillar pillar;
    
    public ContainerDisplayPillar(InventoryPlayer inventory, TileEntityDisplayPillar tileEntity)
    {
    	this.pillar = tileEntity;

    	this.addPlayerInventory(inventory, 0);
    }
    
    public boolean canInteractWith(EntityPlayer player)
    {
    	return true;
    }
    
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;

        return itemstack;
    }
}