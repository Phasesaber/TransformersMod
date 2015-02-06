package com.transformersmod.data;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.achievement.TFAchievements;
import com.transformersmod.packet.PacketHandleStealthTransformation;
import com.transformersmod.packet.PacketHandleTransformation;
import com.transformersmod.packet.PacketSyncTransformationStates;
import com.transformersmod.transformer.Transformer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TFDataManager 
{
	public static Map<UUID, Integer> transformationTimerClient = new HashMap<UUID, Integer>();
	public static Map<UUID, Integer> stealthModeTimerClient = new HashMap<UUID, Integer>();

	public static void setInVehicleMode(EntityPlayer player, boolean vehicleMode)
	{
		TFPlayerData data = TFPlayerData.getData(player);
		
		if (vehicleMode != data.mode)
		{
			player.triggerAchievement(TFAchievements.transform);
			
			if(!vehicleMode)
			{
				data.stealthMode = false;
			}
			
			if (player.worldObj.isRemote)
			{
				TransformersMod.networkWrapper.sendToServer(new PacketHandleTransformation(player, vehicleMode));
			}
			else
			{
				TransformersMod.networkWrapper.sendToDimension(new PacketHandleTransformation(player, vehicleMode), player.dimension);
			}

			data.mode = vehicleMode;
		}
	}
	
	public static void setInStealthMode(EntityPlayer player, boolean stealthMode)
	{
		if (stealthMode != TFPlayerData.getData(player).stealthMode)
		{
			if (isInVehicleMode(player))
			{
				if (player.worldObj.isRemote)
				{
					TransformersMod.networkWrapper.sendToServer(new PacketHandleStealthTransformation(player, stealthMode));
				}
				else
				{
					TransformersMod.networkWrapper.sendToDimension(new PacketHandleStealthTransformation(player, stealthMode), player.dimension);
				}

				TFPlayerData.getData(player).stealthMode = stealthMode;
			}
		}
	}

	public static void setTransformationTimer(EntityPlayer player, int timer)
	{
		transformationTimerClient.put(player.getUniqueID(), timer);
	}

	public static void setStealthModeTimer(EntityPlayer player, int timer)
	{
		stealthModeTimerClient.put(player.getUniqueID(), timer);
	}
	
	public static boolean isInVehicleMode(EntityPlayer player)
	{
		return TFPlayerData.getData(player).mode && TFHelper.isPlayerTransformer(player);
	}

	public static boolean isInStealthMode(EntityPlayer player)
	{
		Transformer transformer = TFHelper.getTransformer(player);

		if (transformer != null)
		{
			return transformer.hasStealthForce(player) && isInVehicleMode(player) && TFPlayerData.getData(player).stealthMode;
		}
		
		return false;
	}
	
	public static int getTransformationTimer(EntityPlayer player)
	{
		Integer timer = transformationTimerClient.get(player.getUniqueID());
		
		return timer != null ? timer : 0;
	}
	
	public static int getStealthModeTimer(EntityPlayer player)
	{
		Integer timer = stealthModeTimerClient.get(player.getUniqueID());
		
		return timer != null ? timer : 0;
	}

	public static void toggleVehicleMode(EntityPlayer player) 
	{
		setInVehicleMode(player, !TFPlayerData.getData(player).mode);
	}
	
	public static void toggleStealthMode(EntityPlayer player) 
	{
		setInStealthMode(player, !TFPlayerData.getData(player).stealthMode);
	}

	public static void updateTransformationStatesFor(EntityPlayer player) 
	{
		Map<UUID, Boolean[]> states = new HashMap<UUID, Boolean[]>();

		for (Object obj : player.worldObj.playerEntities) 
		{
			if (obj instanceof EntityPlayer)
			{
				EntityPlayer cPlayer = (EntityPlayer) obj;

				TFPlayerData data = TFPlayerData.getData(cPlayer);

				if (data != null)
				{
					states.put(cPlayer.getUniqueID(), new Boolean[]{data.mode, data.stealthMode});
				}
			}
		}

		TransformersMod.networkWrapper.sendTo(new PacketSyncTransformationStates(states), (EntityPlayerMP) player);
	}
}