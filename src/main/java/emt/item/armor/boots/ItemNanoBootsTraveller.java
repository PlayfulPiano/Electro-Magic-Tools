package emt.item.armor.boots;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTConfigHandler;

public class ItemNanoBootsTraveller extends ItemElectricBootsTraveller {

    public ItemNanoBootsTraveller(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
    }

    protected void setBootsData() {
        super.setBootsData();
        maxCharge = 1_000_000;
        energyPerDamage = 5000; // 200 hits, 2x prev
        visDiscount = 4;
        damageAbsorptionRatio = 0.9D
        transferLimit = 1600;
        jumpBonus = (float) EMTConfigHandler.nanoBootsJump;
        runBonus = (float) EMTConfigHandler.nanoBootsSpeed;
        minimumHeight = (float) EMTConfigHandler.nanoBootsMinDrop;
        minimumDistance = EMTConfigHandler.nanoBootsMaxDrop;
        tier = 3;
        negateFall = true;
        iconResPath = EMT.TEXTURE_PATH + ":armor/boots_nano";
        armorResPath = EMT.TEXTURE_PATH + ":textures/models/nanobootstravel.png";
        unlocalisedName = EMT.MOD_ID + ".boots.traveller.nano";
    }
}
