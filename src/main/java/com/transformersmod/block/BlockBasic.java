package com.transformersmod.block;

import com.transformersmod.TransformersMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockBasic extends Block
{
    public BlockBasic(Material material)
    {
        super(material);
        this.setCreativeTab(TransformersMod.transformersTab);
    }
    
    public Block setHarvestLvl(String tool, int level)
    {
    	this.setHarvestLevel(tool, level);
    	return this;
    }
    
    public Item getItemDropped(int p_149650_1_, Random rand, int p_149650_3_)
    {
        return Item.getItemFromBlock(this);
    }
}