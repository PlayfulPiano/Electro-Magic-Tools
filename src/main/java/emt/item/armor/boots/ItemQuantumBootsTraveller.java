package emt.item.armor.boots;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.util.EMTConfigHandler;

public class ItemQuantumBootsTraveller extends ItemElectricBootsTraveller {

    public ItemQuantumBootsTraveller(ArmorMaterial material, int par3, int par4) {
        super(material, par3, par4);
    }

    protected void setBootsData() {
        super.setBootsData();
        maxCharge = 10_000_000;
        energyPerDamage = 20_000; // 500 hits, 2.5x prev
        visDiscount = 5;
        damageAbsorptionRatio = 1D
        transferLimit = 12_000;
        jumpBonus = (float) EMTConfigHandler.quantumBootsJump;
        runBonus = (float) EMTConfigHandler.quantumBootsSpeed;
        minimumHeight = (float) EMTConfigHandler.quantumBootsMinDrop;
        minimumDistance = EMTConfigHandler.quantumBootsMaxDrop;
        tier = 4;
        negateFall = true;
        iconResPath = EMT.TEXTURE_PATH + ":armor/boots_quantum";
        armorResPath = EMT.TEXTURE_PATH + ":textures/models/quantumbootstravel.png";
        unlocalisedName = EMT.MOD_ID + ".boots.traveller.quantum".;
    }
}
