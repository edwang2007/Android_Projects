package edwang.weatherappjava.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edwang.weatherappjava.R;
import edwang.weatherappjava.WeatherInfo;
import edwang.weatherappjava.data.CityWeatherRow;
import edwang.weatherappjava.touch.WeatherTouchHelper;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by student on 29/06/2017.
 */

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> implements WeatherTouchHelper{


    public static final String CITY_KEY = "CITY_KEY";
    private Realm realmCity;
    private List<CityWeatherRow> listCity;
    private Context context;

    public void addCity(String cityName) {
        realmCity.beginTransaction();
        CityWeatherRow newCity = realmCity.createObject(CityWeatherRow.class, UUID.randomUUID().toString());
        newCity.setCityName(cityName);
        newCity.setTemp(0.0);
        newCity.setIcon("");
        realmCity.commitTransaction();

        listCity.add(newCity);
        notifyDataSetChanged();
    }

    public CityRecyclerAdapter(Context context, Realm realmCity) {
        this.context = context;
        this.realmCity = realmCity;

        listCity = new ArrayList<CityWeatherRow>();

        RealmResults<CityWeatherRow> cityWeatherResult =
                realmCity.where(CityWeatherRow.class).findAll();

        for (int x = 0; x < cityWeatherResult.size(); x++) {
            listCity.add(cityWeatherResult.get(x));
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View cityRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_row, parent, false);

        return new ViewHolder(cityRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvCity.setText(listCity.get(position).getCityName());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCityDismiss(holder.getAdapterPosition());

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, WeatherInfo.class);
                intent.putExtra(CITY_KEY, listCity.get(position).getCityName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCity.size();
    }

    @Override
    public void onCityDismiss(int position) {
        realmCity.beginTransaction();
        listCity.get(position).deleteFromRealm();
        realmCity.commitTransaction();
        listCity.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout cardView;
        private TextView tvCity;
        private ImageView ivDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            cardView = (RelativeLayout) itemView.findViewById(R.id.cardView);
        }
    }




}
