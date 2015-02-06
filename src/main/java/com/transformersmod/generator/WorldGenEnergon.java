package com.transformersmod.generator;

import com.transformersmod.block.TFBlocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenEnergon extends WorldGenerator
{
    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
    {
        for (int i = 0; i < 100; i++)
        {
            new WorldGenMinable(TFBlocks.energonCrystal.getDefaultState(), 1).generate(worldIn, p_180709_2_, p_180709_3_);
        }

        return true;
    }
}