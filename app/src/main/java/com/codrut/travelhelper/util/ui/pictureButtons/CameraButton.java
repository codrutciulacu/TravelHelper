package com.codrut.travelhelper.util.ui.pictureButtons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;

public class CameraButton extends AppCompatButton {

    public static final int REQUEST_IMAGE_CAPTURE = 1234;
    private Bitmap picture;

    public CameraButton(@NonNull Context context) {
        super(context);
    }

    public CameraButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void dispatchTakePictureIntent(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void handleImageCameraResult(int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = Objects.requireNonNull(data.getExtras());
            picture = (Bitmap) extras.get("data");
        }
    }

    public Bitmap getPicture() {
        return picture;
    }
}
