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
        AUTOCLICKER_TOTAL = playerPref.getInt("AUTOCLICKER_TOTAL",0);
        AUTOCLICKER_UPGRADES = playerPref.getInt("AUTOCLICKER_UPGRADES",0);
        CLICKER_UPGRADES = playerPref.getInt("CLICKER_UPGRADES",0);
        CLICKER_UPGRADES_UNLOCKED = playerPref.getInt("CLICKER_UPGRADES_UNLOCKED",0);
        ASCENSION_UNLOCKED = playerPref.getInt("ASCENSION_UNLOCKED",0);
        ASCENSION_LEVEL = playerPref.getInt("ASCENSION_LEVEL",0);
    }
}