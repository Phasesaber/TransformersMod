package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class TFPackets 
{
	public static void registerPackets()
	{
		TransformersMod.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("tfNetworkWrapper");
		TransformersMod.networkWrapper.registerMessage(PacketHandleTransformation.class, PacketHandleTransformation.class, 0, Side.SERVER);
		TransformersMod.networkWrapper.registerMessage(PacketHandleStealthTransformation.class, PacketHandleStealthTransformation.class, 1, Side.SERVER);
		
		TransformersMod.networkWrapper.registerMessage(PacketSyncTransformationStates.class, PacketSyncTransformationStates.class, 2, Side.SERVER);
		TransformersMod.networkWrapper.registerMessage(PacketTransformersAction.class, PacketTransformersAction.class, 3, Side.SERVER);
		
		TransformersMod.networkWrapper.registerMessage(PacketCloudtrapJetpack.class, PacketCloudtrapJetpack.class, 4, Side.SERVER);
		
		TransformersMod.networkWrapper.registerMessage(PacketBroadcastStealthState.class, PacketBroadcastStealthState.class, 5, Side.SERVER);
		TransformersMod.networkWrapper.registerMessage(PacketBroadcastTransformationState.class, PacketBroadcastTransformationState.class, 6, Side.SERVER);
		
		TransformersMod.networkWrapper.registerMessage(PacketBroadcastState.class, PacketBroadcastState.class, 6, Side.SERVER);
		
		TransformersMod.networkWrapper.registerMessage(PacketVehicleNitro.class, PacketVehicleNitro.class, 7, Side.SERVER);
	}
}
