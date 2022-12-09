package emt.util;

import emt.init.EMTItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class EMTCreativeTab extends CreativeTabs {

    public EMTCreativeTab(String tabLabel) {
        super(tabLabel);
    }

    @Override
    public Item getTabIconItem() {
        return EMTItems.electricThorHammer;
    }
}
