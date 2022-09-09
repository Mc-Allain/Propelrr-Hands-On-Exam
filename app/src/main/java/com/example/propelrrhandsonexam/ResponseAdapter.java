package com.example.propelrrhandsonexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder> {

    List<Response> responseList;

    Context context;

    public ResponseAdapter(List<Response> responseList) {
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_response_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseAdapter.ViewHolder holder, int position) {
        Response response = responseList.get(position);

        holder.tvFullName.setText(response.getFullName());
        holder.tvEmailAddress.setText(response.getEmailAddress());
        holder.tvMobileNumber.setText(response.getMobileNumber());

        String stringDateTime = response.getDateOfBirth() + " 00:00:00";
        DateTime dateTime = new DateTime(stringDateTime);

        holder.tvDateOfBirth.setText(response.getDateOfBirth());

        int age = (int) Math.floor(Units.msToYear(
                new DateTime().getDateTimeValue() - dateTime.getDateTimeValue())
        );
        holder.tvAge.setText(context.getString(R.string.age_value, age));

        holder.tvGender.setText(response.getGender());
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFullName, tvEmailAddress, tvMobileNumber, tvDateOfBirth, tvAge, tvGender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmailAddress = itemView.findViewById(R.id.tvEmailAddress);
            tvMobileNumber = itemView.findViewById(R.id.tvMobileNumber);
            tvDateOfBirth = itemView.findViewById(R.id.tvDateOfBirth);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvGender = itemView.findViewById(R.id.tvGender);
        }
    }
}
