package com.example.androidtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvData;
    ProgressBar progressBar;
    List<Data> dataList;
    DataAdapter dataAdapter ;
    TextView tvTitle,tvComment;
    ImageView imgAva,imgUp;
    LinearLayout lineBar;
    boolean ischecks = true;

    BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        tvComment = findViewById(R.id.tvComment);
        imgAva = findViewById(R.id.imgAva);
        tvTitle = findViewById(R.id.tvTitle);
        lvData = findViewById(R.id.lvData);
        progressBar = findViewById(R.id.progress);
        imgUp = findViewById(R.id.imgUp);
        lineBar = findViewById(R.id.lineBar);

        addDataTo();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


                AndroidNetworking.post("https://api.edupia.vn/service/chicken")
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {

                                    try {
                                        Data data = new Data(response.getJSONObject(position));
                                        tvTitle.setText(data.getName());
                                        tvComment.setText(data.getComment());

                                        String ava = data.getImage();
                                        Glide.with(MainActivity.this).load(ava).into(imgAva);
//
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomsheet, int newstate) {
                        switch (newstate) {
                            case BottomSheetBehavior.STATE_COLLAPSED:
                                imgUp.setImageResource(R.drawable.up);
                                lineBar.setVisibility(View.GONE);
                                ischecks = false;
                                break;
                            case BottomSheetBehavior.STATE_DRAGGING:

                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                imgUp.setImageResource(R.drawable.down);
                                lineBar.setVisibility(View.VISIBLE);
                                ischecks = false;
                                break;
                            case BottomSheetBehavior.STATE_SETTLING:
                                ischecks = true;
                                imgUp.setImageResource(R.drawable.up);
                                lineBar.setVisibility(View.GONE);
                                break;
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View view, float v) {

                    }
                });
            }
        });




    }

    public void addDataTo() {
        dataList = new ArrayList<>();
        dataAdapter = new DataAdapter(this, dataList);
        AndroidNetworking.post("https://api.edupia.vn/service/chicken")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i <response.length() ; i++) {
                            try {
                                Data data = new Data(response.getJSONObject(i));
                                dataList.add(data);
                                lvData.setAdapter(dataAdapter);
                                progressBar.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    @Override
    public void onBackPressed() {

        if (!ischecks) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            ischecks = true;
        } else {
            super.onBackPressed();
            ischecks = false;
        }

    }
}