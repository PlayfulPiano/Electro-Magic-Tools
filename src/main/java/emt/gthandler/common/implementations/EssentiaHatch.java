package emt.gthandler.common.implementations;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.ArrayList;


public class EssentiaHatch extends GT_MetaTileEntity_Hatch {

    private AspectList current = new AspectList();
    private static int ApTe = (int) Math.ceil(Aspect.aspects.size()/GT_Values.V.length);

    public boolean addAspectList(final AspectList A){

        if ((this.mTier+1)*ApTe > A.size()+current.size()){
            if (current.visSize() + A.visSize() < (this.mTier+1)*ApTe*64)
            current.add(A);
            return true;
        }else
            return false;

    }

    public EssentiaHatch(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, new String[] { }, new ITexture[0]);
    }

    public EssentiaHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier,0, aDescription, aTextures);
    }

    public void setCurrent(AspectList List){
        this.current = List;
    }

    public AspectList getAspects() {
        return current;
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new EssentiaHatch("Essentia Hatch "+ GT_Values.VN[this.mTier],this.mTier,this.getDescription(),this.mTextures);
    }


    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        //TODO: new GUI.
        return null;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        //TODO: new contanier GUI.
        return null;
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("This Essentia-Hatch contains:");
        for (final Aspect A : this.current.aspects.keySet())
            list.add(A.getName()+": "+Integer.toString(current.getAmount(A)));

        String[] ret = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i]=list.get(i);
        }

        return ret;

        /*String[] old = super.getInfoData();
        if (old.length!=0) {
            String[] newDescription = new String[old.length + 1];
            for (int i = 0; i < old.length; i++) {
                newDescription[i] = old[i];
            }
            newDescription[old.length] = "This Contains: " + current.size() + " different Aspects";
            return newDescription;
        }else
            return new String[]{"This Contains: " + current.size() + " different Aspects"};*/
    }

    @Override
    public String[] getDescription(){ //this is the fucking tooltip -_-'
        return new String[] {
                "Essentia Hatch for usage in Multiblocks",
                "Can only be filled with the Essentia Filler",
                "Can hold "+Integer.toString((ApTe*(mTier+1)))+" Different Essentia Types",
                "Can hold "+Integer.toString((64*ApTe*(mTier+1)))+" Essentia in total"
        };
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_IN)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_IN)};
    }

    @Override
    public void saveNBTData(NBTTagCompound var1){

        NBTTagList tlist = new NBTTagList();
        var1.setTag("Aspects", tlist);
        Aspect[] aspectA = current.getAspects();

        for(int i = 0; i < aspectA.length; ++i) {
            Aspect aspect = aspectA[i];
            if (aspect != null) {
                NBTTagCompound f = new NBTTagCompound();
                f.setString("key", aspect.getTag());
                f.setInteger("amount", current.getAmount(aspect));
                tlist.appendTag(f);
            }
        }

        super.saveNBTData(var1);
    }

    @Override
    public void loadNBTData(NBTTagCompound var1){
        current = new AspectList();
        NBTTagList tlist = var1.getTagList("Aspects", 10);

        for(int j = 0; j < tlist.tagCount(); ++j) {
            NBTTagCompound rs = tlist.getCompoundTagAt(j);
            if (rs.hasKey("key")) {
                current.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
            }
        }

        super.loadNBTData(var1);
    }

    public int fuelleft() {
        int ret = 0;
        for (final Aspect A : current.aspects.keySet()){
            ret += current.getAmount(A);
        }
        return ret;
    }


    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }

    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    public int getCapacity() {
        return 0;
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
