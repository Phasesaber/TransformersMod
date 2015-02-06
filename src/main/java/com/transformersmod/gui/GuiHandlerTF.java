package com.transformersmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerTF implements IGuiHandler
{
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		
//		switch (id)
//		{
//			case 0: return id == 0 &&  world.getBlock(x, y, z) == TFBlocks.displayPillar ? new ContainerDisplayPillar(player.inventory, (TileEntityDisplayPillar)tileEntity) : null;
//		}
		
		return null;
	}

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		
//		switch (id)
//		{
//			case 0: return id == 0 &&  world.getBlock(x, y, z) == TFBlocks.displayPillar ? new GuiDisplayPillar(player.inventory, (TileEntityDisplayPillar)tileEntity) : null;
//		}
		
		return null;
	}
}