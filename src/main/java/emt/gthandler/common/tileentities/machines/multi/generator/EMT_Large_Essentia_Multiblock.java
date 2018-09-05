package emt.gthandler.common.tileentities.machines.multi.generator;

import emt.gthandler.common.implementations.EssentiaHatch;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;

import java.util.ArrayList;

public abstract class EMT_Large_Essentia_Multiblock extends GT_MetaTileEntity_MultiBlockBase {

    public ArrayList<EssentiaHatch> mEssentiaHatches = new ArrayList();

    public EMT_Large_Essentia_Multiblock(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public EMT_Large_Essentia_Multiblock(String aName) {
        super(aName);
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    public boolean addEssetiaHatchToList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex){
        if (aTileEntity == null) {
            return false;
        } else {
            IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof EssentiaHatch) {
                ((EssentiaHatch)aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return this.mEssentiaHatches.add((EssentiaHatch)aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

}
