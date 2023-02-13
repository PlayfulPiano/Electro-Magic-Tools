package emt.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.EMT;
import emt.item.armor.boots.ItemElectricBootsTraveller;
import emt.item.armor.boots.ItemNanoBootsTraveller;
import emt.item.armor.boots.ItemQuantumBootsTraveller;
import emt.util.EMTConfigHandler;
import emt.util.EMTCreativeTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.common.lib.CreativeTabThaumcraft;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileInfusionMatrix;

public class ItemAlastorsWand extends Item {
    // constant from Thaumcraft's EventHandlerEntity#playerJumps for basic Traveller Boots

        @SideOnly(Side.CLIENT)
        public void registerIcons(IIconRegister ir) {
            this.itemIcon = ir.registerIcon(EMT.TEXTURE_PATH + ":basicwand");
        }

        @SideOnly(Side.CLIENT)
        public IIcon func_77617_a(int par1) {
            return this.itemIcon;
        }

        public ItemAlastorsWand() {
           setCreativeTab(CreativeTabThaumcraft.tabTools);
            setUnlocalizedName("ItemAlastorsScrewdriver");
        }

        @Override
        public boolean onItemUseFirst(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int targetX,
            int targetY,
            int targetZ,
            int side,
            float hitX,
            float hitY,
            float hitZ) {
            if (!world.isRemote) {
                if (player.getEquipmentInSlot(1).getItem() instanceof ItemElectricBootsTraveller) {
                    ItemElectricBootsTraveller boots = (ItemElectricBootsTraveller) player.getEquipmentInSlot(1).getItem();
                    assert boots != null;
                    if (boots.speedBonus > 0.02F) {
                        boots.speedBonus = 0.0F;
                        boots.jumpBonus = 0.0F;
                    } else {
                        boots.speedBonus = 0.055F;
                        boots.jumpBonus = 0.2750000059604645F;
                        if (player.getEquipmentInSlot(1).getItem() instanceof ItemNanoBootsTraveller) {
                            boots.speedBonus = (float) EMTConfigHandler.nanoBootsSpeed;
                            boots.jumpBonus = (float) EMTConfigHandler.nanoBootsJump;
                        } else if (player.getEquipmentInSlot(1).getItem() instanceof ItemQuantumBootsTraveller){
                            boots.speedBonus = (float) EMTConfigHandler.quantumBootsSpeed;
                            boots.jumpBonus = (float) EMTConfigHandler.quantumBootsJump;
                        }
                    }
                }
            }
                return false;

        }
    }

