package com.codrut.travelhelper.util.ui.pictureButtons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import java.io.IOException;

public class GalleryButton extends AppCompatButton {

    public static final int REQUEST_SELECT_IMAGE = 1235;
    private Context context;
    private Bitmap picture;

    public GalleryButton(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public GalleryButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public GalleryButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void dispatchGalleryIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(activity.getPackageManager()) != null)
            activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                    REQUEST_SELECT_IMAGE);
    }

    public void handleResultImage(int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
                picture = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                        data.getData());
            } catch (IOException e) {
                //TODO: Start Manage Activity on failure
                e.printStackTrace();
            }
        }
    }

    public Bitmap getPicture() {
        return picture;
    }
}
