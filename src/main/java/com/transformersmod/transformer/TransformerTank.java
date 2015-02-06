package com.transformersmod.transformer;

import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.entity.EntityTankShell;
import com.transformersmod.item.TFItems;
import com.transformersmod.misc.TFMotionManager;
import com.transformersmod.misc.TFNitroParticleHandler;
import com.transformersmod.misc.VehicleMotion;
import com.transformersmod.packet.PacketVehicleNitro;
import com.transformersmod.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;

import java.util.Random;

public abstract class TransformerTank extends Transformer
{
	@Override
	public String getShootSound()
	{
		return TransformersMod.modid + ":tankfire";
	}
	
	@Override
	public boolean canZoom(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public boolean shouldTakeFallDamage(EntityPlayer player)
	{
		return !TFDataManager.isInVehicleMode(player);
	}
	
	@Override
	public void updateMovement(EntityPlayer player)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		boolean moveForward = minecraft.gameSettings.keyBindForward.isPressed();
		boolean nitroPressed = ClientProxy.keyBindingNitro.isPressed() || minecraft.gameSettings.keyBindSprint.isPressed();

		player.stepHeight = 1.0F;
		VehicleMotion transformedPlayer = TFMotionManager.getTransformerPlayer(player);

		int nitro = 0;
		double vel = 0;

		if (transformedPlayer != null)
		{
			nitro = transformedPlayer.getNitro();
			vel = transformedPlayer.getForwardVelocity();
			double increment = ((nitroPressed && nitro > 0 ? 0.15D : 0.035D) - vel) / 10 + 0.001D;

			if (moveForward && vel <= 1.0D)
			{
				vel += increment * 0.5F;
			}
			if (vel > 0.02D && !moveForward)
			{
				vel -= 0.02D;
			}
			if (vel < 0.01D && !moveForward)
			{
				vel = 0.0D;
			}

			Vec3 vec3 = TFMotionManager.getFrontCoords(player, 0, vel);
			player.motionX = (vec3.xCoord - player.posX);
			player.motionZ = (vec3.zCoord - player.posZ);
			if (vel <= 0) {vel = 0;}
			if (vel > 0.2D) {vel = 0.2D;}
			if (vel < 0.02D && !moveForward) {vel = 0;}

			boolean prevNitro = TFMotionManager.prevNitro;
			
			if (nitro > 0 && nitroPressed && moveForward && player == Minecraft.getMinecraft().thePlayer)
			{
				--nitro;

				if (!prevNitro)
				{
					TransformersMod.networkWrapper.sendToServer(new PacketVehicleNitro(player, true));
					TFMotionManager.prevNitro = true;
				}

				for (int i = 0; i < 4; ++i)
				{
					Vec3 side = TFMotionManager.getBackSideCoords(player, 0.15F, i < 2, -0.6, false);
					Random rand = new Random();
					player.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, side.xCoord, player.posY - 1.4F, side.zCoord, rand.nextFloat() / 20, rand.nextFloat() / 20, rand.nextFloat() / 20);
				}
			}
			else
			{
				if (prevNitro)
				{
					TransformersMod.networkWrapper.sendToServer(new PacketVehicleNitro(player, false));
					TFMotionManager.prevNitro = false;
				}
			}

			transformedPlayer.setNitro(nitro);
			transformedPlayer.setForwardVelocity(vel);

			if (player.isInWater())
			{
				player.motionY = -0.1F;
			}
		}
	}
	
	@Override
	public boolean canShoot(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public Item getShootItem()
	{
		return TFItems.tankShell;
	}

	@Override
	public Entity getShootEntity(EntityPlayer player)
	{
		return new EntityTankShell(player.worldObj, player, 3);
	}
	
	@Override
	public void doNitroParticles(EntityPlayer player)
	{
		for (int i = 0; i < 4; ++i)
		{
			Vec3 side = TFNitroParticleHandler.getBackSideCoords(player, 0.15F, i < 2, -1, false);
			Random rand = new Random();
			player.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, side.xCoord, player.posY - 0F, side.zCoord, rand.nextFloat() / 20, rand.nextFloat() / 20, rand.nextFloat() / 20);
		}
	}
}
