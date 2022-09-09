package com.example.propelrrhandsonexam;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResponseDialog {

    private final Context context;
    private Dialog dialog;

    ResponseAdapter responseAdapter;

    List<Response> responseList = new ArrayList<>();

    public ResponseDialog(Context context) {
        this.context = context;

        createDialog();
    }

    private void createDialog() {
        setDialog();
        setDialogWindow();
    }

    private void setDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reponse_layout);

        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        responseAdapter = new ResponseAdapter(responseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(responseAdapter);

        Button btnClose = dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(view -> dismissDialog());
    }

    private void setDialogWindow() {
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void showDialog(List<Response> responseList) {
        dialog.show();

        this.responseList.clear();
        this.responseList.addAll(responseList);
        responseAdapter.notifyDataSetChanged();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
