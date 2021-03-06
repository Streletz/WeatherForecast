package streletzcoder.weatherinfo.gui_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import streletzcoder.weatherinfo.R;
import streletzcoder.weatherinfo.models.WeatherInfo;

public class DaysListAdapter extends RecyclerView.Adapter<DaysListAdapter.ViewHolder> {

    private ArrayList<WeatherInfo> items = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public DaysListAdapter(Context context, ArrayList<WeatherInfo> items) {
        this.context = context;
        this.items.addAll(items);
        this.inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherInfo info = items.get(position);
        if (position == 0) {
            holder.dateText.setText(String.format("%s (сегодня)", info.getShortDate()));
        } else {
            holder.dateText.setText(info.getShortDate());
        }
        holder.weatherText.setText(info.getShortInfoOnlyWeather());
        switch (info.getWeatherType()) {
            case CLEAR:
                holder.imgWeather.setImageResource(R.drawable.white_balance_sunny);
                break;
            case CLOUDS:
                holder.imgWeather.setImageResource(R.drawable.cloud_outline);
                break;
            case RAIN:
                holder.imgWeather.setImageResource(R.drawable.weather_rainy);
                break;
            case SNOW:
                holder.imgWeather.setImageResource(R.drawable.weather_snowy);
                break;
            case UNKNOWN:
                holder.imgWeather.setImageResource(R.drawable.account_question);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dateText;
        final TextView weatherText;
        final ImageView imgWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            weatherText = itemView.findViewById(R.id.weatherText);
            imgWeather = itemView.findViewById(R.id.imgWeather);
        }
    }
}
