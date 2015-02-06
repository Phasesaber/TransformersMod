package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.data.TFPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketHandleTransformation extends AbstractPacket<PacketHandleTransformation>
{
	public int id;
	private boolean mode;

	public PacketHandleTransformation()
	{

	}

	public PacketHandleTransformation(EntityPlayer player, boolean mode)
	{
		this.id = player.getEntityId();
		this.mode = mode;
	}

    public IMessage handleClientMessage(PacketHandleTransformation message, EntityPlayer player)
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

            playerData.stealthMode = false;

            TFDataManager.setTransformationTimer(from, mode ? 20 : 0);
            String suffix = mode ? "vehicle" : "robot";

            from.worldObj.playSound(from.posX, from.posY - (double)from.renderOffsetY, from.posZ, TransformersMod.modid + ":transform_" + suffix, 1, 1, false);

            playerData.mode = mode;
        }

        return null;
    }

    public IMessage handleServerMessage(PacketHandleTransformation message, EntityPlayer player)
    {
        //		EntityPlayer from = null;
        //
        //		player.addStat(TFAchievements.transform, 1);
        //
        //		for (World world : MinecraftServer.getServer().worldServers)
        //		{
        //			Entity entity = world.getEntityByID(id);
        //
        //			if (entity instanceof EntityPlayer)
        //			{
        //				from = (EntityPlayer) entity;
        //				break;
        //			}
        //		}

        if (player != null)
        {
            TFDataManager.setInVehicleMode(player, mode);

            TransformersMod.networkWrapper.sendToDimension(new PacketBroadcastStealthState(player), player.dimension);
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