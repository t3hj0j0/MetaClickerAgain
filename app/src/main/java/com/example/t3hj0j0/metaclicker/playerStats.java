package com.example.t3hj0j0.metaclicker;

import android.content.SharedPreferences;

public class playerStats
{
    public int getAD_LEVEL()
    {
        return AD_LEVEL;
    }

    public int getAUTOCLICKER_TOTAL()
    {
        return AUTOCLICKER_TOTAL;
    }

    public int getAUTOCLICKER_UPGRADES()
    {
        return AUTOCLICKER_UPGRADES;
    }

    public int getCLICKER_UPGRADES()
    {
        return CLICKER_UPGRADES;
    }

    public int getCLICKER_UPGRADES_UNLOCKED()
    {
        return CLICKER_UPGRADES_UNLOCKED;
    }

    public int getASCENSION_UNLOCKED()
    {
        return ASCENSION_UNLOCKED;
    }

    public int getASCENSION_LEVEL()
    {
        return ASCENSION_LEVEL;
    }

    private int AD_LEVEL;

    public int getMONEY() {
        return MONEY;
    }

    public void setMONEY(int MONEY) {
        this.MONEY = MONEY;
    }

    private int MONEY;

    public void setAD_LEVEL(int AD_LEVEL) {
        this.AD_LEVEL = AD_LEVEL;
    }

    public void setAUTOCLICKER_TOTAL(int AUTOCLICKER_TOTAL) {
        this.AUTOCLICKER_TOTAL = AUTOCLICKER_TOTAL;
    }

    public void setAUTOCLICKER_UPGRADES(int AUTOCLICKER_UPGRADES) {
        this.AUTOCLICKER_UPGRADES = AUTOCLICKER_UPGRADES;
    }

    public void setCLICKER_UPGRADES(int CLICKER_UPGRADES) {
        this.CLICKER_UPGRADES = CLICKER_UPGRADES;
    }

    public void setCLICKER_UPGRADES_UNLOCKED(int CLICKER_UPGRADES_UNLOCKED) {
        this.CLICKER_UPGRADES_UNLOCKED = CLICKER_UPGRADES_UNLOCKED;
    }

    public void setASCENSION_UNLOCKED(int ASCENSION_UNLOCKED) {
        this.ASCENSION_UNLOCKED = ASCENSION_UNLOCKED;
    }

    public void setASCENSION_LEVEL(int ASCENSION_LEVEL) {
        this.ASCENSION_LEVEL = ASCENSION_LEVEL;
    }

    private int AUTOCLICKER_TOTAL;
    private int AUTOCLICKER_UPGRADES;
    private int CLICKER_UPGRADES;
    private int CLICKER_UPGRADES_UNLOCKED;
    private int ASCENSION_UNLOCKED;
    private int ASCENSION_LEVEL;

    private SharedPreferences playerPref;
    public playerStats(SharedPreferences playerPref)
    {
        this.playerPref = playerPref;

        AD_LEVEL = playerPref.getInt("AD_LEVEL",1);
        MONEY = playerPref.getInt("MONEY",0);
        AUTOCLICKER_TOTAL = playerPref.getInt("AUTOCLICKER_TOTAL",0);
        AUTOCLICKER_UPGRADES = playerPref.getInt("AUTOCLICKER_UPGRADES",0);
        CLICKER_UPGRADES = playerPref.getInt("CLICKER_UPGRADES",0);
        CLICKER_UPGRADES_UNLOCKED = playerPref.getInt("CLICKER_UPGRADES_UNLOCKED",0);
        ASCENSION_UNLOCKED = playerPref.getInt("ASCENSION_UNLOCKED",0);
        ASCENSION_LEVEL = playerPref.getInt("ASCENSION_LEVEL",0);
    }
    public void levelUp()
    {
        AD_LEVEL++;
    }
}