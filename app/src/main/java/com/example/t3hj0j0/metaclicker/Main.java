package com.example.t3hj0j0.metaclicker;

import android.content.Context;
import android.content.SharedPreferences;
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
    private int money;
    private Choreographer choreographer;
    private long lastTimeCheck;
    SharedPreferences sharedPref;
    SharedPreferences.Editor prefEditor;
    playerStats playerstats;
    clickAd currentAd;
    int adTime;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("PLAYER_STATS", Context.MODE_PRIVATE);
        prefEditor = sharedPref.edit();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.click_counter = findViewById(R.id.click_counter);
        if (sharedPref.getInt("PLAYED_BEFORE",0) == 0)
        {
            clicks = 0;
            prefEditor.putInt("PLAYED_BEFORE",1);
            prefEditor.putInt("TOTAL_CLICKS",0);
            prefEditor.putInt("MONEY",0);
            prefEditor.putInt("AD_LEVEL",1);
            prefEditor.putInt("AUTOCLICKER_TOTAL",0);
            prefEditor.putInt("AUTOCLICKER_UPGRADES",0);
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
            money_view.setText(getClicks()+" generic currency");
        }
        playerstats = new playerStats(sharedPref);
        currentAd = new clickAd(playerstats.getAD_LEVEL());
        adTime = 30;
        ad_time.setText(adTime+"");
        lastTimeCheck = System.currentTimeMillis();
        choreographer = Choreographer.getInstance();
        choreographer.postFrameCallback(this);
    }
    @Override
    public void doFrame(long l)
    {
        if (System.currentTimeMillis() - lastTimeCheck >= 1000)
        {

            lastTimeCheck = System.currentTimeMillis();
            clicks++;
            click_counter.setText(getClicks()+" clicks");
            prefEditor.putInt("TOTAL_CLICKS",clicks);
            prefEditor.apply();
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
        this.clicks++;
        click_counter.setText(getClicks()+" clicks");
        prefEditor.putInt("TOTAL_CLICKS",clicks);
        prefEditor.apply();
    }
    public void reset(android.view.View view)
    {
        prefEditor.putInt("PLAYED_BEFORE",1);
        prefEditor.putInt("TOTAL_CLICKS",0);

        prefEditor.putInt("AD_LEVEL",1);
        prefEditor.putInt("AUTOCLICKER_TOTAL",0);
        prefEditor.putInt("AUTOCLICKER_UPGRADES",0);
        prefEditor.putInt("CLICKER_UPGRADES",1);
        prefEditor.putInt("CLICKER_UPGRADES_UNLOCKED",0);
        prefEditor.putInt("ASCENSION_UNLOCKED",0);
        prefEditor.putInt("ASCENSION_LEVEL",1);
        clicks = 0;
        click_counter.setText(getClicks()+" clicks");
    }
    public int getAutoClickerPower()
    {
        return playerstats.getAUTOCLICKER_TOTAL();
    }
}