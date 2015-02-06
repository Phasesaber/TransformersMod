package com.transformersmod.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class TFItemRegistry
{
	public static ArrayList<Item> itemList = new ArrayList();
	
	
	public static void registerItem(Item item, String name, String modId)
	{
		String s = name.toLowerCase().replace(' ', '_').replace("'", "");
		itemList.add(item);
		item.setUnlocalizedName(s);
		GameRegistry.registerItem(item, s, modId);
	}
	
	public static void registerIngot(Item item, String name, String modId, String oreDictName)
	{
		registerItem(item, name, modId);
		OreDictionary.registerOre(oreDictName, item);
	}
}