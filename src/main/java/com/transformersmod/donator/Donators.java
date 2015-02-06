package com.transformersmod.donator;

import com.transformersmod.achievement.TFAchievements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Donators
{
	public static Map<UUID, Money> donators = new HashMap<UUID, Money>();

	public static void loadDonators()
	{
		donators.clear();

		DonatorLoader loader = new DonatorLoader();
		loader.loadDonators();
	}

	public static boolean isDonator(UUID id)
	{
		return donators.containsKey(id);
	}

	public static boolean isDonator(EntityPlayer player)
	{
		return isDonator(player.getUniqueID());
	}

	public static Money getDonationAmount(UUID id)
	{
		Money money = donators.get(id);

		if (money == null)
		{
			money = new Money("$0");
		}

		return money;
	}

	public static Money getDonationAmount(EntityPlayer player)
	{
		return getDonationAmount(player.getUniqueID());
	}

	public static void doAchievements(Side side)
	{
		if (side.isServer())
		{
			for (WorldServer world : MinecraftServer.getServer().worldServers) 
			{
				for (Object player : world.playerEntities)
				{
					if (player instanceof EntityPlayer)
					{
						if (isDonator((EntityPlayer) player))
						{
							((EntityPlayer) player).addStat(TFAchievements.donate, 1);
						}
					}
				}
			}
		}
	}
}
