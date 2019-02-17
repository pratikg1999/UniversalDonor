package com.example.universaldonor;

public class BloodStats {

    private long aMinus;
    private long aPlus;
    private long bMinus;
    private long bPlus;
    private long oMinus;
    private long oPlus;
    private long abMinus;
    private long abPlus;

    public BloodStats(long aMinus, long aPlus, long bMinus, long bPlus, long oMinus, long oPlus, long abMinus, long abPlus) {
        this.aMinus = aMinus;
        this.aPlus = aPlus;
        this.bMinus = bMinus;
        this.bPlus = bPlus;
        this.oMinus = oMinus;
        this.oPlus  = oPlus;
        this.abMinus = abMinus;
        this.abPlus = abPlus;
    }

    public BloodStats(){
    }

    public long getaMinus() {
        return aMinus;
    }

    public long getaPlus() {
        return aPlus;
    }

    public long getbMinus() {
        return bMinus;
    }

    public long getbPlus() {
        return bPlus;
    }

    public long getoMinus() {
        return oMinus;
    }

    public long getoPlus() {
        return oPlus;
    }

    public long getAbMinus() {
        return abMinus;
    }

    public long getAbPlus() {
        return abPlus;
    }
}
