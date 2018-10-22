package com.example.saleem.hbmsuweatherapp.activities;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saleem.hbmsuweatherapp.R;
import com.example.saleem.hbmsuweatherapp.database.WeatherDatabase;
import com.example.saleem.hbmsuweatherapp.models.WeatherEntityModel;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherDetailsActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "weather_db";
    private WeatherDatabase weatherDatabase;

    private TextView tvCityName;
    private ImageView imgCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        initializeControls();

        weatherDatabase = Room.databaseBuilder(getApplicationContext(),
                WeatherDatabase.class, DATABASE_NAME)
                //.fallbackToDesctructiveMigration()
                .build();

        if(getIntent().getIntExtra("CITY_ID", 0) > 0) {
            Integer cityId = getIntent().getIntExtra("CITY_ID", 0);

            new RetrieveLocalWeather().execute(cityId);
        }
    }

    private void initializeControls()
    {
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        imgCity = (ImageView) findViewById(R.id.imgCity);
    }


    private class RetrieveLocalWeather extends AsyncTask<Integer, Void, WeatherEntityModel> {

        @Override
        protected WeatherEntityModel doInBackground(Integer... params) {
            WeatherEntityModel item = null;

            try {
                item = weatherDatabase.daoAccess().fetchOneWeatherbyCityId(params[0]);
                //list = weatherDatabase.daoAccess().fetchAllWeatherList();

            } catch (Exception ex) {
                return null;
            }
            return item;
        }

        @Override
        protected void onPostExecute(WeatherEntityModel result) {

            if(result != null) {
                tvCityName.setText(result.getName());
                Picasso.Builder builder = new Picasso.Builder(WeatherDetailsActivity.this);
                builder.downloader(new OkHttp3Downloader(WeatherDetailsActivity.this));

                if(!result.getImageURL().isEmpty())
                {

                builder.build().load(result.getImageURL())
                        .placeholder((R.drawable.ic_launcher_background))
                        .error(R.drawable.ic_launcher_background)
                        .into(imgCity);
            }
            else
            {
                builder.build().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEV2E_XMLFXWwTjgdEINJQJv6Lct72FTMyWLInTe6kvN4ekKn0")
                        .placeholder((R.drawable.ic_launcher_background))
                        .error(R.drawable.ic_launcher_background)
                        .into(imgCity);
            }

                //generateDataList(result);
            }
            else
            {
                Toast.makeText(WeatherDetailsActivity.this, "No weather information found locally.", Toast.LENGTH_SHORT).show();
            }
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
