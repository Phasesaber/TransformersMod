package fiskfille.tf.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import fiskfille.tf.TransformersMod;

public class ItemBasic extends Item
{
	public ItemBasic()
	{
		super();
		this.setCreativeTab(TransformersMod.transformersTab);
	}
	
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(TransformersMod.modid + ":" + iconString);
	}
}