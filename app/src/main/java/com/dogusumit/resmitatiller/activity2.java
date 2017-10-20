package com.dogusumit.resmitatiller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity2 extends AppCompatActivity {

    MaterialCalendarView takvim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        try {
            takvim = (MaterialCalendarView) findViewById(R.id.calendarView);
            takvim.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
            takvim.state().edit()
                    .setMinimumDate(CalendarDay.from(2015, 12, 1))
                    .setMaximumDate(CalendarDay.from(2020, 12, 31))
                    //.setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();

            String gun = getIntent().getStringExtra("gun");
            takvim.setCurrentDate(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(gun));

            new gunleriIsaretle().execute();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        Button btn = (Button) findViewById(R.id.buton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private class gunleriIsaretle extends AsyncTask <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                int[] kaynak = {R.array.g2016, R.array.g2017, R.array.g2018, R.array.g2019, R.array.g2020};
                for (int i : kaynak) {
                    final String[] gunler = getResources().getStringArray(i);
                    for (String j : gunler) {
                        Date gun = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(j);
                        takvim.setDateSelected(gun, true);
                    }
                }
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uygulamayiOyla()
    {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
            catch (Exception ane)
            {
                Toast.makeText(getApplicationContext(),ane.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void marketiAc()
    {
        try {
            Uri uri = Uri.parse("market://developer?id="+getString(R.string.play_store_id));
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id="+getString(R.string.play_store_id))));
            }
            catch (Exception ane)
            {
                Toast.makeText(getApplicationContext(),ane.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.oyla:
                uygulamayiOyla();
                return true;
            case R.id.market:
                marketiAc();
                return true;
            case R.id.cikis:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
