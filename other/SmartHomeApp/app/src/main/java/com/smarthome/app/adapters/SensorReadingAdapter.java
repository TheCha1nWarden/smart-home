package com.smarthome.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthome.app.R;
import com.smarthome.app.model.Sensor;

import java.util.List;

public class SensorReadingAdapter extends RecyclerView.Adapter<SensorReadingAdapter.ViewHolder> {

    private List<Sensor> sensorReadings;

    public SensorReadingAdapter(List<Sensor> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor_reading, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sensor reading = sensorReadings.get(position);
        holder.tvSensorName.setText(reading.getName());
        holder.tvSensorValue.setText(String.format("%s: %s", reading.getLabel(), reading.getValue()));
    }

    @Override
    public int getItemCount() {
        return sensorReadings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSensorName, tvSensorValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSensorName = itemView.findViewById(R.id.tvSensorName);
            tvSensorValue = itemView.findViewById(R.id.tvSensorValue);
        }
    }
}

