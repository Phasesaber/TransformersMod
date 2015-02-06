package com.transformersmod.item.armor;

import com.transformersmod.TFHelper;
import com.transformersmod.TransformersMod;
import com.transformersmod.achievement.TFAchievements;
import com.transformersmod.item.TFItems;
import com.transformersmod.transformer.Transformer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSkystrikeArmor extends ItemArmor implements ITransformerArmor
{
	public ItemSkystrikeArmor(int armorPiece)
	{
		super(TFItems.TRANSFORMERMATERIAL, 4, armorPiece);
		this.setCreativeTab(TransformersMod.transformersTab);
	}

    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
    	if (TFHelper.isPlayerTransformer(player))
    	{
    		player.addStat(TFAchievements.transformer, 1);
    	}
    }
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return TransformersMod.modid + ":textures/models/skystrike/skystrike.png";
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemstack, int armorSlot) 
	{
		ModelBiped armorModel = null;

		if (itemstack != null)
		{
			if (itemstack.getItem() instanceof ItemSkystrikeArmor)
			{
				int type = ((ItemArmor)itemstack.getItem()).armorType;

				armorModel = TransformersMod.proxy.getArmorModel("Skystrike");
			}

			if (armorModel != null)
			{
				armorModel.bipedHead.showModel = armorSlot == 0;
				armorModel.bipedHeadwear.showModel = armorSlot == 0;
				armorModel.bipedBody.showModel = armorSlot == 1;
				armorModel.bipedRightArm.showModel = armorSlot == 1;
				armorModel.bipedLeftArm.showModel = armorSlot == 1;
				armorModel.bipedRightLeg.showModel = armorSlot == 2;
				armorModel.bipedLeftLeg.showModel = armorSlot == 2;

				armorModel.isSneak = entityLiving.isSneaking();
				armorModel.isRiding = entityLiving.isRiding();
				armorModel.isChild = entityLiving.isChild();
				armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 : 0;

				if (entityLiving instanceof EntityPlayer)
				{
					ItemStack itemstack1 = entityLiving.getHeldItem();
					armorModel.aimedBow = ((EntityPlayer)entityLiving).getItemInUseDuration() > 0 && itemstack1 != null && itemstack1.getItemUseAction() == EnumAction.BOW;
					armorModel.heldItemRight = ((EntityPlayer)entityLiving).getItemInUseDuration() > 0 && itemstack1 != null && itemstack1.getItemUseAction() == EnumAction.BLOCK ? 3 : (entityLiving.getEquipmentInSlot(0) != null ? 1 : 0);
				}

				return armorModel;
			}
		}

		return null;
	}
	
	@Override
	public Transformer getTransformer() 
	{
		return TransformersMod.transformerSkystrike;
	}
}