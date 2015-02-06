package com.transformersmod.item;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.item.armor.ItemCloudtrapArmor;
import com.transformersmod.item.armor.ItemPurgeArmor;
import com.transformersmod.item.armor.ItemSkystrikeArmor;
import com.transformersmod.item.armor.ItemSubwooferArmor;
import com.transformersmod.item.armor.ItemVurpArmor;
import com.transformersmod.registry.TFItemRegistry;

public class TFItems
{
	public static ArmorMaterial TRANSFORMERMATERIAL = EnumHelper.addArmorMaterial("Transformer", "transformer", 1250 / 16, new int[]{3, 9, 6, 3}, 1);
	public static ArmorMaterial TANKMATERIAL = EnumHelper.addArmorMaterial("Transformer", "transformer", 1550 / 16, new int[]{4, 9, 7, 4}, 1);
	
	public static Item transformium;
	public static Item transformiumArmorMolds;
	public static Item standardEngine;
	public static Item jetTurbine;
	public static Item jetThruster;
	public static Item f88JetWing;
	public static Item f88JetCockpit;
	public static Item t50JetWing;
	public static Item t50JetCockpit;
	public static Item tankTracks;
	public static Item tankTurret;
	public static Item carWheel;
	
	public static Item skystrikesCrossbow;
	public static Item purgesKatana;
	public static Item vurpsRocketLauncher;
	public static Item flamethrower;
	
	public static Item skystrikeHelmet;
	public static Item skystrikeChestplate;
	public static Item skystrikeLeggings;
	public static Item skystrikeBoots;
	public static Item purgeHelmet;
	public static Item purgeChestplate;
	public static Item purgeLeggings;
	public static Item purgeBoots;
	public static Item vurpHelmet;
	public static Item vurpChestplate;
	public static Item vurpLeggings;
	public static Item vurpBoots;
	public static Item subwooferHelmet;
	public static Item subwooferChestplate;
	public static Item subwooferLeggings;
	public static Item subwooferBoots;
	public static Item cloudtrapHelmet;
	public static Item cloudtrapChestplate;
	public static Item cloudtrapLeggings;
	public static Item cloudtrapBoots;
	
	public static Item displayVehicle;
	public static Item energonCrystalPiece;
	
	public static Item tankShell;
	public static Item missile;
	public static Item smallThruster;
	
	public static Item transformiumDetector;
	
	public void load()
	{
		String modId = TransformersMod.modid;
		
		transformium = new ItemBasic();
		transformiumArmorMolds = new ItemMetaBasic("Transformium Head Mold", "Transformium Torso Mold", "Transformium Legs Mold", "Transformium Feet Mold");
		standardEngine = new ItemBasic();
		jetTurbine = new ItemBasic();
		f88JetWing = new ItemBasic();
		f88JetCockpit = new ItemBasic();
		tankTracks = new ItemBasic();
		tankTurret = new ItemBasic();
		carWheel = new ItemBasic();
		t50JetCockpit = new ItemBasic();
		t50JetWing = new ItemBasic();
		jetThruster = new ItemBasic();
		smallThruster = new ItemBasic();
		flamethrower = new ItemFlamethrower(ToolMaterial.WOOD);
		
		skystrikeHelmet = new ItemSkystrikeArmor(0);
		skystrikeChestplate = new ItemSkystrikeArmor(1);
		skystrikeLeggings = new ItemSkystrikeArmor(2);
		skystrikeBoots = new ItemSkystrikeArmor(3);
		purgeHelmet = new ItemPurgeArmor(0);
		purgeChestplate = new ItemPurgeArmor(1);
		purgeLeggings = new ItemPurgeArmor(2);
		purgeBoots = new ItemPurgeArmor(3);
		vurpHelmet = new ItemVurpArmor(0);
		vurpChestplate = new ItemVurpArmor(1);
		vurpLeggings = new ItemVurpArmor(2);
		vurpBoots = new ItemVurpArmor(3);
		subwooferHelmet = new ItemSubwooferArmor(0);
		subwooferChestplate = new ItemSubwooferArmor(1);
		subwooferLeggings = new ItemSubwooferArmor(2);
		subwooferBoots = new ItemSubwooferArmor(3);
		cloudtrapHelmet = new ItemCloudtrapArmor(0);
		cloudtrapChestplate = new ItemCloudtrapArmor(1);
		cloudtrapLeggings = new ItemCloudtrapArmor(2);
		cloudtrapBoots = new ItemCloudtrapArmor(3);
		skystrikesCrossbow = new ItemSkystrikesCrossbow(ToolMaterial.WOOD);
		purgesKatana = new ItemPurgesKatana(ToolMaterial.EMERALD);
//		vurpsRocketLauncher = new ItemVurpsRocketLauncher();
		
		transformiumDetector = new ItemTransformiumDetector();
		
		displayVehicle = new ItemMiniVehicle("item.display_skystrike.name", "item.display_purge.name", "item.display_vurp.name", "item.display_subwoofer.name", "item.display_cloudtrap.name");
		energonCrystalPiece = new ItemBasic();
		
		tankShell = new ItemBasic();
		missile = new ItemBasic().setFull3D();
		smallThruster = new ItemBasic();
		
		TRANSFORMERMATERIAL.customCraftingMaterial = transformium;
		TANKMATERIAL.customCraftingMaterial = transformium;
		
		TFItemRegistry.registerItem(transformium, "Transformium", modId);
		TFItemRegistry.registerItem(transformiumArmorMolds, "Transformium Armor Molds", modId);
		TFItemRegistry.registerItem(standardEngine, "Standard Engine", modId);
		TFItemRegistry.registerItem(jetTurbine, "Jet Turbine", modId);
		TFItemRegistry.registerItem(f88JetWing, "F-88 Jet Wing", modId);
		TFItemRegistry.registerItem(f88JetCockpit, "F-88 Jet Cockpit", modId);
		TFItemRegistry.registerItem(t50JetWing, "T-50 Jet Wing", modId);
		TFItemRegistry.registerItem(t50JetCockpit, "T-50 Jet Cockpit", modId);
		TFItemRegistry.registerItem(tankTracks, "Tracks", modId);
		TFItemRegistry.registerItem(tankTurret, "Turret", modId);
		TFItemRegistry.registerItem(carWheel, "Wheel", modId);
		TFItemRegistry.registerItem(jetThruster, "Jet Thruster", modId);
		TFItemRegistry.registerItem(smallThruster, "Small Thruster", modId);
		TFItemRegistry.registerItem(transformiumDetector, "Transformium Detector", modId);
		TFItemRegistry.registerItem(flamethrower, "Flame Thrower", modId);
		
		TFItemRegistry.registerItem(skystrikesCrossbow, "Skystrike's Energon Crossbow", modId);
		TFItemRegistry.registerItem(purgesKatana, "Purge's Katana", modId);
		
		TFItemRegistry.registerItem(skystrikeHelmet, "Skystrike Head", modId);
		TFItemRegistry.registerItem(skystrikeChestplate, "Skystrike Torso", modId);
		TFItemRegistry.registerItem(skystrikeLeggings, "Skystrike Legs", modId);
		TFItemRegistry.registerItem(skystrikeBoots, "Skystrike Feet", modId);
		TFItemRegistry.registerItem(purgeHelmet, "Purge Head", modId);
		TFItemRegistry.registerItem(purgeChestplate, "Purge Torso", modId);
		TFItemRegistry.registerItem(purgeLeggings, "Purge Legs", modId);
		TFItemRegistry.registerItem(purgeBoots, "Purge Feet", modId);
		TFItemRegistry.registerItem(vurpHelmet, "Vurp Head", modId);
		TFItemRegistry.registerItem(vurpChestplate, "Vurp Torso", modId);
		TFItemRegistry.registerItem(vurpLeggings, "Vurp Legs", modId);
		TFItemRegistry.registerItem(vurpBoots, "Vurp Feet", modId);
		TFItemRegistry.registerItem(subwooferHelmet, "Subwoofer Head", modId);
		TFItemRegistry.registerItem(subwooferChestplate, "Subwoofer Torso", modId);
		TFItemRegistry.registerItem(subwooferLeggings, "Subwoofer Legs", modId);
		TFItemRegistry.registerItem(subwooferBoots, "Subwoofer Feet", modId);
		TFItemRegistry.registerItem(cloudtrapHelmet, "Cloudtrap Head", modId);
		TFItemRegistry.registerItem(cloudtrapChestplate, "Cloudtrap Torso", modId);
		TFItemRegistry.registerItem(cloudtrapLeggings, "Cloudtrap Legs", modId);
		TFItemRegistry.registerItem(cloudtrapBoots, "Cloudtrap Feet", modId);
		
		TFItemRegistry.registerItem(displayVehicle, "Display Vehicle", modId);
		TFItemRegistry.registerItem(energonCrystalPiece, "Energon Crystal Piece", modId);
		TFItemRegistry.registerItem(tankShell, "Tank Shell", modId);
		TFItemRegistry.registerItem(missile, "Missile", modId);
	}
}