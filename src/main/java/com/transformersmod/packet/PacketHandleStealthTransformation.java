package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.data.TFPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketHandleStealthTransformation extends AbstractPacket<PacketHandleStealthTransformation>
{
	public int id;
	private boolean mode;

	public PacketHandleStealthTransformation()
	{

	}

	public PacketHandleStealthTransformation(EntityPlayer player, boolean mode)
	{
		this.id = player.getEntityId();
		this.mode = mode;
	}

    public IMessage handleClientMessage(PacketHandleStealthTransformation message, EntityPlayer player)
    {
        EntityPlayer from = null;

        Entity entity = player.worldObj.getEntityByID(id);

        if (entity instanceof EntityPlayer)
        {
            from = (EntityPlayer) entity;
        }

        if (from != null && from != Minecraft.getMinecraft().thePlayer)
        {
            TFPlayerData playerData = TFPlayerData.getData(from);
            TFDataManager.setStealthModeTimer(from, mode ? 5 : 0);

            String suffix = mode ? "vehicle" : "robot";

            from.worldObj.playSound(from.posX, from.posY - (double)from.renderOffsetY, from.posZ, TransformersMod.modid + ":transform_" + suffix, 1, 1.5F, false);

            playerData.stealthMode = mode;
        }

        return null;
    }

    public IMessage handleServerMessage(PacketHandleStealthTransformation message, EntityPlayer player)
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
            TFDataManager.setInStealthMode(player, mode);
        }

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.id = buf.readInt();
        this.mode = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(mode);
    }
}