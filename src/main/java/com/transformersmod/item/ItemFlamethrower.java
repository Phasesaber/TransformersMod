package com.transformersmod.item;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.misc.TFMotionManager;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemFlamethrower extends ItemSword
{
	public ItemFlamethrower(ToolMaterial material)
	{
		super(material);
		this.setCreativeTab(TransformersMod.transformersTab);
		this.setMaxDamage(1500);
	}

	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
	{
		if (TFHelper.isPlayerCloudtrap(player) && !world.isRemote && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
		{
			stack.damageItem(5, player);
			
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.consumeInventoryItem(TFItems.energonCrystalPiece);
			}
		}
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		int duration = this.getMaxItemUseDuration(stack) - count;
		
		if (duration < 100)
		if (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode)
		{			
			if (duration % 5 == 0)
			{
				Vec3 backCoords = TFMotionManager.getFrontCoords(player, -0.3F, true);
				player.motionX = (backCoords.xCoord - player.posX);
				player.motionZ = (backCoords.zCoord - player.posZ);
				
				player.worldObj.playAuxSFX(1009, new BlockPos((int)player.posX, (int)player.posY, (int)player.posZ), 0);
			}
			
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
			double d31 = 0.5D;
			Vec3 vec33 = vec32.addVector(f71 * d31, f61 * d31, f81 * d31);
			
			for (int i = 0; i < 7; ++i)
			{
	            float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
				float f2 = (player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f);
				double d0 = vec33.xCoord * (double)f;
				double d1 = vec33.yCoord * (double)f;
				double d2 = vec33.zCoord * (double)f;
				Vec3 vec3 = new Vec3(d0, d1, d2);
				float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
				float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
				float f5 = -MathHelper.cos(-f1 * 0.017453292F);
				float f6 = MathHelper.sin(-f1 * 0.017453292F);
				float f7 = f4 * f5;
				float f8 = f3 * f5;
				double d3 = i + 1;
				Vec3 hurtVec = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
				
				for (int i1 = 0; i1 < 5; ++i1)
				{
					Random rand = new Random();
					Block block = player.worldObj.getBlockState(new BlockPos((int)hurtVec.xCoord - 1, (int)hurtVec.yCoord, (int)hurtVec.zCoord)).getBlock();
//					player.worldObj.spawnParticle("flame", hurtVec.xCoord + rand.nextFloat() - 0.5F, hurtVec.yCoord + rand.nextFloat() - 1.0F, hurtVec.zCoord + rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D);
					player.worldObj.spawnParticle(EnumParticleTypes.FLAME, hurtVec.xCoord, hurtVec.yCoord, hurtVec.zCoord, rand.nextFloat() / 5, rand.nextFloat() / 5, rand.nextFloat() / 5);
					
					if (!player.worldObj.isRemote)
					{
						if (block == Blocks.air && player.worldObj.getBlockState(new BlockPos((int) hurtVec.xCoord - 1, (int) hurtVec.yCoord - 1, (int) hurtVec.zCoord)).getBlock().isOpaqueCube())
						{
							player.worldObj.setBlockState(new BlockPos((int)hurtVec.xCoord - 1, (int)hurtVec.yCoord, (int)hurtVec.zCoord), Blocks.fire.getDefaultState());
						}
					}
				}
				
				List<Entity> entities = getEntitiesNear(player.worldObj,  hurtVec.xCoord, hurtVec.yCoord, hurtVec.zCoord, 1.0F);
				
				for (Entity entity : entities)
				{
					if (!entity.getUniqueID().equals(player.getUniqueID()))
					{										
						if (entity instanceof EntityLivingBase)
						{
							((EntityLivingBase)entity).setFire(20);
							((EntityLivingBase)entity).attackEntityFrom(DamageSource.causePlayerDamage(player), 10.0F);
						}
					}
				}
			}
		}
	}

	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 72000;
	}

	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (TFHelper.isPlayerCloudtrap(player) && (player.inventory.hasItem(TFItems.energonCrystalPiece) || player.capabilities.isCreativeMode))
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		
		return stack;
	}

	public List getEntitiesNear(World world, double par1, double par2, double par3, float par4)
	{
        return world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4), IEntitySelector.selectAnything);
	}
}