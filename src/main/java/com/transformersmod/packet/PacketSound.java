package com.transformersmod.packet;

import com.transformersmod.TransformersMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

//Not in use
public class PacketSound extends AbstractPacket<PacketSound>
{
	private String sound;
	private float volume;
	private float pitch;
	
	private int id;
	
	public PacketSound()
	{
		
	}
	
	public PacketSound(String sound, Entity entity, float volume, float pitch)
	{
		this.id = entity.getEntityId();
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

    public IMessage handleClientMessage(PacketSound message, EntityPlayer player)
    {
        Entity entity = player.worldObj.getEntityByID(id);

        if (entity != null)
        {
            entity.worldObj.playSound(entity.posX, entity.posY - entity.getYOffset(), entity.posZ, TransformersMod.modid + ":" + sound, 1, 1, false);
        }

        return null;
    }

    public IMessage handleServerMessage(PacketSound message, EntityPlayer player)
    {
        TransformersMod.networkWrapper.sendToAll(this);

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        sound = ByteBufUtils.readUTF8String(buf);
        id = buf.readInt();
        volume = buf.readFloat();
        pitch = buf.readFloat();
    }

    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, sound);
        buf.writeInt(id);
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
    }
}
