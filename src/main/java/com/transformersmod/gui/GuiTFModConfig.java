package com.transformersmod.gui;

import com.transformersmod.TransformersMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiTFModConfig extends GuiConfig
{
	public GuiTFModConfig(GuiScreen parent)
	{
		super(
			parent,
			new ConfigElement(TransformersMod.configFile.getCategory("Options")).getChildElements(),
			TransformersMod.modid,
			false,
			false,
			GuiConfig.getAbridgedConfigPath(TransformersMod.configFile.toString()));
	}
}