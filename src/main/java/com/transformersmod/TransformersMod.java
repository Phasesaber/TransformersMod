package com.transformersmod;

import com.transformersmod.achievement.TFAchievements;
import com.transformersmod.block.TFBlocks;
import com.transformersmod.config.TFConfig;
import com.transformersmod.donator.Donators;
import com.transformersmod.entity.TFEntities;
import com.transformersmod.event.TFEvents;
import com.transformersmod.generator.OreWorldGenerator;
import com.transformersmod.item.TFItems;
import com.transformersmod.misc.CreativeTabTransformers;
import com.transformersmod.packet.TFPackets;
import com.transformersmod.proxy.CommonProxy;
import com.transformersmod.recipe.TFRecipes;
import com.transformersmod.transformer.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.lang.reflect.Method;

@Mod(modid = TransformersMod.modid, name = "Transformers Mod", version = TransformersMod.version, guiFactory = "com.transformersmod.gui.TFGuiFactory")
public class TransformersMod
{
	@Mod.Instance(TransformersMod.modid)
	public static TransformersMod instance;
	
	public static Configuration configFile;
	
	public static final String modid = "transformers";
	public static final String version = "0.6.0";
	
	public static SimpleNetworkWrapper networkWrapper;
	
	@SidedProxy(clientSide = "com.transformersmod.proxy.ClientProxy", serverSide = "com.transformersmod.proxy.CommonProxy")
	public static CommonProxy proxy;

	public TFConfig config = new TFConfig();
	public TFItems items = new TFItems();
	public TFBlocks blocks = new TFBlocks();

    //public static Update currentVersion;
    //public static Update newestVersion;
	
	public static CreativeTabs transformersTab = new CreativeTabTransformers();
	
	public static Method setSizeMethod;
	
	public static Transformer transformerPurge = new TransformerPurge();
	public static Transformer transformerSkystrike = new TransformerSkystrike();
	public static Transformer transformerCloudtrap = new TransformerCloudtrap();
	public static Transformer transformerVurp = new TransformerVurp();
	public static Transformer transformerSubwoofer = new TransformerSubwoofer();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException
    {
        //currentVersion = new Gson().fromJson(new InputStreamReader(TransformersMod.class.getResourceAsStream("version.json")), Update.class);
        //newestVersion = new Gson().fromJson(new BufferedReader(new InputStreamReader(new URL("https://ilexiconn.net/transformersmod/version.json").openStream())), Update.class);
		Donators.loadDonators();
		
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.load();
		config.load(configFile);
		configFile.save();
		
		items.load();
		blocks.load();

		TFAchievements.register();
		
		TFRecipes.registerRecipes();
		
		TFEntities.registerEntities();
		
		GameRegistry.registerWorldGenerator(new OreWorldGenerator(), 0);
		
		for (Method method : Entity.class.getDeclaredMethods())
		{
			Class<?>[] parameters = method.getParameterTypes();
			
			if (parameters.length == 2)
			{
				if (parameters[0] == float.class && parameters[1] == float.class)
				{
					method.setAccessible(true);
					setSizeMethod = method;
					break;
				}
			}
		}

		TFPackets.registerPackets();
	}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerRenderInformation();
        proxy.registerKeyBinds();
        proxy.registerTickHandler();
        TFEvents.registerEvents(event.getSide());
    }
}