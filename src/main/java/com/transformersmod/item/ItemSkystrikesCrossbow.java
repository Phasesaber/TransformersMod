package com.transformersmod.item;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class ItemSkystrikesCrossbow extends ItemSword
{
	public ItemSkystrikesCrossbow(ToolMaterial material)
	{
		super(material);
		this.setCreativeTab(TransformersMod.transformersTab);
		this.setMaxDamage(1500);
	}
	
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean b)
	{
		EntityPlayer player = (EntityPlayer)entity;
		
		if (!itemstack.hasTagCompound())
		{
			itemstack.setTagCompound(new NBTTagCompound());
			itemstack.getTagCompound().setBoolean("blueMode", false);
		}
	}

	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer player, int par4)
	{
		if (TFHelper.isPlayerSkystrike(player) && !par2World.isRemote && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
		{
			boolean flag = par1ItemStack.hasTagCompound() ? par1ItemStack.getTagCompound().getBoolean("blueMode") : false;
			par1ItemStack.getTagCompound().setBoolean("blueMode", !flag);
			
			par1ItemStack.damageItem(5, player);
			
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.consumeInventoryItem(TFItems.energonCrystalPiece);
			}
		}
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		int duration = this.getMaxItemUseDuration(stack) - count;
		
		if (stack.hasTagCompound() && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
		{
			boolean isInVehichleMode = false;
			
			if (duration == 1)
			{

			}
			
			if (duration > 1 && duration < 80)
			{	
				float f = 1.0F;
				float f11 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
				float f21 = (player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f) + 90;
				double d01 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
				double d11 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(player.worldObj.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
				double d21 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
				Vec3 vec32 = new Vec3(d01, d11, d21);
				float f31 = MathHelper.cos(-f21 * 0.017453292F - (float)Math.PI);
				float f41 = MathHelper.sin(-f21 * 0.017453292F - (float)Math.PI);
				float f51 = -MathHelper.cos(-f11 * 0.017453292F);
				float f61 = MathHelper.sin(-f11 * 0.017453292F);
				float f71 = f41 * f51;
				float f81 = f31 * f51;
				double d31 = isInVehichleMode ? 0.0D : 0.3D;
				Vec3 vec33 = vec32.addVector(f71 * d31, f61 * d31, f81 * d31);

				for (int i = 0; i < 20; ++i)
				{
					float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
					float f2 = (player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f);
					double d0 = vec33.xCoord * (double)f;
					double d1 = vec33.yCoord * (double)f - 0.1D;
					double d2 = vec33.zCoord * (double)f;
					Vec3 vec3 = new Vec3(d0, d1, d2);
					float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
					float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
					float f5 = -MathHelper.cos(-f1 * 0.017453292F);
					float f6 = MathHelper.sin(-f1 * 0.017453292F);
					float f7 = f4 * f5;
					float f8 = f3 * f5;
					double d3 = i + 1;
					Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);

					for (int i1 = 0; i1 < 10; ++i1)
					{
						boolean flag = stack.hasTagCompound() ? stack.getTagCompound().getBoolean("blueMode") : false;
						player.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, vec31.xCoord, vec31.yCoord - (isInVehichleMode ? 0.5D : 0.0D), vec31.zCoord, flag ? -1.0D : 0.0D, 0.0D, flag ? 1.0D : 0.0D);
					}

					List<Entity> list = getEntitiesNear(player.worldObj,  vec31.xCoord, vec31.yCoord, vec31.zCoord, 0.3F);

					for (Entity entity : list)
					{
						if (!entity.getUniqueID().equals(player.getUniqueID()))
						{										
							if (entity instanceof EntityLivingBase)
							{
								((EntityLivingBase)entity).attackEntityFrom(DamageSource.causePlayerDamage(player), 5.0F);
							}
						}
					}
				}
			}
		}
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer player)
	{
		if (TFHelper.isPlayerSkystrike(player) && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		
		return stack;
	}

	public List<Entity> getEntitiesNear(World par0World, double par1, double par2, double par3, float par4)
	{
		List<Entity> list = par0World.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4), IEntitySelector.selectAnything);
		return list;
	}
}