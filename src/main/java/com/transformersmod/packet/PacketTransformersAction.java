package com.transformersmod.packet;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.data.TFDataManager;
import com.transformersmod.item.TFItems;
import com.transformersmod.transformer.Transformer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketTransformersAction extends AbstractPacket<PacketTransformersAction>
{
	public int id;
	private PlayerInteractEvent.Action action;

	public PacketTransformersAction()
	{

	}

	public PacketTransformersAction(EntityPlayer player, PlayerInteractEvent.Action action)
	{
		this.id = player.getEntityId();
		this.action = action;
	}

    public IMessage handleClientMessage(PacketTransformersAction message, EntityPlayer player)
    {
        Entity fromEntity = player.worldObj.getEntityByID(id);
        if (fromEntity instanceof EntityPlayer)
        {
            EntityPlayer from = (EntityPlayer) fromEntity;

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                Transformer transformer = TFHelper.getTransformer(player);

                if (transformer != null)
                {
                    String shootSound = transformer.getShootSound();

                    if (shootSound != null && TFDataManager.isInVehicleMode(from))
                    {
                        from.worldObj.playSound(from.posX, from.posY - (double)from.renderOffsetY, from.posZ, shootSound, transformer.getShootVolume(), 1, false);
                        //						from.rotationPitch -= 2;
                    }
                }
            }
        }

        return null;
    }

    public IMessage handleServerMessage(PacketTransformersAction message, EntityPlayer player)
    {
        TransformersMod.networkWrapper.sendToAll(this);

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
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
            {
                Transformer transformer = TFHelper.getTransformer(player);

                if (transformer != null)
                {
                    if (transformer.canShoot(player) && TFDataManager.isInVehicleMode(from))
                    {
                        Item shootItem = transformer.getShootItem();

                        boolean isCreative = from.capabilities.isCreativeMode;
                        boolean hasAmmo = isCreative || from.inventory.hasItem(shootItem);

                        if (hasAmmo)
                        {
                            World world = from.worldObj;
                            Entity entity = transformer.getShootEntity(player);
                            entity.posY--;
                            world.spawnEntityInWorld(entity);

                            if (!isCreative)
                            {
                                player.inventory.consumeInventoryItem(TFItems.tankShell);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.id = buf.readInt();
        this.action = PlayerInteractEvent.Action.values()[buf.readInt()];
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        int index = -1;

        int cID = 0;
        for (Action cAction : PlayerInteractEvent.Action.values())
        {
            if (cAction == action)
            {
                index = cID;
                break;
            }
            cID++;
        }

        buf.writeInt(index);
    }
}
