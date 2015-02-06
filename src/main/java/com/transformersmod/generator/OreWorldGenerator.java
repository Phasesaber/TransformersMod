package com.transformersmod.generator;

import com.transformersmod.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OreWorldGenerator implements IWorldGenerator
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch (world.provider.getDimensionId())
		{
            case 0:
                generateOverworld(world, random, chunkX * 16, chunkZ * 16);
                break;
		}
	}
	
	public void generateOverworld(World world, Random random, int chunkX, int chunkZ)
	{
		if (world.getWorldInfo().getTerrainType() != WorldType.FLAT)
		{
			generateCrystal(75, TFBlocks.energonCrystal, 48, world, random, chunkX, chunkZ);
		}
		
		generateOre(2, TFBlocks.transformiumOre, 8, 10, world, random, chunkX, chunkZ);
	}
	
	public void generateOre(int veinsPerChunk, Block block, int veinSize, int minY, World world, Random random, int chunkX, int chunkZ)
	{
        for (int i = 0; i < veinsPerChunk; i++)
		{
			int randPosX = chunkX + random.nextInt(16);
			int randPosY = random.nextInt(minY);
			int randPosZ = chunkZ + random.nextInt(16);
			new WorldGenMinable(block.getDefaultState(), veinSize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}
	
	public void generateCrystal(int veinsPerChunk, Block block, int minY, World world, Random random, int chunkX, int chunkZ)
	{
        for (int i = 0; i < veinsPerChunk; i++)
		{
			int randPosX = chunkX + random.nextInt(8);
			int randPosY = random.nextInt(minY);
			int randPosZ = chunkZ + random.nextInt(8);
            BlockPos pos = new BlockPos(randPosX, randPosY, randPosZ);
	        
			if (world.getBlockState(pos).getBlock() == Blocks.air)
			{   				
				if (world.getBlockState(pos.add(1, 0, 0)).getBlock().getMaterial() == Material.rock)
		    	{
					world.setBlockState(pos, block.getDefaultState());
		    		//world.setBlockMetadataWithNotify(randPosX, randPosY, randPosZ, 2, 2);
		    	}
		    	else if (world.getBlockState(pos.add(0, 0, 1)).getBlock().getMaterial() == Material.rock)
		    	{
                    world.setBlockState(pos, block.getDefaultState());
		    		//world.setBlockMetadataWithNotify(randPosX, randPosY, randPosZ, 4, 2);
		    	}
		    	else if (world.getBlockState(pos.add(-1, 0, 0)).getBlock().getMaterial() == Material.rock)
		    	{
                    world.setBlockState(pos, block.getDefaultState());
		    		//world.setBlockMetadataWithNotify(randPosX, randPosY, randPosZ, 1, 2);
		    	}
		    	else if (world.getBlockState(pos.add(0, 0, -1)).getBlock().getMaterial() == Material.rock)
		    	{
                    world.setBlockState(pos, block.getDefaultState());
		    		//world.setBlockMetadataWithNotify(randPosX, randPosY, randPosZ, 3, 2);
		    	}
		    	if (world.getBlockState(pos.add(0, -1, 0)).getBlock().getMaterial() == Material.rock)
		    	{
                    world.setBlockState(pos, block.getDefaultState());
		    		//world.setBlockMetadataWithNotify(randPosX, randPosY, randPosZ, 5, 2);
		    	}
		    	if (world.getBlockState(pos.add(0, 1, 0)).getBlock().getMaterial() == Material.rock)
		    	{
                    world.setBlockState(pos, block.getDefaultState());
		    		//world.setBlockMetadataWithNotify(randPosX, randPosY, randPosZ, 6, 2);
		    	}
			}
		}
	}
}