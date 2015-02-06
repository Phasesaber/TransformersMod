package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.data.TFPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketBroadcastTransformationState extends AbstractPacket<PacketBroadcastTransformationState>
{
	private int id;
	
	private boolean vehicle;
	
	public PacketBroadcastTransformationState()
	{
		
	}

	public PacketBroadcastTransformationState(EntityPlayer player)
	{
		this.id = player.getEntityId();
		this.vehicle = TFDataManager.isInVehicleMode(player);
	}

    public IMessage handleClientMessage(PacketBroadcastTransformationState message, EntityPlayer player)
    {
        Entity lookupEntity = player.worldObj.getEntityByID(id);

        if (lookupEntity instanceof EntityPlayer && player != lookupEntity)
        {
            EntityPlayer lookupPlayer = (EntityPlayer) lookupEntity;

            TFPlayerData playerData = TFPlayerData.getData(lookupPlayer);
            playerData.mode = vehicle;
            TFDataManager.setTransformationTimer(lookupPlayer, vehicle ? 0 : 10);
        }

        return null;
    }

    public IMessage handleServerMessage(PacketBroadcastTransformationState message, EntityPlayer player)
    {
        TransformersMod.networkWrapper.sendToDimension(this, player.dimension);
        TFPlayerData playerData = TFPlayerData.getData(player);
        playerData.mode = vehicle;

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        vehicle = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(vehicle);
    }
}
