package com.transformersmod.item;

import com.google.common.collect.Multimap;
import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.misc.TFMotionManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemPurgesKatana extends ItemSword
{
	public ItemPurgesKatana(ToolMaterial material)
	{
		super(material);
		this.setCreativeTab(TransformersMod.transformersTab);
		this.setMaxDamage(1500);
	}
	
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
    {
		if (!TFDataManager.isInVehicleMode(player) && TFHelper.isPlayerPurge(player))
		{
	        int j = this.getMaxItemUseDuration(stack) - par4;
	        double d = (double)j / 10;
	        
	        if (d > 2.0D)
	        {
	        	d = 2.0D;
	        }
	        	        
	        stack.damageItem(2, player);
	        Vec3 vec3 = TFMotionManager.getFrontCoords(player, player.onGround ? d : d * 0.75D, true);
	        player.motionX = (vec3.xCoord - player.posX);
	        player.motionY = (vec3.yCoord - player.posY) / 2;
			player.motionZ = (vec3.zCoord - player.posZ);
		}
    }
    
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.DRINK;
    }
    
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	if (TFHelper.isPlayerPurge(player))
    	{
    		if (!TFDataManager.isInVehicleMode(player))
        	{
        		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        	}
//        	else
//        	{        		
//        		if (!par2World.isRemote)
//        		{
//        			EntitySnowball arrow = new EntitySnowball(par2World, par3EntityPlayer);
//            		arrow.posY += EntityRendererAlt.offsetY;
//        			par2World.spawnEntityInWorld(arrow);
//        		}
//        		
//    			for (int i = 0; i < 10; ++i)
//    			{
//    				float f = 1.0F;
//    		        float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
//    		        float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
//    		        double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)f;
//    		        double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)f + EntityRendererAlt.offsetY;
//    		        double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)f;
//    		        Vec3 vec3 = new Vec3(d0, d1, d2);
//    		        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
//    		        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
//    		        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
//    		        float f6 = MathHelper.sin(-f1 * 0.017453292F);
//    		        float f7 = f4 * f5;
//    		        float f8 = f3 * f5;
//    		        double d3 = i + 1;
//    		        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
//    				
//    				for (int i1 = 0; i1 < 5; ++i1)
//    				{
//    					par3EntityPlayer.worldObj.spawnParticle("smoke", vec31.xCoord, vec31.yCoord, vec31.zCoord, 0.0D, 0.0D, 0.0D);
//    				}
//    			}
//        	}
    	}
        return stack;
    }
	
	public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", 8.0D, 0));
        return multimap;
    }
}