package emt.item.armor.boots;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.init.EMTItems;
import emt.util.EMTConfigHandler;
import emt.util.EMTTextHelper;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.armor.Hover;

public class ItemElectricBootsTraveller extends ItemElectricBoots implements IMetalArmor {

    public ItemElectricBootsTraveller(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        this.setCreativeTab(EMT.TAB);
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected void setBootsData() {
        super.setBootsData();
        maxCharge = 100_000;
        energyPerDamage = 1000; // 100 hits
        visDiscount = 2;
        damageAbsorptionRatio = 0.5D;
        transferLimit = 100;
        jumpBonus = 0.2750000059604645F;  // constant from Thaumcraft's EventHandlerEntity#playerJumps for basic Traveller Boots
        runBonus = 0.055F; // base electric
        minimumHeight = 4F;
        minimumDistance = 20d;
        tier = 2;
        negateFall = true;
        iconResPath = EMT.TEXTURE_PATH + ":armor/boots_electric";
        armorResPath = EMT.TEXTURE_PATH + ":textures/models/electricboots.png";
        unlocalisedName = EMT.MOD_ID + ".boots.traveller.electric";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        list.add(StatCollector.translateToLocal("ic2.item.tooltip.PowerTier") + " " + getTier(new ItemStack(this)));
        list.add(EMTTextHelper.PURPLE + EMTTextHelper.localize("tooltip.EMT.visDiscount") + ": " + visDiscount + "%");
    }

    @Override
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        if (EMTConfigHandler.enchanting == false) {
            return 0;
        } else {
            return 4;
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
        if (EMTConfigHandler.enchanting == false) {
            return false;
        } else {
            return true;
        }
    }
}
