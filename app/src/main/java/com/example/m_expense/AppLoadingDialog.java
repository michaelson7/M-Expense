package com.example.m_expense;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

public class AppLoadingDialog {
    Context activity;
    Dialog dialog;
    public AppLoadingDialog(Context activity) {
        this.activity = activity;
        dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
    }

    public void showDialog(String message) {
        ContentLoadingProgressBar gifImageView = dialog.findViewById(R.id.progressbar);
        AppCompatTextView text_message=dialog.findViewById(R.id.text_message);
        text_message.setText(message);
        gifImageView.show();
        dialog.show();
    }
    public void showDialog() {

        ContentLoadingProgressBar gifImageView = dialog.findViewById(R.id.progressbar);
        AppCompatTextView text_message=dialog.findViewById(R.id.text_message);
        gifImageView.show();
        dialog.show();
    }
    public void hideDialog(){
        dialog.dismiss();
    }
    public boolean isShowing(){
        return  dialog.isShowing();
    }
}
