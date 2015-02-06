package com.transformersmod.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.transformersmod.TFHelper;

@SideOnly(Side.CLIENT)
public class RenderCustomPlayer extends RenderPlayer
{
	public RenderCustomPlayer()
	{
		super(Minecraft.getMinecraft().getRenderManager());
	}

	public void setModel(ModelBase model)
	{
		this.mainModel = model;
	}
	
//	public void setManager(RenderManager manager)
//	{
//		this.renderManager = manager;
//	}
	
	@Override
	public void func_177139_c(AbstractClientPlayer player)
	{
		ItemStack currentArmour = player.getCurrentArmor(2);
		
		if(currentArmour != null)
		{
			if (!TFHelper.isTransformerArmor(player, currentArmour.getItem()))
			{
				super.func_177139_c(player);
			}
		}
		else
		{
			super.func_177139_c(player);
		}
	}
}