package com.transformersmod.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractPacket<REQ extends AbstractPacket> implements IMessage, IMessageHandler<REQ, IMessage>
{
    public IMessage onMessage(REQ message, MessageContext ctx)
    {
        if (ctx.side == Side.CLIENT) return handleClientMessage(message, Minecraft.getMinecraft().thePlayer);
        else return handleServerMessage(message, ctx.getServerHandler().playerEntity);
    }

    public abstract IMessage handleClientMessage(REQ message, EntityPlayer player);

    public abstract IMessage handleServerMessage(REQ message, EntityPlayer player);
}