package com.transformersmod.render.tileentity;

import com.transformersmod.model.tileentity.ModelCrystal;
import com.transformersmod.tileentity.TileEntityCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderCrystal extends TileEntitySpecialRenderer
{
	private ResourceLocation texture = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private ModelCrystal model;
	private ItemRenderer itemRenderer;
 
    public RenderCrystal()
    {
        model = new ModelCrystal();
        itemRenderer = new ItemRenderer(Minecraft.getMinecraft());
    }

    public void renderTileEntityAt(TileEntity p_180535_1_, double posX, double posZ, double p_180535_6_, float p_180535_8_, int p_180535_9_)
    {
        renderAModelAt((TileEntityCrystal)p_180535_1_, posX, posZ, p_180535_6_, p_180535_8_);
    }

    public void renderAModelAt(TileEntityCrystal tileentity, double d, double d1, double d2, float f)
    {
    	GL11.glPushMatrix();
    	GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
    	GL11.glScalef(1.0F, -1F, -1F);
    	adjustRotation(tileentity, d, d1, d2, f);
    	
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(0.0F, 0.5F, 1.0F, 0.5F);
    	this.bindTexture(new ResourceLocation("textures/misc/underwater.png"));
    	model.renderAll();
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glPopMatrix();
    }
    
    public void adjustRotation(TileEntityCrystal tile, double d, double d1, double d2, float f)
    {
    	int rot = tile.getWorld().getBlockState(tile.getPos()).getBlock().getMetaFromState(tile.getWorld().getBlockState(tile.getPos()));
    	if (rot == 1) {GL11.glRotatef(0 * 90, 0.0F, 1.0F, 0.0F); GL11.glTranslatef(0, 0, 0.075F);}
    	if (rot == 2) {GL11.glRotatef(2 * 90, 0.0F, 1.0F, 0.0F); GL11.glTranslatef(0, 0, 0.075F);}
    	if (rot == 3) {GL11.glRotatef(1 * 90, 0.0F, 1.0F, 0.0F); GL11.glTranslatef(0, 0, 0.075F);}
    	if (rot == 4) {GL11.glRotatef(3 * 90, 0.0F, 1.0F, 0.0F); GL11.glTranslatef(0, 0, 0.075F);}
    	if (rot == 5) {GL11.glTranslatef(0.05F, 0, 0.075F);}
    	if (rot == 6)
    	{
    		GL11.glTranslatef(-0.05F, 2, 0.075F);
    		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
    	}
    	
    	if (rot != 5 && rot != 6)
    	{
    		GL11.glTranslatef(0.6F, 0.1F, 0.0F);
			GL11.glRotatef(45, 0, 0, 1);
    	}
    }
}