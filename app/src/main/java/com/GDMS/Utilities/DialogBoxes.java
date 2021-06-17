package com.GDMS.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.GDMS.R;

public class DialogBoxes {


    public static void showSingleButtonDialog(@NonNull Context context, String title, @NonNull String msg, String buttonText,
                                              final DialogInterface.OnClickListener listener, boolean shouldShowButton,
                                              DialogInterface.OnDismissListener dismissListener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        dialog.setContentView(R.layout.dialog_costum);


        ((TextView) dialog.findViewById(R.id.single_btn_dialog_msg)).setText(msg);
        if (title == null) {
            dialog.findViewById(R.id.single_btn_dialog_msg).setVisibility(View.GONE);
        } else {
            ((TextView) dialog.findViewById(R.id.single_btn_dialog_title)).setText(title);
        }

        if (!shouldShowButton) {
            dialog.findViewById(R.id.dialog_one_button).setVisibility(View.GONE);
        } else {
            if (buttonText != null) {
                ((TextView) dialog.findViewById(R.id.dialog_one_button)).setText(buttonText);
            }
            dialog.findViewById(R.id.dialog_one_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(dialog, 0);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
        if (dismissListener != null) {
            dialog.setOnDismissListener(dismissListener);
        }
        dialog.show();
    }
    public static void showTwoButtonDialog(@NonNull Context context, String title, @NonNull String msg, String btnTxt1, String btnTxt2,
                                           final DialogInterface.OnClickListener btnListener1, final DialogInterface.OnClickListener btnListener2,
                                           boolean shouldShowButtons, DialogInterface.OnDismissListener dismissListener) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        dialog.setContentView(R.layout.dialog_costum);

        ((TextView) dialog.findViewById(R.id.single_btn_dialog_msg)).setText(msg);
        if (title == null) {
            dialog.findViewById(R.id.single_btn_dialog_msg).setVisibility(View.GONE);
        } else {
            ((TextView) dialog.findViewById(R.id.single_btn_dialog_title)).setText(title);
        }

        if (!shouldShowButtons) {
            dialog.findViewById(R.id.dialog_one_button).setVisibility(View.GONE);
            dialog.findViewById(R.id.dialog_two_button).setVisibility(View.GONE);
        } else {
            if (btnTxt1 != null) {
                ((TextView) dialog.findViewById(R.id.dialog_one_button)).setText(btnTxt1);
            }
            dialog.findViewById(R.id.dialog_one_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnListener1 != null) {
                        btnListener1.onClick(dialog, 0);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            if (btnTxt2 != null) {
                ((TextView) dialog.findViewById(R.id.dialog_two_button)).setText(btnTxt2);
            }
            dialog.findViewById(R.id.dialog_two_button).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.dialog_two_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnListener2 != null) {
                        btnListener2.onClick(dialog, 0);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
        if (dismissListener != null) {
            dialog.setOnDismissListener(dismissListener);
        }
        dialog.show();
    }
}
