package com.example.t3hj0j0.metaclicker;

public class clickAd
{
    public int getAdLevel() {
        return adLevel;
    }

    public void setAdLevel(int adLevel) {
        this.adLevel = adLevel;
    }

    public int getAdHealth() {
        return adHealth;
    }

    public void setAdHealth(int adHealth)
    {
        this.adHealth = adHealth;
    }

    public int getAdWorth()
    {
        return adWorth;
    }

    public void setAdWorth(int adWorth)
    {
        this.adWorth = adWorth;
    }

    private int adLevel;
private int adHealth;
private int adWorth;
public clickAd(int adLevel)
{
    this.adLevel = adLevel;
    createAdHealth();
    createAdValue();
}
public void createAdHealth()
{
    setAdHealth((int)Math.pow(getAdLevel(),2)+getAdLevel()*4+getAdLevel());
}
public void createAdValue()
{
    this.setAdWorth(getAdLevel()+getAdHealth()/10);
}
}
