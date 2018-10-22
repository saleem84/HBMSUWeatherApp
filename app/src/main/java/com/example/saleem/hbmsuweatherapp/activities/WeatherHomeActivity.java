package com.example.saleem.hbmsuweatherapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
//import android.arch.persistence.room.Room;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saleem.hbmsuweatherapp.R;
import com.example.saleem.hbmsuweatherapp.adapter.CustomAdapter;
import com.example.saleem.hbmsuweatherapp.communication.GetDataService;
import com.example.saleem.hbmsuweatherapp.communication.RetrofitClientInstance;
import com.example.saleem.hbmsuweatherapp.database.WeatherDatabase;
import com.example.saleem.hbmsuweatherapp.models.RetrofitWeather;
import com.example.saleem.hbmsuweatherapp.models.Weather;
import com.example.saleem.hbmsuweatherapp.models.WeatherEntityModel;
import com.example.saleem.hbmsuweatherapp.models.WeatherModel;
import com.example.saleem.hbmsuweatherapp.services.NotificationReceiver;
import com.example.saleem.hbmsuweatherapp.utils.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherHomeActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    private static final String DATABASE_NAME = "weather_db";

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    private WeatherDatabase weatherDatabase;
    private TextView dtLastUpdated, btnCentigrade, btnFht;

    private String unit = "metric"; //metric, imperial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);

        weatherDatabase = Room.databaseBuilder(getApplicationContext(),
                WeatherDatabase.class, DATABASE_NAME)
                //.fallbackToDesctructiveMigration()
                .build();



        ScheduleRepeatedNotifications();

        initializeControls();

        if(getIntent().getIntExtra("UPDATE", 0) == 1)
        {
            loadDataFromServer();
        }
        else
        {
            new RetrieveLocalWeather().execute();
        }



        /*Create handle for the RetrofitInstance interface*/

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btnCentigrade:
            {
                unit = "metric";
                loadDataFromServer();
                break;
            }

            case R.id.btnFht:
            {
                unit = "imperial";
                loadDataFromServer();
                break;
            }
        }

    }

    @Override
    public void onItemClick(WeatherEntityModel item) {
        //Toast.makeText(WeatherHomeActivity.this, "Clicked item: " + item.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(WeatherHomeActivity.this, WeatherDetailsActivity.class);
        intent.putExtra("CITY_ID", item.getId());

        startActivity(intent);
    }

    private void initializeControls()
    {
        dtLastUpdated = (TextView) findViewById(R.id.tvLastUpdated);
        btnCentigrade = (TextView) findViewById(R.id.btnCentigrade);
        btnFht = (TextView) findViewById(R.id.btnFht);

        btnCentigrade.setOnClickListener(this);
        btnFht.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
    }


    private void loadDataFromServer()
    {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        progressDoalog = new ProgressDialog(WeatherHomeActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();


        Call<RetrofitWeather> call = service.getAllWeatherData("292968,292932,292223,292878,291074,292672,290595", unit, "41e3090fcfb9002186fd9110f34e8077");
        call.enqueue(new Callback<RetrofitWeather>() {
            @Override
            public void onResponse(Call<RetrofitWeather> call, Response<RetrofitWeather> response) {
                progressDoalog.dismiss();


                List<WeatherEntityModel> list = retrieveDataList(response.body().getList());
                new StoreLocalWeather().execute(list);
            }

            @Override
            public void onFailure(Call<RetrofitWeather> call, Throwable t) {
                progressDoalog.dismiss();
                //Toast.makeText(WeatherHomeActivity.this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(WeatherHomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<WeatherEntityModel> retrieveDataList(List<WeatherModel> list)
    {
        List<WeatherEntityModel> listWeather = new ArrayList<>();

        if(list != null && list.size() > 0)
        {
            for(WeatherModel model : list)
            {
                WeatherEntityModel weatherModel = new WeatherEntityModel();
                weatherModel.setId(model.getId());
                weatherModel.setName(model.getName());

                if(model.getWeather() != null && model.getWeather().size() > 0) {
                    String description = "", titleMain = "";
                    for (Weather w : model.getWeather())
                    {
                        description += w.getDescription() + ",";
                        titleMain += w.getMain() + ",";
                    }
                    weatherModel.setDescription(description);
                    weatherModel.setTitleMain(titleMain);
                }

                weatherModel.setSunrise(model.getSys().getSunrise());
                weatherModel.setSunset(model.getSys().getSunset());
                weatherModel.setLat(model.getCoord().getLat());
                weatherModel.setLon(model.getCoord().getLon());
                weatherModel.setTemperature(model.getMain().getTemp());
                weatherModel.setPressure(model.getMain().getPressure());
                weatherModel.setHumidity(model.getMain().getHumidity());
                weatherModel.setTemp_min(model.getMain().getTemp_min());
                weatherModel.setTemp_max(model.getMain().getTemp_max());
                weatherModel.setVisibility(model.getVisibility());
                weatherModel.setWind_speed(model.getWind().getSpeed());
                weatherModel.setDtWeatherTime(model.getDt());

                Date dateObj = Calendar.getInstance().getTime();
                SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");

                String newDateStr = postFormater.format(dateObj);

                weatherModel.setDtLastUpdate(newDateStr);

                String ImageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEV2E_XMLFXWwTjgdEINJQJv6Lct72FTMyWLInTe6kvN4ekKn0";

                switch (model.getId()) {
                    case 292968: // Abu Dhabi
                    {
                        ImageURL = "https://img.grouponcdn.com/deal/etLqRvb8ixsevYWzu5uJ/m4-2048x1229/v1/c700x420.jpg";
                        break;
                    }
                    case 292932: // Ajman
                    {
                        ImageURL = "http://www.hotelajman.com/uploads/9/8/2/4/98249840/ajmanmuseum-4_2_orig.jpg";
                        break;
                    }
                    case 292223: // Dubai
                    {
                        ImageURL = "https://www.mckinsey.com/middle-east/~/media/McKinsey/Locations/Europe%20and%20Middle%20East/Middle%20East/Dubai/middle-east_overview_dubai_thumbnail_159765343_1536x1536.ashx";
                        break;
                    }
                    case 292878: // Fujaira
                    {
                        ImageURL = "http://www.dxbmarine.com/wp-content/uploads/2013/11/dubai_marine_beach_resort_and_spa_Fujairah_Slider_1.jpg";
                        break;
                    }
                    case 291074: // Ras Al Khaimah
                    {
                        ImageURL = "http://www.hoteliermiddleeast.com/pictures/2017/Hotels/Hilton-RAK-SPA-VIEW.jpg";
                        break;
                    }
                    case 292672: // Sharjah
                    {
                        ImageURL = "https://media-cdn.tripadvisor.com/media/photo-s/0f/a1/f2/02/best-of-sharjah-and-ajman.jpg";
                        break;
                    }
                    case 290595: // Umm al Quwain
                    {
                        ImageURL = "http://www.seatrade-maritime.com/media/k2/items/cache/fe5110a273ff86432258b5818c79739d_XL.jpg";
                        break;
                    }
                }

                weatherModel.setImageURL(ImageURL);

                listWeather.add(weatherModel);

            }
        }

        return listWeather;
    }

    private void generateDataList(List<WeatherEntityModel> list) {

        // List<WeatherModel> list = body.getList();

        if(list != null)
        {
            adapter = new CustomAdapter(this,list, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WeatherHomeActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }
    }



    private class StoreLocalWeather extends AsyncTask<List<WeatherEntityModel>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<WeatherEntityModel>... params) {
            try {

                if(params != null && params[0].size() > 0)
                {
                    weatherDatabase.daoAccess().deleteWeatherList();
                    weatherDatabase.daoAccess().insertMultipleWeathers(params[0]);
                }
                else
                {
                    return false;
                }

            } catch (Exception ex) {
                return false;
            }

            /*
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            */
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if(result) {
                Toast.makeText(WeatherHomeActivity.this, "Weather information stored locally.", Toast.LENGTH_SHORT).show();

                new RetrieveLocalWeather().execute();
            }
            else
            {
                Toast.makeText(WeatherHomeActivity.this, "Weather information could not be stored.", Toast.LENGTH_SHORT).show();
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


    private class RetrieveLocalWeather extends AsyncTask<String, Void, List<WeatherEntityModel>> {

        @Override
        protected List<WeatherEntityModel> doInBackground(String... params) {
            List<WeatherEntityModel> list = null;

            try {
                //WeatherEntityModel item = weatherDatabase.daoAccess().fetchOneWeatherbyCityId(292968);
                list = weatherDatabase.daoAccess().fetchAllWeatherList();

            } catch (Exception ex) {
                return null;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<WeatherEntityModel> result) {

            if(result != null && result.size() > 0) {
                dtLastUpdated.setText("Last updated on " + result.get(0).getDtLastUpdate());

                generateDataList(result);
            }
            else
            {
                Toast.makeText(WeatherHomeActivity.this, "No weather information found locally.", Toast.LENGTH_SHORT).show();
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

    private void ScheduleRepeatedNotifications()
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 10);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }
}
