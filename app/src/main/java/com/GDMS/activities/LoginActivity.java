package com.GDMS.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.GDMS.R;
import com.GDMS.Utilities.DialogBoxes;
import com.GDMS.Utilities.Helper;
import com.GDMS.Utilities.Utility;
import com.GDMS.models.Userlogin.Users;
import com.GDMS.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
           // Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    RelativeLayout btn1;
    EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Please Wait..");
        btn1 = findViewById(R.id.btnLogin);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.hasPermissions(LoginActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    String user = userName.getText().toString();
                    String pass = password.getText().toString();

                    if (!user.isEmpty() && !pass.isEmpty()){
                      requestForLogin(user, pass);
                  } else {
                        Toast.makeText(LoginActivity.this, "All field must be filled ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }


    private void requestForLogin(String user, String pass) {
progressDialog.setMessage("Checking Your Credential's");
progressDialog.show();
        if (Utility.isNetworkAvailable(LoginActivity.this)) {

            Call<Users> loginRespone = RestClient.getInstance().getApiServices().userLogin(user,pass);
            loginRespone.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                   if (response.isSuccessful()){
                       switch (response.body().getStatus()){
                           case 1: {
                               progressDialog.dismiss();
                               //  Helper.getInstance().setUserDetails(response.body().getUserDetails());

                               Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                               SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                               editor.putString("user_id", response.body().getUserDetails().getUserid());
                               editor.putString("password", response.body().getUserDetails().getPassword());
                               editor.putString("first_name", response.body().getUserDetails().getFirstname());
                               editor.putString("last_name", response.body().getUserDetails().getLastname());
                               editor.putString("department", response.body().getUserDetails().getDepartment());
                               editor.putString("mail", String.valueOf(response.body().getUserDetails().getUseremail()));
                               editor.putInt("group_id", response.body().getUserDetails().getGroupid());
                               editor.putInt("is_login", 1);
                               editor.apply();

//                                localDB=new LocalDB(context);
//                                if (localDB.isDataAlreadyInDB_or_Not("87273482")){
//                                    Toast.makeText(context, "Allready Scaned!", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    localDB_InsertMethod("87273482");
//                                }
                               //localDB.deleteRow("87273484");

                               Utility.launchActivity(LoginActivity.this, MainActivity.class, true);
                           }
                               break;

                           case 2 :
                           case 5:

                           case 3:
                           case 4:
                           default: {
                               progressDialog.dismiss();
                               DialogBoxes.showSingleButtonDialog(LoginActivity.this, "warning", ""+response.body().getMessage(), "ok", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<Users> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            progressDialog.dismiss();
            DialogBoxes.showSingleButtonDialog(LoginActivity.this, "warning", "Check Your Internet", "ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, true, null);
        }


    }

    private boolean isNotEmpty() {
        if (userName.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }



}