package com.transformersmod.tick;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.data.TFPlayerData;
import com.transformersmod.misc.TFMotionManager;
import com.transformersmod.proxy.ClientProxy;
import com.transformersmod.proxy.CommonProxy;
import com.transformersmod.transformer.Transformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler
{
	public static int time = 0;

    public static boolean prevViewBobbing;
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		boolean inVehicleMode = TFDataManager.isInVehicleMode(player);
		if (ClientProxy.keyBindingTransform.isPressed() && Minecraft.getMinecraft().currentScreen == null && (TFHelper.isPlayerTransformer(player)))
		{
			GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
			int transformationTimer = TFDataManager.getTransformationTimer(player);
			
			if (inVehicleMode && transformationTimer == 0)
			{
				TFDataManager.setInVehicleMode(player, false);
				gameSettings.viewBobbing = prevViewBobbing;
				player.playSound(TransformersMod.modid + ":transform_robot", 1.0F, 1.0F);
				TFPlayerData.getData(player).stealthMode = false;
			}
			else if (!inVehicleMode && transformationTimer == 20)
			{
				TFDataManager.setInVehicleMode(player, true);
				prevViewBobbing = gameSettings.viewBobbing;
				gameSettings.viewBobbing = false;
				player.playSound(TransformersMod.modid + ":transform_vehicle", 1.0F, 1.0F);
				TFPlayerData.getData(player).stealthMode = false;
				TFMotionManager.resetPlayer(player);
			}

			EntityRenderer entityRenderer = Minecraft.getMinecraft().entityRenderer;

			try
			{
                ClientProxy.camRollField.set(entityRenderer, 0);
			}
			catch (IllegalArgumentException e) 
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}

		if (ClientProxy.keyBindingStealthMode.isPressed())
		{
			Transformer transformer = TFHelper.getTransformer(player);
			
			if (transformer != null)
			{
				if (inVehicleMode && Minecraft.getMinecraft().currentScreen == null && transformer.hasStealthForce(player))
				{
					int stealthModeTimer = TFDataManager.getStealthModeTimer(player);
					
					if (TFDataManager.isInStealthMode(player) && stealthModeTimer == 0)
					{
						TFDataManager.setInStealthMode(player, false);
						player.playSound(TransformersMod.modid + ":transform_robot", 1.0F, 1.5F);
					}
					else if (!TFDataManager.isInStealthMode(player) && stealthModeTimer == 5)
					{
						TFDataManager.setInStealthMode(player, true);
						player.playSound(TransformersMod.modid + ":transform_vehicle", 1.0F, 1.5F);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		++time;
		EntityPlayer player = event.player;

		if (player.worldObj.isRemote)
		{
			if (time % 2 == 0)
			{
				CommonProxy.tickHandler.onPlayerTick(player);
			}
			
			CommonProxy.tickHandler.handleTransformation(player);
		}
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		switch (event.phase)
		{
            case START:
            {
                CommonProxy.tickHandler.onClientTickStart();
                break;
            }
            case END:
            {
                CommonProxy.tickHandler.onClientTickEnd();
                break;
            }
		}		
	}
}