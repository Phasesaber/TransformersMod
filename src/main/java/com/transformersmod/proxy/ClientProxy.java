package com.transformersmod.proxy;

import com.transformersmod.entity.EntityMissile;
import com.transformersmod.entity.EntityTankShell;
import com.transformersmod.item.TFItems;
import com.transformersmod.model.player.ModelBipedTF;
import com.transformersmod.model.transformer.*;
import com.transformersmod.render.entity.RenderCustomPlayer;
import com.transformersmod.render.entity.RenderMissile;
import com.transformersmod.render.entity.RenderTankShell;
import com.transformersmod.render.item.RenderItemDisplayVehicle;
import com.transformersmod.render.item.RenderItemFlamethrower;
import com.transformersmod.render.item.RenderItemPurgesKatana;
import com.transformersmod.render.item.RenderItemSkystrikesCrossbow;
import com.transformersmod.render.tileentity.RenderCrystal;
import com.transformersmod.render.tileentity.RenderDisplayPillar;
import com.transformersmod.tick.ClientTickHandler;
import com.transformersmod.tileentity.TileEntityCrystal;
import com.transformersmod.tileentity.TileEntityDisplayPillar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

public class ClientProxy extends CommonProxy
{
	private static ModelSkystrike modelSkystrike = new ModelSkystrike();
	private static ModelPurge modelPurge = new ModelPurge();
	private static ModelVurp modelVurp = new ModelVurp();
	private static ModelSubwoofer modelSubwoofer = new ModelSubwoofer();
	private static ModelCloudtrap modelCloudTrap = new ModelCloudtrap();
	public static ModelBiped modelBipedMain = new ModelBiped();
	
	public static KeyBinding keyBindingTransform = new KeyBinding("Transform", Keyboard.KEY_C, "Transformers");
	public static KeyBinding keyBindingNitro = new KeyBinding("Nitro Boost", Keyboard.KEY_X, "Transformers");
	public static KeyBinding keyBindingBrake = new KeyBinding("Brake", Keyboard.KEY_Z, "Transformers");
	public static KeyBinding keyBindingZoom = new KeyBinding("Tank Aim", Keyboard.KEY_B, "Transformers");
	public static KeyBinding keyBindingStealthMode = new KeyBinding("Stealth Mode", Keyboard.KEY_V, "Transformers");
	public static KeyBinding keyBindingVehicleFirstPerson = new KeyBinding("Vehicle First Person", Keyboard.KEY_G, "Transformers");
	
	public static Field camRollField;
	
	public static ModelChildBase.Biped getVurp()
	{
		return modelVurp;
	}
	
	public static ModelChildBase.Biped getSubwoofer()
	{
		return modelSubwoofer;
	}
	
	public static ModelChildBase.Biped getSkystrike()
	{
		return modelSkystrike;
	}

	public static ModelChildBase.Biped getCloudtrap()
	{
		return modelCloudTrap;
	}
	
	public static ModelChildBase.Biped getPurge()
	{
		return modelPurge;
	}
	
	@Override
	public void registerRenderInformation()
	{
        RenderCustomPlayer renderCustomPlayer = new RenderCustomPlayer();
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityPlayer.class, renderCustomPlayer);

        RenderPlayer playerRenderer = (RenderPlayer)Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityPlayer.class);
        ModelBipedTF newModel = new ModelBipedTF(0.0F);

        try
        {
            RenderPlayer.class.getField("mainModel").setAccessible(true);
            RendererLivingEntity.class.getField("mainModel").set(playerRenderer, newModel);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        int i = 0;
		for (Field curField : EntityRenderer.class.getDeclaredFields())
		{
			if (curField.getType() == float.class)
			{
				if (++i == 15)
				{
					camRollField = curField;
					curField.setAccessible(true);
				}
			}
		}
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTankShell.class, new RenderTankShell());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, new RenderMissile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayPillar.class, new RenderDisplayPillar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new RenderCrystal());
		
		
		MinecraftForgeClient.registerItemRenderer(TFItems.purgesKatana, new RenderItemPurgesKatana());
		MinecraftForgeClient.registerItemRenderer(TFItems.skystrikesCrossbow, new RenderItemSkystrikesCrossbow());
		
		MinecraftForgeClient.registerItemRenderer(TFItems.displayVehicle, new RenderItemDisplayVehicle());
		MinecraftForgeClient.registerItemRenderer(TFItems.flamethrower, new RenderItemFlamethrower());
	}
	
	@Override
	public void registerTickHandler()
	{
		tickHandler = new ClientTickHandler();
	}
	
	@Override
	public void registerKeyBinds()
	{
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindingBrake);
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindingNitro);
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindingTransform);
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindingStealthMode);
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindingZoom);
		ClientRegistry.registerKeyBinding(ClientProxy.keyBindingVehicleFirstPerson);
	}
	
	public ModelBiped getArmorModel(String string)
	{
		if (string.equalsIgnoreCase("Skystrike"))
		{
			return getSkystrike();
		}
		else if (string.equalsIgnoreCase("Purge"))
		{
			return getPurge();
		}
		else if (string.equalsIgnoreCase("Cloudtrap"))
		{
			return getCloudtrap();
		}
		else if (string.equalsIgnoreCase("Vurp"))
		{
			return getVurp();
		}
		else if (string.equalsIgnoreCase("Subwoofer"))
		{
			return getSubwoofer();
		}
		return null;
	}
}