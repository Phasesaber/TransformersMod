package com.transformersmod.item;

import com.transformersmod.TransformersMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMetaBasic extends Item
{
	@SideOnly(Side.CLIENT)
	private final String[] itemNames;
	
	public ItemMetaBasic(String... itemNames)
	{
		super();
		this.itemNames = itemNames;
		this.setCreativeTab(TransformersMod.transformersTab);
		this.setHasSubtypes(true);
	}
	
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
	{
		for (int i = 0; i < itemNames.length; ++i)
		{
			subItems.add(new ItemStack(this, 1, i));
		}
	}
	
	public String getItemStackDisplayName(ItemStack stack)
	{
		return StatCollector.translateToLocal(itemNames[stack.getItemDamage() > itemNames.length ? itemNames.length : stack.getItemDamage()]);
	}
}