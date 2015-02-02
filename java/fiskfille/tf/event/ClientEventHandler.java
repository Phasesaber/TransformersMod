package fiskfille.tf.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fiskfille.tf.TFHelper;
import fiskfille.tf.data.TFDataManager;
import fiskfille.tf.misc.TFMotionManager;
import fiskfille.tf.misc.VehicleMotion;
import fiskfille.tf.proxy.ClientProxy;
import fiskfille.tf.render.entity.CustomEntityRenderer;

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

		boolean jet = TFHelper.isPlayerJet(player);

		boolean isClientPlayer = mc.thePlayer == player;
		boolean isTransformer = TFHelper.isPlayerTransformer(player);
		boolean inVehicleMode = TFDataManager.isInVehicleMode(player);
		boolean halfTransformed = TFDataManager.getTransformationTimer(player) <= 10;
		boolean isTransformerAndInVehicleMode = isTransformer && inVehicleMode;

		boolean notMainClientPlayer = !(player instanceof EntityClientPlayerMP) && !(isClientPlayer);

		if(notMainClientPlayer && isTransformerAndInVehicleMode && halfTransformed)
		{
			GL11.glPushMatrix();
			if(TFHelper.isPlayerCar(player))
			{
				GL11.glTranslatef(0, -1.1F, 0);
			}
			else
			{
				GL11.glTranslatef(0, -1, 0);
			}
		}

		if (!jet)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0, -CustomEntityRenderer.getOffsetY(player), 0);
		}
		
		if (player.isSneaking() && TFDataManager.getTransformationTimer(player) < 10)
		{
			if(jet)
			{
				GL11.glTranslatef(0, 0.002F, 0);
			}
			else
			{
				GL11.glTranslatef(0, 0.08F, 0);
			}
		}
	}

	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Post event)
	{
		EntityPlayer player = event.entityPlayer;

		boolean isClientPlayer = mc.thePlayer == player;
		boolean isTransformer = TFHelper.isPlayerTransformer(player);
		boolean inVehicleMode = TFDataManager.isInVehicleMode(player);
		boolean halfTransformed = TFDataManager.getTransformationTimer(player) <= 10;
		boolean isTransformerAndInVehicleMode = isTransformer && inVehicleMode;

		boolean notMainClientPlayer = !(player instanceof EntityClientPlayerMP) && !(isClientPlayer);

		if(notMainClientPlayer && isTransformerAndInVehicleMode && halfTransformed)
		{
			GL11.glPopMatrix();
		}

		if (!(TFHelper.isPlayerJet(player)))
		{
			GL11.glPopMatrix();
		}

		ModelBiped modelBipedMain = ObfuscationReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[]{"f", "modelBipedMain"});
		ClientProxy.modelBipedMain = modelBipedMain;

		//	TFHelper.getInstance().adjustPlayerVisibility(event.entityPlayer, modelBipedMain);

		ObfuscationReflectionHelper.setPrivateValue(RenderPlayer.class, event.renderer, modelBipedMain, new String[]{"f", "modelBipedMain"});
	}

	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.theWorld != null)
		{
			if (event.phase == TickEvent.Phase.START)
			{				
				EntityClientPlayerMP player = mc.thePlayer;

				if (!(TFHelper.isPlayerJet(player)))
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
		}
	}

	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event)
	{
		EntityPlayerSP player = event.entity;
		VehicleMotion transformedPlayer = TFMotionManager.transformedPlayerMap.get(player);

		int nitro = transformedPlayer == null ? 0 : transformedPlayer.getNitro();
		boolean moveForward = Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed();
		boolean nitroPressed = ClientProxy.keyBindingNitro.getIsKeyPressed() || Minecraft.getMinecraft().gameSettings.keyBindSprint.getIsKeyPressed();

		if(TFDataManager.isInVehicleMode(player))
		{
			if (nitro > 0 && moveForward && nitroPressed && !TFDataManager.isInStealthMode(player))
			{
				event.newfov = 1.3F;
			}

			if(TFHelper.isPlayerPurge(player))
			{
				if(ClientProxy.keyBindingZoom.getIsKeyPressed())
				{
					//event.newfov = 0.2F;
				}
			}
		}
	}
}