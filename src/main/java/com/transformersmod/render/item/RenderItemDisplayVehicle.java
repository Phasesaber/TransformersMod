package com.transformersmod.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.transformersmod.TransformersMod;
import com.transformersmod.model.transformer.vehicle.ModelCloudtrapVehicle;
import com.transformersmod.model.transformer.vehicle.ModelPurgeVehicle;
import com.transformersmod.model.transformer.vehicle.ModelSkystrikeVehicle;
import com.transformersmod.model.transformer.vehicle.ModelSubwooferVehicle;
import com.transformersmod.model.transformer.vehicle.ModelVehicleBase;
import com.transformersmod.model.transformer.vehicle.ModelVurpVehicle;

// It's deprecated, but I'm lazy so we're using this.
public class RenderItemDisplayVehicle implements IItemRenderer
{
	private ModelSkystrikeVehicle skystrike = new ModelSkystrikeVehicle();
	private ModelPurgeVehicle purge = new ModelPurgeVehicle();
	private ModelVurpVehicle vurp = new ModelVurpVehicle();
	private ModelSubwooferVehicle subwoofer = new ModelSubwooferVehicle();
	private ModelCloudtrapVehicle cloudtrap = new ModelCloudtrapVehicle();
	
	public ModelVehicleBase getModelFromMetadata(int metadata)
	{
		if (metadata == 0) {return skystrike;}
		if (metadata == 1) {return purge;}
		if (metadata == 2) {return vurp;}
		if (metadata == 3) {return subwoofer;}
		if (metadata == 4) {return cloudtrap;}
		return skystrike;
	}
	
	public String getTextureFromMetadata(int metadata)
	{
		if (metadata == 0) {return "skystrike/skystrike.png";}
		if (metadata == 1) {return "purge/purge.png";}
		if (metadata == 2) {return "vurp/vurp.png";}
		if (metadata == 3) {return "subwoofer/subwoofer.png";}
		if (metadata == 4) {return "cloudtrap/cloudtrap.png";}
		return null;
	}
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}
	
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}
	
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(TransformersMod.modid, "textures/models/" + getTextureFromMetadata(item.getItemDamage())));
		ModelVehicleBase model = getModelFromMetadata(item.getItemDamage());
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(210, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(20, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.6F, -2.0F, 0.7F);
			
			float scale = 0.7F;
			GL11.glScalef(scale, scale, scale);
			model.render();
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.EQUIPPED)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-45, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-45, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.3F, -0.9F, -0.2F);
			
			float scale = 0.7F;
			GL11.glScalef(scale, scale, scale);
			model.render();
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.INVENTORY)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(0, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1.0F, 0.0F);
			
			float scale = 1.0F;
			GL11.glScalef(scale, scale, scale);
			model.render();
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.ENTITY)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(0, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -0.5F, 0.0F);
			
			float scale = 0.5F;
			GL11.glScalef(scale, scale, scale);
			model.render();
			GL11.glPopMatrix();
		}
	}
}