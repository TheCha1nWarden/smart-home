package com.smarthome.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthome.app.R;
import com.smarthome.app.model.Device;
import com.smarthome.app.model.Status;

import java.util.List;
import java.util.Objects;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private List<Device> devices;
    private OnDeviceToggleListener toggleListener;

    public DeviceAdapter(List<Device> devices, OnDeviceToggleListener toggleListener) {
        this.devices = devices;
        this.toggleListener = toggleListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Device device = devices.get(position);
        holder.tvDeviceName.setText(device.getName());
        holder.switchDevice.setChecked(Objects.equals(device.isStatus(), Status.ON));

        holder.switchDevice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleListener.onToggle(device.getId(), isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeviceName;
        Switch switchDevice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            switchDevice = itemView.findViewById(R.id.switchDevice);
        }
    }

    public interface OnDeviceToggleListener {
        void onToggle(Long deviceId, boolean isOn);
    }
}

