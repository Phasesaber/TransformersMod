package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import com.transformersmod.misc.TFNitroParticleHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketVehicleNitro extends AbstractPacket<PacketVehicleNitro>
{
	private int id;
	private boolean nitroOn;
	
	public PacketVehicleNitro()
	{
		
	}
	
	public PacketVehicleNitro(EntityPlayer player, boolean nitroOn)
	{
		this.id = player.getEntityId();
		this.nitroOn = nitroOn;
	}

    public IMessage handleClientMessage(PacketVehicleNitro message, EntityPlayer player)
    {
        Entity entity = player.worldObj.getEntityByID(id);

        if (entity instanceof EntityPlayer)
        {
            EntityPlayer fromPlayer = (EntityPlayer) entity;

            if (fromPlayer != player)
            {
                TFNitroParticleHandler.setNitro(fromPlayer, nitroOn);
            }
        }

        return null;
    }

    public IMessage handleServerMessage(PacketVehicleNitro message, EntityPlayer player)
    {
        TransformersMod.networkWrapper.sendToDimension(this, player.dimension);

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        nitroOn = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(nitroOn);
    }
}
