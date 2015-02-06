package com.transformersmod.block;

import com.transformersmod.TransformersMod;
import com.transformersmod.tileentity.TileEntityCrystal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEnergonCrystal extends BlockBasic implements ITileEntityProvider
{
	public BlockEnergonCrystal()
	{
		super(Material.glass);
		this.setCreativeTab(TransformersMod.transformersTab);
	}

	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityCrystal();
	}
}