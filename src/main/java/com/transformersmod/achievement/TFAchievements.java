package com.transformersmod.achievement;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import com.transformersmod.block.TFBlocks;
import com.transformersmod.item.TFItems;

public class TFAchievements 
{
	public static Achievement transformers = (new Achievement("achievement.transformers_mod", "transformers_mod", 0, 0, TFBlocks.energonCrystal, null)).func_180788_c();
	public static Achievement transformium = (new Achievement("achievement.transformium", "transformium", 2, -2, TFItems.transformium, transformers)).func_180788_c();
	public static Achievement transformer = new Achievement("achievement.transformer", "transformer", 4, -2, TFItems.skystrikeHelmet, transformium).func_180788_c();
	public static Achievement transform = new Achievement("achievement.transform", "transform", 4, -4, TFItems.carWheel, transformer).setSpecial().func_180788_c();
	public static Achievement firstMissile = new Achievement("achievement.shoot_missile", "shoot_missile", 4, 0, TFItems.missile, transformer).func_180788_c();
	public static Achievement donate = new Achievement("achievement.tf.donate", "tf.donate", 0, 2, Items.emerald, transformers).setSpecial().func_180788_c();
	
	public static AchievementPage transformersPage = new AchievementPage("Transformers", transformers, transformium, firstMissile, transformer, transform, donate);
	
	public static void register()
	{
		AchievementPage.registerAchievementPage(transformersPage);
	}
}
