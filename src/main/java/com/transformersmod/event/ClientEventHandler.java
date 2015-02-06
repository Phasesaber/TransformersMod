package com.transformersmod.event;

import com.transformersmod.TFHelper;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.misc.TFMotionManager;
import com.transformersmod.misc.VehicleMotion;
import com.transformersmod.proxy.ClientProxy;
import com.transformersmod.render.entity.CustomEntityRenderer;
import com.transformersmod.transformer.Transformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class ClientEventHandler
{
	private final Minecraft mc = Minecraft.getMinecraft();
	private EntityRenderer renderer, prevRenderer;

	@SubscribeEvent
	public void onPlaySound(PlaySoundAtEntityEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			if (event.name.startsWith("step.") && TFDataManager.isInVehicleMode((EntityPlayer) event.entity))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event)
	{
		if (TFDataManager.getTransformationTimer(event.entityPlayer) < 10)
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = event.entityPlayer;

		Transformer transformer = TFHelper.getTransformer(player);

		boolean isClientPlayer = mc.thePlayer == player;
		boolean isTransformer = TFHelper.isPlayerTransformer(player);
		boolean inVehicleMode = TFDataManager.isInVehicleMode(player);
		int transformationTimer = TFDataManager.getTransformationTimer(player);
		boolean isTransformerAndInVehicleMode = isTransformer && inVehicleMode;

		float cameraYOffset = 0;

		if (transformer != null)
		{
			cameraYOffset = transformer.getCameraYOffset();
		}
		
		if (isClientPlayer && cameraYOffset != 0)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0, -CustomEntityRenderer.getOffsetY(player), 0);
		}

		//TODO?
				//			if (player.isSneaking() && TFDataManager.getTransformationTimer(player) < 10)
		//			{
		//				if (jet)
		//				{
		//					GL11.glTranslatef(0, 0.002F, 0);
		//				}
		//				else
		//				{
		//					GL11.glTranslatef(0, 0.08F, 0);
		//				}
		//			}
	}

	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Post event)
	{
		EntityPlayer player = event.entityPlayer;

		Transformer transformer = TFHelper.getTransformer(player);

		boolean isClientPlayer = mc.thePlayer == player;

		if (transformer != null)
		{
			if (isClientPlayer && transformer.getCameraYOffset() != 0)
			{
				GL11.glPopMatrix();
			}
		}

		ModelBiped modelBipedMain = ObfuscationReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[]{"f", "modelBipedMain"});
		ClientProxy.modelBipedMain = modelBipedMain;

		//	TFHelper.getInstance().adjustPlayerVisibility(event.entityPlayer, modelBipedMain);

		ObfuscationReflectionHelper.setPrivateValue(RenderPlayer.class, event.renderer, modelBipedMain, "f", "modelBipedMain");
	}

	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.theWorld != null)
		{
			if (event.phase == TickEvent.Phase.START)
			{
                EntityPlayerSP player = mc.thePlayer;

				Transformer transformer = TFHelper.getTransformer(player);

				if (transformer != null)
				{
					if (transformer.getCameraYOffset() != 0)
					{
						if (renderer == null)
						{
							renderer = new CustomEntityRenderer(mc);
						}

						if (mc.entityRenderer != renderer)
						{
							prevRenderer = mc.entityRenderer;
							mc.entityRenderer = renderer;
						}
					}
					else if (prevRenderer != null && mc.entityRenderer != prevRenderer)
					{
						mc.entityRenderer = prevRenderer;
					}
				}
				else if (prevRenderer != null && mc.entityRenderer != prevRenderer)
				{
					mc.entityRenderer = prevRenderer;
				}
			}
		}
	}

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event)
	{
		EntityPlayerSP player = (EntityPlayerSP) event.entity;
		VehicleMotion transformedPlayer = TFMotionManager.getTransformerPlayer(player);

		int nitro = transformedPlayer == null ? 0 : transformedPlayer.getNitro();
		boolean moveForward = Minecraft.getMinecraft().gameSettings.keyBindForward.isPressed();
		boolean nitroPressed = ClientProxy.keyBindingNitro.isPressed() || Minecraft.getMinecraft().gameSettings.keyBindSprint.isPressed();

		if (TFDataManager.isInVehicleMode(player))
		{
			if (nitro > 0 && moveForward && nitroPressed && !TFDataManager.isInStealthMode(player))
			{
				event.newfov = 1.3F;
			}
		}
	}
}