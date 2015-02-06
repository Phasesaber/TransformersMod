package com.transformersmod.transformer;

import net.minecraft.item.Item;
import com.transformersmod.item.TFItems;

public class TransformerVurp extends TransformerCar
{
	@Override
	public Item getHelmet() 
	{
		return TFItems.vurpHelmet;
	}

	@Override
	public Item getChestplate() 
	{
		return TFItems.vurpChestplate;
	}

	@Override
	public Item getLeggings()
	{
		return TFItems.vurpLeggings;
	}

	@Override
	public Item getBoots()
	{
		return TFItems.vurpBoots;
	}
}
