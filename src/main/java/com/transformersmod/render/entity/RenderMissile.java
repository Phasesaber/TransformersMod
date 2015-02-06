package com.transformersmod.render.entity;

import com.transformersmod.TransformersMod;
import com.transformersmod.entity.EntityMissile;
import com.transformersmod.model.ModelMissile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderMissile extends Render
{
    private ModelMissile model = new ModelMissile();
    private ResourceLocation texture = new ResourceLocation(TransformersMod.modid, "textures/models/weapons/missile.png");

    public RenderMissile()
    {
        super(Minecraft.getMinecraft().getRenderManager());
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityMissile missile, double x, double y, double z, float p_76986_9_)
    {
    	 this.bindEntityTexture(missile);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x, (float)y, (float)z);
         GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90, 0, -1, 0);
         
         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
         float f11 = (float)missile.missileShake - p_76986_9_;

         if (f11 > 0.0F)
         {
             float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
             GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
         }
         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
         
         float f = 0.7F;
         GL11.glScalef(f, f, f);
         model.render(missile, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GL11.glRotatef(90, 0.0F, 0.0F, -1.0F);
         GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture()
    {
        return texture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityMissile)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_9_);
    }
}