/*******************************************************************************
 * Copyright (c) 2014 Tombenpotter.
 * All rights reserved. 
 *
 * This program and the accompanying materials are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 *
 * This class was made by Tombenpotter and is distributed as a part of the Electro-Magic Tools mod.
 * Electro-Magic Tools is a derivative work on Thaumcraft 4 (c) Azanor 2012.
 * http://www.minecraftforum.net/topic/1585216-
 *
 * Attention: this class originally belongs to the ThaumicTinkerer mod.
 * All I did was modifying it to fulfill my needs.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * http://www.minecraftforum.net/topic/1813058-
 ******************************************************************************/

package tombenpotter.emt.common.module.ic2.items.armor.boots;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import tombenpotter.emt.ModInformation;
import tombenpotter.emt.common.util.ConfigHandler;

public class ItemQuantumBootsTraveller extends ItemElectricBootsTraveller {

    public ItemQuantumBootsTraveller(int par3, int par4) {
        super(par3, par4);
        maxCharge = 10000000;
        speedBonus = (float) ConfigHandler.quantumBootsSpeed;
        jumpBonus = (float) ConfigHandler.quantumBootsJump;
        visDiscount = 5;
        transferLimit = 10000;
        energyPerDamage = 1000;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(ModInformation.texturePath + ":quantumbootstravel");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return ModInformation.texturePath + ":textures/models/quantumbootstravel.png";
    }
}
