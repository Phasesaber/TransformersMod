package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.data.TFPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketBroadcastState extends AbstractPacket<PacketBroadcastState>
{
	private int id;
	
	private boolean stealth;
	private boolean vehicle;
	
	public PacketBroadcastState()
	{
		
	}
	
	public PacketBroadcastState(EntityPlayer player)
	{
		this.id = player.getEntityId();
		this.stealth = TFDataManager.isInStealthMode(player);
		this.vehicle = TFDataManager.isInVehicleMode(player);
	}

    public IMessage handleClientMessage(PacketBroadcastState message, EntityPlayer player)
    {
        Entity lookupEntity = player.worldObj.getEntityByID(id);

        if (lookupEntity instanceof EntityPlayer && player != lookupEntity)
        {
            EntityPlayer lookupPlayer = (EntityPlayer) lookupEntity;

            TFPlayerData playerData = TFPlayerData.getData(lookupPlayer);
            playerData.mode = vehicle;
            TFDataManager.setTransformationTimer(lookupPlayer, vehicle ? 0 : 10);
            playerData.stealthMode = stealth;
            TFDataManager.setStealthModeTimer(lookupPlayer, stealth ? 0 : 5);
        }

        return null;
    }

    public IMessage handleServerMessage(PacketBroadcastState message, EntityPlayer player)
    {
        TransformersMod.networkWrapper.sendToDimension(this, player.dimension);
        TFPlayerData playerData = TFPlayerData.getData(player);
        playerData.mode = vehicle;
        playerData.stealthMode = stealth;

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        stealth = buf.readBoolean();
        vehicle = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(stealth);
        buf.writeBoolean(vehicle);
    }
}
