package com.transformersmod.entity;

import com.transformersmod.TransformersMod;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TFEntities 
{
	public static void registerEntities()
	{
//		EntityRegistry.registerModEntity(EntityLaser.class, "laser", 305, MainClass.modid, 1, 10, true);
		
		int tankShellID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityTankShell.class, "tf_tank_shell", tankShellID);
		EntityRegistry.registerModEntity(EntityTankShell.class, "tf_tank_shell", tankShellID, TransformersMod.instance, 20, 10, true);
		int missileID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityMissile.class, "tf_missile", missileID);
		EntityRegistry.registerModEntity(EntityMissile.class, "tf_missile", missileID, TransformersMod.instance, 20, 10, true);
	}
}
