package com.example.saleem.hbmsuweatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saleem.hbmsuweatherapp.R;
import com.example.saleem.hbmsuweatherapp.models.WeatherEntityModel;
import com.example.saleem.hbmsuweatherapp.models.WeatherModel;
import com.example.saleem.hbmsuweatherapp.utils.OnItemClickListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<WeatherEntityModel> dataList;
    private Context context;
    private final OnItemClickListener listener;

    public CustomAdapter(Context context,List<WeatherEntityModel> dataList, OnItemClickListener listener){
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle, txtTemperature, txtCoordinates, txtDescription;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            txtTemperature = mView.findViewById(R.id.temperature);
            txtCoordinates = mView.findViewById(R.id.coordinates);
            txtDescription = mView.findViewById(R.id.description);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        bind(holder.mView, dataList.get(position), listener);

        holder.txtTitle.setText(dataList.get(position).getName());
        holder.txtTemperature.setText(dataList.get(position).getTemperature().toString());
        holder.txtCoordinates.setText("(" + dataList.get(position).getLat() + ", " + dataList.get(position).getLon() + ")");
        holder.txtDescription.setText(dataList.get(position).getDescription());


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));

        if (dataList.size() > 0) {
            String ImageURL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEV2E_XMLFXWwTjgdEINJQJv6Lct72FTMyWLInTe6kvN4ekKn0";

           switch (dataList.get(position).getId())
           {
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


            builder.build().load(ImageURL)
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.coverImage);
        }
        else
        {
            builder.build().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEV2E_XMLFXWwTjgdEINJQJv6Lct72FTMyWLInTe6kvN4ekKn0")
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.coverImage);
        }

    }

    public void bind(final View mView, final WeatherEntityModel item, final OnItemClickListener listener) {

        mView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}