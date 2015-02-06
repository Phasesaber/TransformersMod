package com.transformersmod.render.item;

import com.transformersmod.model.item.ModelPurgesKatana;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.transformersmod.TransformersMod;

// It's deprecated, but I'm lazy so we're using this.
public class RenderItemPurgesKatana implements IItemRenderer
{
	private ModelPurgesKatana model = new ModelPurgesKatana();
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type != ItemRenderType.ENTITY && type != ItemRenderType.INVENTORY;
	}
	
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}
	
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(TransformersMod.modid, "textures/models/weapons/purges_katana.png"));
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.FIRST_PERSON_MAP)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(0, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(210, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.7F, 0.2F, -0.0F);
			
			float f = 1.0F;
			float f1 = f * 0.75F;
			GL11.glScalef(f, f, f);
			model.render(0.0625F);
			
			float f2 = 0.65F;
			GL11.glScalef(f, f, f2);
			model.renderWheels(0.0625F);
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.EQUIPPED)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(10, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(220, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.8F, 0.25F, -0.055F);
			
			float f = 1.0F;
			float f1 = f * 0.75F;
			GL11.glScalef(f, f, f);
			model.render(0.0625F);
			
			float f2 = 0.65F;
			GL11.glScalef(f, f, f2);
			model.renderWheels(0.0625F);
			GL11.glPopMatrix();
		}
	}
}