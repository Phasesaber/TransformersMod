package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import com.transformersmod.tick.ClientTickHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCloudtrapJetpack extends AbstractPacket<PacketCloudtrapJetpack>
{
	private int id;
	private boolean jetpacking;

	public PacketCloudtrapJetpack()
	{

	}

	public PacketCloudtrapJetpack(EntityPlayer player, boolean jetpacking)
	{
		this.id = player.getEntityId();
		this.jetpacking = jetpacking;
	}

    public IMessage handleClientMessage(PacketCloudtrapJetpack message, EntityPlayer player)
    {
        EntityPlayer from = null;

        Entity entity = player.worldObj.getEntityByID(id);

        if (entity instanceof EntityPlayer)
        {
            from = (EntityPlayer) entity;
        }

        if (from != null && from != Minecraft.getMinecraft().thePlayer)
        {
            if (jetpacking)
            {
                if (!ClientTickHandler.cloudtrapJetpacking.contains(from))
                {
                    ClientTickHandler.cloudtrapJetpacking.add(from);
                }
            }
            else
            {
                ClientTickHandler.cloudtrapJetpacking.remove(from);
            }
        }

        return null;
    }

    public IMessage handleServerMessage(PacketCloudtrapJetpack message, EntityPlayer player)
    {
        EntityPlayer from = null;

        for (World world : MinecraftServer.getServer().worldServers)
        {
            Entity entity = world.getEntityByID(id);

            if (entity instanceof EntityPlayer)
            {
                from = (EntityPlayer) entity;
                break;
            }
        }

        if (from != null)
        {
            TransformersMod.networkWrapper.sendToAll(this);
        }

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.id = buf.readInt();
        this.jetpacking = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(jetpacking);
    }
}