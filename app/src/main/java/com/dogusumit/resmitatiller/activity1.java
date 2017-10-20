package com.dogusumit.resmitatiller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

public class activity1 extends AppCompatActivity {

    ListView listView;
    String[] yillar = {"2016","2017","2018","2019","2020"};
    int[] listeler = {R.array.l2016,R.array.l2017,R.array.l2018,R.array.l2019,R.array.l2020};
    int[] gunler = {R.array.g2016,R.array.g2017,R.array.g2018,R.array.g2019,R.array.g2020};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        listView = (ListView) findViewById(R.id.list_View);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<>(getApplicationContext(),R.layout.textcenter,R.id.textItem,yillar);
        spinner.setAdapter(adapter_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              listeDoldur(position);
          }
          @Override
          public void onNothingSelected(AdapterView<?> parent) {
          }
        });
        try{
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            spinner.setSelection(Arrays.asList(yillar).indexOf(String.valueOf(year)));
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void listeDoldur(int pos){
        try {
            final String[] liste = getResources().getStringArray(listeler[pos]);
            final String[] gun = getResources().getStringArray(gunler[pos]);
            ArrayAdapter<String> adapter_list = new ArrayAdapter<>(getApplicationContext(),R.layout.textcenter,R.id.textItem,liste);
            listView.setAdapter(adapter_list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str = gun[position];
                    Intent intent = new Intent(getBaseContext(), activity2.class);
                    intent.putExtra("gun", str);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}