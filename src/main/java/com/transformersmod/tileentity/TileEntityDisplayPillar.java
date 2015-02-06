package com.transformersmod.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDisplayPillar extends TileEntity
{
	private ItemStack displayItem;

    public void setDisplayItem(ItemStack item, boolean sync)
	{
		if(item != this.displayItem)
		{
			if(sync)
			{
				sync();
			}

			this.displayItem = ItemStack.copyItemStack(item);
		}
	}

	private void sync() 
	{
		worldObj.markBlockForUpdate(getPos());
	}

	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		NBTTagCompound itemTag = tagCompound.getCompoundTag("Item");

		this.displayItem = ItemStack.loadItemStackFromNBT(itemTag);
	}

	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		NBTTagCompound itemTag = new NBTTagCompound();

		if(displayItem != null)
		{
			displayItem.writeToNBT(itemTag);
		}

		tagCompound.setTag("Item", itemTag);
	}

	public ItemStack getDisplayItem()
	{
		return displayItem;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);

		return new S35PacketUpdateTileEntity(getPos(), 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager netManager, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}
}