package com.transformersmod.block;

import com.transformersmod.TransformersMod;
import com.transformersmod.item.TFItems;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockEnergonCube extends BlockBasic //BlockIce
{
	public BlockEnergonCube() 
	{
		super(Material.glass);
		this.setCreativeTab(TransformersMod.transformersTab);
	}
	
	public int quantityDropped(Random random)
	{
		return random.nextInt(1) + 9;
	}
	
	public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_)
	{
		return TFItems.energonCrystalPiece;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
}
