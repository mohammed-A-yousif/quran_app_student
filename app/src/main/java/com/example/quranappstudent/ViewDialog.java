package com.example.quranappstudent;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;


public class ViewDialog {

    LottieAnimationView mLottieAnimationView;
    String mAnimFile = "world-locations.json";

    Activity activity;
    Dialog dialog;

    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        dialog  = new Dialog(activity);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dailog_layout);


        mLottieAnimationView = dialog.findViewById(R.id.lottie_animation_view);
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }

}