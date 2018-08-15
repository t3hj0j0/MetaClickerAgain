package com.example.t3hj0j0.metaclicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Choreographer;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Main extends AppCompatActivity implements Choreographer.FrameCallback
{
    @BindView(R.id.click_counter) TextView click_counter;
    @BindView(R.id.ad_health) TextView ad_health;
    @BindView(R.id.auto_clicker_upgrade) TextView auto_clicker_upgrade;
    @BindView(R.id.clicker_upgrade) TextView clicker_upgrade;
    @BindView(R.id.ad_time) TextView ad_time;
    @BindView(R.id.money_view) TextView money_view;
    private int clicks;
    private Choreographer choreographer;
    private long lastTimeCheck;
    SharedPreferences sharedPref;
    SharedPreferences.Editor prefEditor;
    playerStats playerstats;
    clickAd currentAd;
    int adTime;
    int autoUpgradeCost;
    int clickUpgradeCost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPref = getSharedPreferences("PLAYER_STATS", Context.MODE_PRIVATE);
        prefEditor = sharedPref.edit();
        if (sharedPref.getInt("PLAYED_BEFORE",0) == 0)
        {
            clicks = 0;
            prefEditor.putInt("PLAYED_BEFORE",1);
            prefEditor.putInt("TOTAL_CLICKS",0);
            prefEditor.putInt("MONEY",0);
            prefEditor.putInt("AD_LEVEL",1);
            prefEditor.putInt("AUTOCLICKER_TOTAL",0);
            prefEditor.putInt("CLICKER_UPGRADES",1);
            prefEditor.putInt("CLICKER_UPGRADES_UNLOCKED",0);
            prefEditor.putInt("ASCENSION_UNLOCKED",0);
            prefEditor.putInt("ASCENSION_LEVEL",1);
            prefEditor.apply();
        }
        else if (sharedPref.getInt("PLAYED_BEFORE",0) == 1)
        {
            this.clicks = sharedPref.getInt("TOTAL_CLICKS", -1);
            click_counter.setText(getClicks()+" clicks");
            this.clicks = sharedPref.getInt("MONEY", -1);
        }
        playerstats = new playerStats(sharedPref);
        currentAd = new clickAd(playerstats.getAD_LEVEL());
        autoUpgradeCost = 15+playerstats.getAUTOCLICKER_TOTAL()+((int)Math.pow(playerstats.getAUTOCLICKER_TOTAL(),2)-(int)(playerstats.getAUTOCLICKER_TOTAL()*.5));
        clickUpgradeCost = 2+playerstats.getAUTOCLICKER_TOTAL()+((int)Math.pow(playerstats.getAUTOCLICKER_TOTAL(),2)-(int)(playerstats.getAUTOCLICKER_TOTAL()*.5));
        auto_clicker_upgrade.setText(autoUpgradeCost+"");
        clicker_upgrade.setText(clickUpgradeCost+"");
        adTime = 30;
        ad_health.setText(currentAd.getAdHealth() + "");
        money_view.setText(playerstats.getMONEY() + " generic currency units");
        ad_time.setText(adTime+" seconds");
        lastTimeCheck = System.currentTimeMillis();
        choreographer = Choreographer.getInstance();
        choreographer.postFrameCallback(this);
    }

    @Override
    public void doFrame(long l)
    {
        if (System.currentTimeMillis() - lastTimeCheck >= 1000)
        {
            autoClick();
            lastTimeCheck = System.currentTimeMillis();
            adTime--;
            if (adTime <= 0)
            {
                currentAd = new clickAd(playerstats.getAD_LEVEL());
                adTime = 30;
            }
            else
            ad_time.setText(adTime+"");
        }
        choreographer.postFrameCallback(this);
    }
    public int getClicks()
    {
        return this.clicks;
    }
    public void click(android.view.View view)
    {
        clicks+= playerstats.getCLICKER_UPGRADES();

        click_counter.setText(getClicks()+" clicks");
        prefEditor.putInt("TOTAL_CLICKS",clicks);
        prefEditor.apply();

        currentAd.setAdHealth(currentAd.getAdHealth() - playerstats.getCLICKER_UPGRADES());
        ad_health.setText(currentAd.getAdHealth()+"");

        deathCheck();
    }
    public void autoClick()
    {
        clicks+= playerstats.getAUTOCLICKER_TOTAL();

        click_counter.setText(getClicks()+" clicks");
        prefEditor.putInt("TOTAL_CLICKS",clicks);
        prefEditor.apply();

        currentAd.setAdHealth(currentAd.getAdHealth() - playerstats.getAUTOCLICKER_TOTAL());
        ad_health.setText(currentAd.getAdHealth()+"");

        deathCheck();
    }
    public void deathCheck()
    {
        if (currentAd.getAdHealth() <= 0)
        {
            adTime = 30;
            ad_time.setText(adTime+"");

            prefEditor.putInt("AD_LEVEL",playerstats.getAD_LEVEL()+1);
            prefEditor.putInt("MONEY",playerstats.getMONEY()+currentAd.getAdWorth());
            prefEditor.apply();

            playerstats.levelUp();
            currentAd = new clickAd(playerstats.getAD_LEVEL());
            ad_health.setText(currentAd.getAdHealth()+"");
            playerstats.setMONEY(sharedPref.getInt("MONEY",-1));
            money_view.setText(playerstats.getMONEY() + " generic currency units");
        }
    }
    public void reset(android.view.View view)
    {
        prefEditor.putInt("PLAYED_BEFORE",1);
        prefEditor.putInt("TOTAL_CLICKS",0);
        prefEditor.putInt("MONEY",0);
        prefEditor.putInt("AD_LEVEL",1);
        prefEditor.putInt("AUTOCLICKER_TOTAL",0);
        prefEditor.putInt("CLICKER_UPGRADES",1);
        prefEditor.putInt("ASCENSION_UNLOCKED",0);
        prefEditor.putInt("ASCENSION_LEVEL",1);
        prefEditor.apply();
        clicks = 0;
        click_counter.setText(getClicks()+" clicks");
        playerstats = new playerStats(sharedPref);
        currentAd = new clickAd(playerstats.getAD_LEVEL());
        autoUpgradeCost = 15+playerstats.getAUTOCLICKER_TOTAL()+((int)Math.pow(playerstats.getAUTOCLICKER_TOTAL(),2)-(int)(playerstats.getAUTOCLICKER_TOTAL()*.5));
        clickUpgradeCost = 2+playerstats.getAUTOCLICKER_TOTAL()+((int)Math.pow(playerstats.getAUTOCLICKER_TOTAL(),2)-(int)(playerstats.getAUTOCLICKER_TOTAL()*.5));
        auto_clicker_upgrade.setText(autoUpgradeCost+"");
        clicker_upgrade.setText(clickUpgradeCost+"");
        adTime = 30;
        ad_health.setText(currentAd.getAdHealth() + "");
        money_view.setText(playerstats.getMONEY() + " generic currency units");
        ad_time.setText(adTime+" seconds");
        lastTimeCheck = System.currentTimeMillis();
    }
    public void upgradeAutoClicker(android.view.View view)
    {
        if (playerstats.getMONEY() >= autoUpgradeCost)
        {
            prefEditor.putInt("AUTOCLICKER_TOTAL",playerstats.getAUTOCLICKER_TOTAL()+1);
            prefEditor.putInt("MONEY",playerstats.getMONEY()-autoUpgradeCost);
            prefEditor.apply();
            playerstats.setMONEY(sharedPref.getInt("MONEY",0));
            playerstats.setAUTOCLICKER_TOTAL(sharedPref.getInt("AUTOCLICKER_TOTAL",0));
            autoUpgradeCost = playerstats.getAUTOCLICKER_TOTAL()+((int)Math.pow(playerstats.getAUTOCLICKER_TOTAL(),2)-(int)(playerstats.getAUTOCLICKER_TOTAL()*.5));
            auto_clicker_upgrade.setText(autoUpgradeCost+"");
            money_view.setText(playerstats.getMONEY() + " generic currency units");
        }
    }
    public void upgradeClicker(android.view.View view)
    {
        if (playerstats.getMONEY() >= clickUpgradeCost)
        {
            prefEditor.putInt("CLICKER_UPGRADES",playerstats.getCLICKER_UPGRADES()+1);
            prefEditor.putInt("MONEY",playerstats.getMONEY()-clickUpgradeCost);
            prefEditor.apply();
            playerstats.setMONEY(sharedPref.getInt("MONEY",0));
            playerstats.setCLICKER_UPGRADES(sharedPref.getInt("CLICKER_UPGRADES",0));
            clickUpgradeCost = playerstats.getCLICKER_UPGRADES()+((int)Math.pow(playerstats.getCLICKER_UPGRADES(),2)-(int)(playerstats.getCLICKER_UPGRADES()*.5));
            clicker_upgrade.setText(clickUpgradeCost+"");
            money_view.setText(playerstats.getMONEY() + " generic currency units");
        }
    }
}