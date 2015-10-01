package tombenpotter.emt.common.items.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import tombenpotter.emt.common.entities.EntityLaser;

public class ItemExplosionFocus extends ItemBaseFocus {
	
    private static final AspectList visCost = new AspectList().add(Aspect.FIRE, 200).add(Aspect.ENTROPY, 200);

    public ItemExplosionFocus() {
        super(".focus.explosion", "focus_explosion");
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0x8B0000;
    }

    @Override
    public AspectList getVisCost(ItemStack focusstack) {
        return visCost;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "EXPLOSION";
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition movingobjectposition) {
        ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
        if (player.capabilities.isCreativeMode || wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, true)) {
            if (!world.isRemote) {
                EntityLaser laser;
                laser = new EntityLaser(world, player, 2);
                world.spawnEntityInWorld(laser);
            }
        }
        return itemstack;
    }
}
