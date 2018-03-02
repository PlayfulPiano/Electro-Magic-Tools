package emt.tile.solar.order;

import cpw.mods.fml.common.FMLCommonHandler;
import emt.init.EMTBlocks;
import emt.tile.solar.TileEntitySolarBase;
import emt.util.EMTConfigHandler;
import ic2.api.energy.prefab.BasicSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityOrderSolar extends TileEntitySolarBase {

	public TileEntityOrderSolar() {
		super();
		energySource = new BasicSource(this, 10000, 3);
		output = EMTConfigHandler.compressedSolarOutput;
		this.maxstorage=this.getEUCapacity();
	}

	@Override
	public void createEnergy() {
	    if ((this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord)) && (!this.worldObj.isRaining()) && (!this.worldObj.isThundering())) {
	    	isActive = true;
	    	if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
	    		this.energySource.addEnergy(this.output*calc_multi());
	    		this.generating=output*calc_multi();
	    	}
	    }else isActive = false;
	}
	
	@Override
	public float calc_multi() {
		if (this.worldObj.isDaytime())
			 return 2F;
		else return 0.75F;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(EMTBlocks.emtSolars, 1, 9);
	}
	
	@Override
	public String getInventoryName() {
		return "Compressed Ordo Solar";
	}
}
