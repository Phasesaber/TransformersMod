package com.transformersmod.tick;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.config.TFConfig;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.misc.TFMotionManager;
import com.transformersmod.misc.TFNitroParticleHandler;
import com.transformersmod.misc.VehicleMotion;
import com.transformersmod.packet.PacketCloudtrapJetpack;
import com.transformersmod.proxy.ClientProxy;
import com.transformersmod.render.entity.CustomEntityRenderer;
import com.transformersmod.transformer.Transformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientTickHandler
{
	public static List<EntityPlayer> cloudtrapJetpacking = new ArrayList<EntityPlayer>();
	private Minecraft mc = Minecraft.getMinecraft();
	private boolean prevJetpacking;

	public void onPlayerTick(EntityPlayer player)
	{	
		Transformer transformer = TFHelper.getTransformer(player);

		boolean inVehicleMode = TFDataManager.isInVehicleMode(player);

		int transformationTimer = TFDataManager.getTransformationTimer(player);
		float offsetY = getCameraOffset(player, transformer) + (float)transformationTimer / 20;

		CustomEntityRenderer.setOffsetY(player, offsetY);

		int stealthModeTimer = TFDataManager.getStealthModeTimer(player);

		if (stealthModeTimer < 5 && !TFDataManager.isInStealthMode(player))
		{
			TFDataManager.setStealthModeTimer(player, stealthModeTimer + 1);
		}
		if (stealthModeTimer > 0 && TFDataManager.isInStealthMode(player))
		{
			TFDataManager.setStealthModeTimer(player, stealthModeTimer - 1);
		}

		VehicleMotion transformedPlayer = TFMotionManager.getTransformerPlayer(player);

		if (inVehicleMode && transformationTimer < 10)
		{
			player.setSprinting(false);

			if (player == Minecraft.getMinecraft().thePlayer)
			{
				GameSettings gameSettings = mc.gameSettings;

				if (Keyboard.isKeyDown(Keyboard.KEY_R) && Minecraft.getMinecraft().currentScreen == null)
				{
					gameSettings.thirdPersonView = 2;
				}
				else if (ClientProxy.keyBindingVehicleFirstPerson.isKeyDown())
				{
					gameSettings.thirdPersonView = 0;
				}
				else
				{
					gameSettings.thirdPersonView = 3;
				}

				if(transformer != null)
				{
					transformer.updateMovement(player);
				}
			}

			TFNitroParticleHandler.doNitroParticles(player);
		}
		else
		{
			if (transformer != null)
			{
				if (transformer.hasJetpack())
				{
					boolean isClientPlayer = mc.thePlayer == player;
					boolean jetpacking = mc.gameSettings.keyBindJump.isKeyDown();

					if (isClientPlayer)
					{
						if (prevJetpacking != jetpacking)
						{
							TransformersMod.networkWrapper.sendToServer(new PacketCloudtrapJetpack(player, jetpacking));
							prevJetpacking = jetpacking;
						}
					}
					else
					{
						for (EntityPlayer cPlayer : cloudtrapJetpacking)
						{
							for (int i = 0; i < 20; ++i)
							{
								Random rand = new Random();
								cPlayer.worldObj.spawnParticle(EnumParticleTypes.FLAME, cPlayer.posX, cPlayer.posY, cPlayer.posZ, rand.nextFloat() / 4 - 0.125F, -0.8F, rand.nextFloat() / 4 - 0.125F);
							}
						}
					}

					if (jetpacking)
					{
						player.motionY += 0.09F;

						if (isClientPlayer)
						{
							for (int i = 0; i < 20; ++i)
							{
								Random rand = new Random();
								player.worldObj.spawnParticle(EnumParticleTypes.FLAME, player.posX, player.posY - 1.5F, player.posZ, rand.nextFloat() / 4 - 0.125F, -0.8F, rand.nextFloat() / 4 - 0.125F);
							}
						}
					}
				}
			}

			player.stepHeight = 0.5F;

			if (player.isPotionActive(Potion.resistance) && player.getActivePotionEffect(Potion.resistance).getDuration() < 1)
			{
				player.removePotionEffect(Potion.resistance.id);
			}
		}

		int nitro = transformedPlayer == null ? 0 : transformedPlayer.getNitro();
		boolean moveForward = Minecraft.getMinecraft().gameSettings.keyBindForward.isPressed();
		boolean nitroPressed = ClientProxy.keyBindingNitro.isPressed() || Minecraft.getMinecraft().gameSettings.keyBindSprint.isPressed();

		if (nitro < 160 && !((nitroPressed && !TFDataManager.isInStealthMode(player)) && moveForward && inVehicleMode && transformationTimer < 10))
		{
			++nitro;
		}

		TFMotionManager.setNitro(player, nitro);

		if(transformer == null && inVehicleMode)
		{
			TFDataManager.setInVehicleMode(player, false);
		}
	}

	private float getCameraOffset(EntityPlayer player, Transformer transformer) 
	{
		if (transformer != null && TFDataManager.getTransformationTimer(player) > 10)
		{
			return transformer.getCameraYOffset();
		}
		else
		{
			return -1;
		}
	}

	public void handleTransformation(EntityPlayer player)
	{
		Transformer transformer = TFHelper.getTransformer(player);

		int transformationTimer = TFDataManager.getTransformationTimer(player);
		boolean inVehicleMode = TFDataManager.isInVehicleMode(player);

        if (transformationTimer < 20 && !inVehicleMode)
		{
			transformationTimer++;
			
			TFDataManager.setTransformationTimer(player, transformationTimer);

			if(transformer != null)
			{
				transformer.transformationTick(player, transformationTimer);
			}

			TFMotionManager.setForwardVelocity(player, 0.0D);

			if (transformationTimer == 19)
			{
				if (Minecraft.getMinecraft().thePlayer == player)
				{
					Minecraft.getMinecraft().gameSettings.thirdPersonView = TFConfig.firstPersonAfterTransformation ? 0 : Minecraft.getMinecraft().gameSettings.thirdPersonView;
				}
			}
		}
		else if (transformationTimer > 0 && inVehicleMode)
		{
			transformationTimer--;
			
			TFDataManager.setTransformationTimer(player, transformationTimer);

			if(transformer != null)
			{
				transformer.transformationTick(player, transformationTimer);
			}
		}
	}

	public void onClientTickEnd()
	{
		EntityPlayer player = mc.thePlayer;

		try
		{
			Transformer transformer = TFHelper.getTransformer(player);

			float thirdPersonDistance;

			if (transformer != null && (transformer.canZoom(player)) && TFDataManager.isInVehicleMode(player) && ClientProxy.keyBindingZoom.isPressed())
			{
				thirdPersonDistance = transformer.getZoomAmount(player);
			}
			else
			{
                assert transformer != null;
                thirdPersonDistance = transformer.getThirdPersonDistance(player);
			}

			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, thirdPersonDistance, "thirdPersonDistance", "E", "field_78490_B");
		}
		catch (Exception e) {}
	}

	public void onClientTickStart()
	{

	}
}