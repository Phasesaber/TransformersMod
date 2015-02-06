package com.transformersmod.render.tileentity;

import com.transformersmod.TransformersMod;
import com.transformersmod.model.tileentity.ModelDisplayPillar;
import com.transformersmod.model.transformer.vehicle.*;
import com.transformersmod.tileentity.TileEntityDisplayPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDisplayPillar extends TileEntitySpecialRenderer
{
	private ResourceLocation texture = new ResourceLocation(TransformersMod.modid + ":textures/models/tiles/display_pillar.png");
	private ModelDisplayPillar model;
	private ModelSkystrikeVehicle skystrike = new ModelSkystrikeVehicle();
	private ModelPurgeVehicle purge = new ModelPurgeVehicle();
	private ModelVurpVehicle vurp = new ModelVurpVehicle();
	private ModelSubwooferVehicle subwoofer = new ModelSubwooferVehicle();
	private ModelCloudtrapVehicle cloudtrap = new ModelCloudtrapVehicle();

    public RenderDisplayPillar()
	{
		model = new ModelDisplayPillar();
    }

	public void renderAModelAt(TileEntityDisplayPillar tileentity, double d, double d1, double d2, float f)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
		GL11.glScalef(1.0F, -1F, -1F);
		this.bindTexture(texture);
		model.renderAll();
				
		ItemStack displayItem = tileentity.getDisplayItem();
		
		if (displayItem != null)
		{
			this.bindTexture(new ResourceLocation(TransformersMod.modid, "textures/models/" + getTextureFromStack(displayItem)));
			ModelVehicleBase vehicle = getModelFromStack(displayItem);
			
			if (vehicle != null)
			{
				GL11.glRotatef(Minecraft.getMinecraft().thePlayer.ticksExisted, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.2F/* + (vehicle == skystrike ? 0.15F : 0.0F)*/, 0.0F);
				
				float scale = 0.75F;
				GL11.glScalef(scale, scale, scale);
				vehicle.render();
			}
		}

		GL11.glPopMatrix();
	}

	public ModelVehicleBase getModelFromStack(ItemStack displayItem)
	{
		if (displayItem.getItemDamage() == 0) {return skystrike;}
		if (displayItem.getItemDamage() == 1) {return purge;}
		if (displayItem.getItemDamage() == 2) {return vurp;}
		if (displayItem.getItemDamage() == 3) {return subwoofer;}
		if (displayItem.getItemDamage() == 4) {return cloudtrap;}
		
		return null;
	}

	public String getTextureFromStack(ItemStack displayItem)
	{
		if (displayItem.getItemDamage() == 0) {return "skystrike/skystrike.png";}
		if (displayItem.getItemDamage() == 1) {return "purge/purge.png";}
		if (displayItem.getItemDamage() == 2) {return "vurp/vurp.png";}
		if (displayItem.getItemDamage() == 3) {return "subwoofer/subwoofer.png";}
		if (displayItem.getItemDamage() == 4) {return "cloudtrap/cloudtrap.png";}
		
		return null;
	}
	
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f, int i)
	{
		renderAModelAt((TileEntityDisplayPillar)tileentity, d, d1, d2, f);
	} 
}