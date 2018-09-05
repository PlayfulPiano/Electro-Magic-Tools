package emt.gthandler.common.tileentities.machines.multi.generator;

import emt.gthandler.common.implementations.EssentiaHatch;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.common.config.ConfigBlocks;

import static thaumcraft.api.ThaumcraftApi.getCraftingRecipes;

public class EMT_LargeTechnoAlchemicConstruct extends EMT_Large_Essentia_Multiblock {

    public EMT_LargeTechnoAlchemicConstruct(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public EMT_LargeTechnoAlchemicConstruct(String aName) {
        super(aName);
    }

    private static final byte CASING_INDEX = 16;
    private static byte maxcoiltier = (byte) (ItemList.Casing_Coil_NaquadahAlloy.get(1L).getItemDamage()-ItemList.Casing_Coil_Cupronickel.get(1L).getItemDamage());
    private byte coiltier = 0;

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone){
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_INDEX], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_INDEX]};
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {

        for (final GT_MetaTileEntity_Hatch_InputBus IPB: mInputBusses){
            for (int i = 0; i< IPB.mInventory.length; ++i) {
                if (IPB.mInventory[i] != null) {
                    for (Object O : getCraftingRecipes()) {
                        if (O instanceof CrucibleRecipe) {
                            final CrucibleRecipe CR = ((CrucibleRecipe) O);
                            if (CR.key.equals("PUREIRON"))
                            if (CR.catalystMatches(IPB.mInventory[i])) {
                                for (final EssentiaHatch H : mEssentiaHatches) {
                                    AspectList trial = H.getAspects().copy();
                                    for (final Aspect A : CR.aspects.aspects.keySet()) {
                                        if (!trial.reduce(A, CR.aspects.getAmount(A)))
                                            return false;
                                    }
                                    if (ThaumcraftApiHelper.isResearchComplete(this.getBaseMetaTileEntity().getOwnerName(), CR.key)) {
                                        IPB.mInventory[i]=IPB.mInventory[i].splitStack((IPB.mInventory[i].stackSize-1));
                                        if (IPB.mInventory[i].stackSize == 0)
                                            IPB.mInventory[i]=null;
                                        H.setCurrent(trial);
                                        this.mOutputItems = new ItemStack[]{CR.getRecipeOutput()};
                                        this.mMaxProgresstime = (1 + maxcoiltier - coiltier)*20 ;
                                        this.mEUt = - (int) Math.floor((GT_Values.V[3] - ((GT_Values.V[3] / 10) * (1 + maxcoiltier - coiltier))));
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        ++this.mProgresstime;
        if (this.mProgresstime >= (this.mMaxProgresstime-1))
            for (int i = 0; i < this.mOutputItems.length; i++)
                for (final GT_MetaTileEntity_Hatch_OutputBus OB: this.mOutputBusses)
                    for (int j = 0; j < OB.mInventory.length; j++)
                        if (OB.mInventory[j] == null)
                            OB.mInventory[j] = this.mOutputItems[i];
        return super.onRunningTick(aStack);
    }
    
    @Override
    public boolean checkMachine(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {

        final int xDir = ForgeDirection.getOrientation(iGregTechTileEntity.getBackFacing()).offsetX;
        final int zDir = ForgeDirection.getOrientation(iGregTechTileEntity.getBackFacing()).offsetZ;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    final IGregTechTileEntity tTileEntity = iGregTechTileEntity.getIGregTechTileEntityOffset(xDir + x, y, zDir + z);

                    if ((y == 1) && (x == 0) && (z == 0)) {
                        if (!this.addMufflerToMachineList(tTileEntity, CASING_INDEX))
                            return false;
                    }
                    else if ((y == 0) && (x == 0) && (z == 0)){
                        if (iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_Cupronickel.getBlock() || iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_Kanthal.getBlock() ||  iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_Nichrome.getBlock() || iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_TungstenSteel.getBlock()  || iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_HSSG.getBlock() || iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_Naquadah.getBlock() || iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ItemList.Casing_Coil_NaquadahAlloy.getBlock()  ){
                            this.coiltier = (byte) (iGregTechTileEntity.getMetaIDOffset(x+xDir,y,z+zDir) - ItemList.Casing_Coil_Cupronickel.get(1L).getItemDamage());
                            if (coiltier > maxcoiltier || this.coiltier < 0)
                                return false;
                        }else
                            return false;
                    }
                    else if (xDir+x == 0 && y == 0 && zDir+z == 0 )
                        continue;
                    else
                    if (!(this.addEssetiaHatchToList(tTileEntity,CASING_INDEX) || this.addInputToMachineList(tTileEntity,CASING_INDEX) || this.addMaintenanceToMachineList(tTileEntity,CASING_INDEX) || this.addEnergyInputToMachineList(tTileEntity,CASING_INDEX) || this.addOutputToMachineList(tTileEntity,CASING_INDEX))){
                            if (!(iGregTechTileEntity.getBlockOffset(x+xDir,y,z+zDir) == ConfigBlocks.blockMetalDevice))
                                return false;
                    }
                }
            }
        }

        if (this.mEssentiaHatches.size() < 1 || this.mInputBusses.size() < 1 || this.mOutputBusses.size() < 1)
            return false;

        //repair shit debug shit
        this.mWrench = true;
        this.mScrewdriver = true;
        this.mSoftHammer = true;
        this.mHardHammer = true;
        this.mSolderingTool = true;
        this.mCrowbar = true;

        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack itemStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack itemStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack itemStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack itemStack) {
        return false;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new EMT_LargeTechnoAlchemicConstruct(mName);
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
