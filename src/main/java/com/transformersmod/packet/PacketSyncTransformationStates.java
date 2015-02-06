package com.transformersmod.packet;

import com.transformersmod.data.TFPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class PacketSyncTransformationStates extends AbstractPacket<PacketSyncTransformationStates>
{
	private Map<UUID, Boolean[]> states;

	public PacketSyncTransformationStates()
	{

	}

	public PacketSyncTransformationStates(Map<UUID, Boolean[]> states)
	{
		this.states = states;
	}

    public IMessage handleClientMessage(PacketSyncTransformationStates message, EntityPlayer player)
    {
        if (states != null)
        {
            for (Object cPlayer : Minecraft.getMinecraft().theWorld.playerEntities)
            {
                if (cPlayer instanceof EntityPlayer)
                {
                    for (Entry<UUID, Boolean[]> state : states.entrySet())
                    {
                        EntityPlayer currentPlayer = (EntityPlayer) cPlayer;

                        UUID uuid = state.getKey();
                        if (uuid != null && uuid.equals(currentPlayer.getUniqueID()))
                        {
                            TFPlayerData.getData(currentPlayer).mode = state.getValue()[0];
                            TFPlayerData.getData(currentPlayer).stealthMode = state.getValue()[1];
                            //TFDataManager.setTransformationTimer(currentPlayer, state.getValue() ? 0 : 10);
                        }
                    }
                }
            }
        }

        return null;
    }

    public IMessage handleServerMessage(PacketSyncTransformationStates message, EntityPlayer player)
    {
        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        states = new HashMap<UUID, Boolean[]>();

        int count = buf.readInt();

        for (int i = 0; i < count; i++)
        {
            states.put(UUID.fromString(ByteBufUtils.readUTF8String(buf)), new Boolean[]{buf.readBoolean(), buf.readBoolean()});
        }
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(states.size());

        for (Entry<UUID, Boolean[]> entry : states.entrySet())
        {
            ByteBufUtils.writeUTF8String(buf, entry.getKey().toString());
            buf.writeBoolean(entry.getValue()[0]);
            buf.writeBoolean(entry.getValue()[1]);
        }
    }
}