package project.weather;

/**
 * Created by Crystal on 11/14/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import project.weather.viewpager.ViewActivity;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivIcon;
        public TextView tvCity;
        public Button btnDelete;
        public Button btnEdit;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }

    private List<City> cityList;
    private Context context;
    private int lastPosition = -1;

    public CityAdapter(List<City> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_city, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.tvCity.setText(cityList.get(position).getCityName());

        viewHolder.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("cityName", cityList.get(position).getCityName());
                context.startActivity(intent);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCity(position);
            }
        });

        setAnimation(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void addCity(City city) {
        city.save();
        cityList.add(city);
        notifyDataSetChanged();
    }

    public void updateCity(int index, City city) {
        cityList.set(index, city);
        city.save();
        notifyItemChanged(index);
    }

    public void removeCity(int index) {
        // remove it from the DB
        cityList.get(index).delete();
        // remove it from the list
        cityList.remove(index);
        notifyDataSetChanged();
    }

    public void swapCity(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(cityList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(cityList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public City getCity(int i) {
        return cityList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
