package emt.tile.solar.compressed;

import emt.init.EMTBlocks;
import emt.tile.solar.TileEntitySolarBase;
import emt.util.EMTConfigHandler;
import ic2.api.energy.prefab.BasicSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityDoubleCompressedSolar extends TileEntitySolarBase {

	public TileEntityDoubleCompressedSolar() {
		super();
		energySource = new BasicSource(this, 100000, 4);
		output = EMTConfigHandler.doubleCompressedSolarOutput;
		this.maxstorage=this.getEUCapacity();
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(EMTBlocks.emtSolars, 1, 1);
	}

	@Override
	public float calc_multi() {
		return 1F;
	}

	@Override
	public String getInventoryName() {
		return "Double Compressed Solar";
	}
}
