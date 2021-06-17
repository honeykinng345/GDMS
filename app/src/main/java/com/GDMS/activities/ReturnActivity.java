package com.GDMS.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.GDMS.Adapter.StringArrayAdapter;
import com.GDMS.R;
import com.GDMS.Utilities.DCListDataBase;
import com.GDMS.Utilities.Helper;
import com.GDMS.Utilities.Utility;
import com.GDMS.models.ReasonList.RReason;
import com.GDMS.models.ReasonList.ReasonListResponse;
import com.GDMS.network.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnActivity extends AppCompatActivity {
    StringArrayAdapter arrayAdapter;
    Spinner spinner1;
    ArrayList<RReason>modelList;
    RelativeLayout btnSubmit;
    EditText  edtText;
    int RREASONID;
    String reason = " ";

    DCListDataBase dcListDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        spinner1 = findViewById(R.id.spiner);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtText = findViewById(R.id.edtText);

        modelList = new ArrayList<>();

   dcListDataBase = DCListDataBase.getInstance(ReturnActivity.this);
        getsponerData();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    RREASONID = modelList.get(position).getRreasonid();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Utility.getLastLocation(ReturnActivity.this);
              reason =  edtText.getText().toString().trim();
        dcListDataBase.openDatabase();
     if (dcListDataBase.updateList(Helper.getDch().getDocnumber(), String.valueOf(Helper.getDch().getDocserial()),String.valueOf(Utility.lat2)
                        ,String.valueOf(Utility.lon2),Helper.getCurrentDateTime(),Utility.completeAddress,String.valueOf(RREASONID),reason,"3")){



     Utility.launchActivity(ReturnActivity.this,MainActivity.class,true);
         dcListDataBase.closeDB();
     }
            }
        });

    }

    private void getsponerData() {

        Call<ReasonListResponse> reasonListResponseCall  = RestClient.getInstance().getApiServices().RReasonList();

        reasonListResponseCall.enqueue(new Callback<ReasonListResponse>() {
            @Override
            public void onResponse(Call<ReasonListResponse> call, Response<ReasonListResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus() == 1){


                        for (int i = 0; i <response.body().getRReasonList().size() ; i++) {
                           modelList.add(response.body().getRReasonList().get(i));

                        }

                        RReason rReason = new RReason();
                        rReason.setRreasondesc("Select");
                        rReason.setRreasonid(1000);

                        modelList.add(response.body().getRReasonList().set(0,rReason));
                        arrayAdapter = new StringArrayAdapter(ReturnActivity.this, modelList);
                        spinner1.setAdapter(arrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReasonListResponse> call, Throwable t) {
                Toast.makeText(ReturnActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.launchActivity(ReturnActivity.this,MainActivity.class,false);
    }
}