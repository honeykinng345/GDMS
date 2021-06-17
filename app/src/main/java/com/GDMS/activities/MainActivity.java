package com.GDMS.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.GDMS.Adapter.DcListAdapter;
import com.GDMS.R;
import com.GDMS.Utilities.DCListDataBase;
import com.GDMS.Utilities.DialogBoxes;
import com.GDMS.Utilities.Helper;
import com.GDMS.Utilities.Utility;
import com.GDMS.models.CheckLocation.LocationResponse;
import com.GDMS.models.GetDc.Dch;
import com.GDMS.models.GetDc.GetDcResponse;
import com.GDMS.models.ReasonList.RReason;
import com.GDMS.models.Userlogin.UserDetails;
import com.GDMS.network.RestClient;
import com.airbnb.lottie.LottieAnimationView;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DcListAdapter.OnItemClickListener {

    LinearLayoutCompat layoutManuListMain;
    LinearLayoutCompat itemnav;
    DCListDataBase dcListDataBase;
    DrawerLayout drawer;
    ImageButton ivMenu, logout;
    TextView scanQrTv;
    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    LottieAnimationView qr, qrENd;
    RecyclerView recyelerView;
    EditText searchList;
    private static final int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    ProgressDialog progressDialog;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView disLisRV;
    private ArrayList<Dch> dcharrayList = new ArrayList<>();
    boolean checkbtnclick = false;
    private DcListAdapter dcListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        drawer = findViewById(R.id.drawer);
        ivMenu = findViewById(R.id.ivMenu);
        logout = findViewById(R.id.logout);
        layoutManuListMain = findViewById(R.id.layoutManuListMain);
        itemnav = layoutManuListMain.findViewById(R.id.itemnav);
        recyelerView = findViewById(R.id.recyelerView);
        scannerView = findViewById(R.id.scanner_view);
        qr = findViewById(R.id.qr);
        scanQrTv = findViewById(R.id.scanQrTv);
        qrENd = findViewById(R.id.qrENd);
        searchList = findViewById(R.id.searchList);
        mCodeScanner = new CodeScanner(this, scannerView);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Wait..");
        Utility.getLastLocation(MainActivity.this);
        dcListDataBase = DCListDataBase.getInstance(MainActivity.this);
        buildRecyclerView();
        //this method is use to gel current USer lat Loung
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.hideSoftKeyboard(searchList, MainActivity.this);
                checkbtnclick = false;
                recyelerView.setVisibility(View.GONE);
                scannerView.setVisibility(View.VISIBLE);
                openQrScreen();
            }
        });

        qrENd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbtnclick = true;
                recyelerView.setVisibility(View.GONE);
                scannerView.setVisibility(View.VISIBLE);
                openQrScreen();

            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });
        itemnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dcListDataBase.openDatabase();
                dcListDataBase.deleteData();
                dcListDataBase.closeDB();
                scanQrTv.setText("Start Trip");
                qr.setVisibility(View.VISIBLE);
                qrENd.setVisibility(View.GONE);
                recyelerView.setVisibility(View.GONE);
                drawer.closeDrawers();
            }
        });
    }

    private void logoutUser() {
        DialogBoxes.showTwoButtonDialog(MainActivity.this, "WARNING", "Are you sure to logout?", "YES", "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putInt("is_login", 2);
                dcListDataBase.openDatabase();
                dcListDataBase.deleteData();
                dcListDataBase.closeDB();
                editor.apply();
                Utility.launchActivity(MainActivity.this, LoginActivity.class, true);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, true, null);
    }


    private void buildRecyclerView() {
        dcListDataBase.openDatabase();
        dcharrayList = dcListDataBase.getData();
        dcListDataBase.closeDB();
        recyelerView.setHasFixedSize(true); //Increases the app's performance since the size of the items layout won't increase
        mLayoutManager = new LinearLayoutManager(this);
        recyelerView.setLayoutManager(mLayoutManager);
        if (dcharrayList.size() > 0) {
            recyelerView.setVisibility(View.VISIBLE);
            scannerView.setVisibility(View.GONE);
            qr.setVisibility(View.GONE);
            scanQrTv.setText("End trip");
            qrENd.setVisibility(View.VISIBLE);
            dcListAdapter = new DcListAdapter(MainActivity.this, dcharrayList, this);
            recyelerView.setAdapter(dcListAdapter);
            dcListAdapter.notifyDataSetChanged();
        } else {
            recyelerView.setVisibility(View.GONE);
            qr.setVisibility(View.VISIBLE);
            qrENd.setVisibility(View.GONE);

        }
    }

    private void openQrScreen() {
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scannerView.animate();
              //  Toast.makeText(MainActivity.this, "QRCode" + result.getText(), Toast.LENGTH_SHORT).show();
                //Open Scaner and after  fetching result call this below method
                CheckLocation(result.getText());

            }
        }));


    }

    //api callin to check location
    private void CheckLocation(String QrValue) {
        progressDialog.setMessage("Processing... ");
        progressDialog.show();
        Call<LocationResponse> responseCall = RestClient.getInstance().getApiServices().CheckLocation(QrValue);

        responseCall.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {


                if (response.isSuccessful()) {

                    switch (response.body().getStatus()) {
                        case 1: {
                            // call this method to send  lat loung the lat loung we are getting from the api call  in miles b/w to points;
                            Utility.getUserlatLoung(Double.parseDouble(response.body().getLocationDetails().getWHLat()), Double.parseDouble(response.body().getLocationDetails().getWHLong()));
                            //call the Utilie class variable that store the result of two location in miles..
                            //after calling variable who have value of two location in miles
                            //  calling method to convert milies into meters passing the variable who have muiles vales in the below method
                            Log.d("miles", "" + Utility.distanxceMilesValue);
                            //  double latlongMeteres = Utility.milesToMeters(Utility.distanxceMilesValue);
                            //   Toast.makeText(MainActivity.this, ""+latlongMeteres, Toast.LENGTH_SHORT).show();
                            //   Log.d("meteres",""+latlongMeteres);
                            checkLAtLongIsmatchWithUserLatlongOrNot(Utility.distanxceMilesValue);

                        }
                        break;
                        case 2:
                        default: {
                            progressDialog.dismiss();
                            DialogBoxes.showSingleButtonDialog(MainActivity.this, "warning", "" + response.body().getMessage(), "ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, true, null);
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    DialogBoxes.showSingleButtonDialog(MainActivity.this, "warning", "Unknown Error Occurred", "ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, true, null);
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    //this is metthod is to check the employe lat loung is match with user lat loung or not
    private void checkLAtLongIsmatchWithUserLatlongOrNot(double latlongMeteres) {
        dcListDataBase.openDatabase();
        ArrayList<Dch> reasons = dcListDataBase.getData();
        dcListDataBase.closeDB();
        if (latlongMeteres <= 100) {
            if (checkbtnclick) {
                scannerView.setVisibility(View.GONE);

                for (int i = 0; i < reasons.size(); i++) {
                    final String DOCNUMBER = reasons.get(i).getDocnumber();
                    final int DOCSERIAL = reasons.get(i).getDocserial();
                    final int STATUS = reasons.get(i).getStatus();
                    final String DELIVERYDATETIME = reasons.get(i).getDeliverydatetime();
                    final String DELIVERYLAT = reasons.get(i).getDeliverylat();
                    final String DELIVERYLONG = reasons.get(i).getDeliverylong();
                    final String DELIVERYLOCATION = String.valueOf(reasons.get(i).getDeliverylocation());
                    //String test = (String) reasons.get(i).getRreasonid();
                    int RREASONID = 0;

                    RREASONID = reasons.get(i).getRreasonid();
                    final String REMARKS = reasons.get(i).getRemarks();

                    Call<GetDcResponse> responseCall = RestClient.getInstance().getApiServices().UpdateDC(DOCNUMBER, DOCSERIAL
                            , STATUS, DELIVERYDATETIME, DELIVERYLAT, DELIVERYLONG, DELIVERYLOCATION, RREASONID, REMARKS);
                    int finalI = i;
                    responseCall.enqueue(new Callback<GetDcResponse>() {
                        @Override
                        public void onResponse(Call<GetDcResponse> call, Response<GetDcResponse> response) {
                            if (response.isSuccessful()) {
                                switch (response.body().getStatus()) {
                                    case 1: {
                                        dcListDataBase.openDatabase();
                                        if (dcListDataBase.deleteSingleItem(reasons.get(finalI).getDocnumber(), String.valueOf(reasons.get(finalI).getDocserial()))) {
                                            //Toast.makeText(MainActivity.this, "DOne Delted", Toast.LENGTH_SHORT).show();
                                        }else{
                                            //Toast.makeText(MainActivity.this, "DOne no", Toast.LENGTH_SHORT).show();
                                        }
                                        dcListDataBase.closeDB();
                                    }
                                    break;
                                    case 2: {
                                        // Toast.makeText(MainActivity.this, "DOne falied", Toast.LENGTH_SHORT).show();


                                    }
                                    break;
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<GetDcResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

                progressDialog.dismiss();
                scanQrTv.setText("Start Trip");
                qr.setVisibility(View.VISIBLE);
                qrENd.setVisibility(View.GONE);

            } else {
                getDc(Helper.getUserid(MainActivity.this));
            }
            //   Toast.makeText(this, ""+Helper.getUserid(MainActivity.this), Toast.LENGTH_SHORT).show();

        } else {
            scannerView.setVisibility(View.GONE);
            progressDialog.dismiss();
            DialogBoxes.showSingleButtonDialog(MainActivity.this, "Warning Location", "Your location is not Warehouse location", "ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, true, null);
        }

    }

    private void getDc(String userid) {
        Call<GetDcResponse> dcResponseCall = RestClient.getInstance().getApiServices().GetDC(userid);
        dcResponseCall.enqueue(new Callback<GetDcResponse>() {
            @Override
            public void onResponse(Call<GetDcResponse> call, Response<GetDcResponse> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getStatus()) {
                        case 1: {

                            dcListDataBase.openDatabase();
                            Dch dch = new Dch();
                            for (int i = 0; i < response.body().getDch().size(); i++) {
                                dch.setDocnumber(response.body().getDch().get(i).getDocnumber());
                                dch.setDocserial(response.body().getDch().get(i).getDocserial());
                                dch.setVehiclenum(response.body().getDch().get(i).getVehiclenum());
                                dch.setDeliveryby(response.body().getDch().get(i).getDeliveryby());
                                dch.setStatus(response.body().getDch().get(i).getStatus());
                                dch.setSageupdate(response.body().getDch().get(i).getSageupdate());
                                dch.setRemarks(response.body().getDch().get(i).getRemarks());
                                dch.setRreasonid(0);
                                dch.setDeliverylat(response.body().getDch().get(i).getDeliverylat());
                                dch.setDeliverylong(response.body().getDch().get(i).getDeliverylong());
                                dch.setDeliverylocation(String.valueOf(response.body().getDch().get(i).getDeliverylocation()));
                                dch.setSysdatetime(String.valueOf(response.body().getDch().get(i).getSysdatetime()));
                                dcListDataBase.addItemData(dch);

                            }

                            progressDialog.dismiss();
                            dcListDataBase.closeDB();
                            buildRecyclerView();
                        }
                        break;
                        case 2: {
                            scannerView.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            DialogBoxes.showSingleButtonDialog(MainActivity.this, "warning", "" + response.body().getMessage(), "ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, true, null);
                        }
                        break;

                        default: {
                            scannerView.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            DialogBoxes.showSingleButtonDialog(MainActivity.this, "warning", "Unknown Error Occurred", "ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, true, null);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDcResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }


    @Override
    public void onButtonClick(View v, int position) {

        DialogBoxes.showTwoButtonDialog(MainActivity.this, "Confirmation", "Confirm Deliver # \n " + dcharrayList.get(position).getDocnumber(), "YES", "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UpdateDataa(dcharrayList.get(position).getDocnumber(), dcharrayList.get(position).getDocserial());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, true, null);
    }

    public void UpdateDataa(String docnumber, Integer docserial) {
        Utility.getLastLocation(this);
        dcListDataBase.openDatabase();
        if (dcListDataBase.updateList(docnumber, String.valueOf(docserial), String.valueOf(Utility.lat2), String.valueOf(Utility.lon2), Helper.getCurrentDateTime(), Utility.completeAddress, "2")) {
            dcharrayList = dcListDataBase.getData();
            dcListAdapter.updateList(dcharrayList);
        }
        dcListDataBase.closeDB();

    }

}