package com.codrut.travelhelper.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codrut.travelhelper.R;
import com.codrut.travelhelper.data.model.AdditionalInfo;
import com.codrut.travelhelper.data.model.Destination;
import com.codrut.travelhelper.data.model.Period;
import com.codrut.travelhelper.data.model.Trip;
import com.codrut.travelhelper.manage.ManageTripContract;
import com.codrut.travelhelper.manage.presenter.ManageTripPresenterImpl;
import com.codrut.travelhelper.util.ui.dateButton.DateButton;
import com.codrut.travelhelper.util.ui.pictureButtons.CameraButton;
import com.codrut.travelhelper.util.ui.pictureButtons.GalleryButton;

import java.time.LocalDate;
import java.util.Objects;

public class ManageTripActivity extends AppCompatActivity implements ManageTripContract.ManageTripView {

    private final static String TAG = "ManageTripActivity";

    private String id;
    private EditText nameEditText;
    private EditText locationEditText;
    private SeekBar priceSeekBar;
    private RatingBar ratingBar;
    private DateButton startDateButton;
    private DateButton endDateButton;
    private GalleryButton galleryButton;
    private CameraButton pictureButton;

    private ManageTripContract.ManageTripPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_trip);

        initView();
        initPresenter();
        getTripId();
    }

    private void initPresenter() {
        presenter = ManageTripPresenterImpl.getInstance();
        presenter.attachView(this);
    }

    private void initView() {
        initFields();
        initRatingAndPriceBars();
        initDateButtons();
        initPictureButtons();
        initSaveButton();
    }

    private void initFields() {
        nameEditText = findViewById(R.id.name_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
    }

    private void initRatingAndPriceBars() {
        priceSeekBar = findViewById(R.id.price_seek_bar);
        ratingBar = findViewById(R.id.rating_bar);
    }

    private void initDateButtons() {
        startDateButton = findViewById(R.id.start_date_button);
        startDateButton.setOnClickListener(v -> startDateButton.showDialog(getSupportFragmentManager()));

        endDateButton = findViewById(R.id.end_date_button);
        endDateButton.setOnClickListener(v -> endDateButton.showDialog(getSupportFragmentManager()));
    }

    private void initPictureButtons() {
        galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(v -> galleryButton.dispatchGalleryIntent(this));

        pictureButton = findViewById(R.id.pictureButton);
        pictureButton.setOnClickListener(v -> pictureButton.dispatchTakePictureIntent(this));
    }

    private void getTripId() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle idBundle = intent.getExtras();
            if (idBundle != null) {
                id = idBundle.getString("id");
                presenter.get(id);
            }
        }
    }

    @Override
    public void populateView(Trip data) {
        nameEditText.setText(data.getAdditionalInfo().getTitle());
        locationEditText.setText(data.getDestination().getName());
        ratingBar.setRating(data.getAdditionalInfo().getRating());
        priceSeekBar.setProgress(data.getAdditionalInfo().getPrice());
        startDateButton.dateChanged(data.getPeriod().getStartDate());
        endDateButton.dateChanged(data.getPeriod().getEndDate());
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            showSavedData();

            Trip trip = getFiledDataAsTrip();

            if (id != null) {
                presenter.update(trip);
            } else {
                presenter.save(trip);
            }
        });

    }

    private Trip getFiledDataAsTrip() {
        String name = nameEditText.getText().toString();
        String locationName = locationEditText.getText().toString();
        float rating = ratingBar.getRating();
        int price = priceSeekBar.getProgress();
        LocalDate startDate = startDateButton.getDate();
        LocalDate endDate = endDateButton.getDate();

        AdditionalInfo info = new AdditionalInfo(name, rating, price);
        Destination location = new Destination(locationName, null);
        Period period = new Period(startDate, endDate);

        return new Trip(id, info, period, location);
    }

    private void showSavedData() {
        Log.d(TAG, nameEditText.getText().toString());
        Log.d(TAG, locationEditText.getText().toString());
        Log.d(TAG, String.valueOf(ratingBar.getRating()));
        Log.d(TAG, String.valueOf(priceSeekBar.getProgress()));
        Log.d(TAG, startDateButton.getDate().toString());
        Log.d(TAG, endDateButton.getDate().toString());
        Glide.with(this)
                .load(pictureButton.getPicture())
                .centerCrop()
                .into((ImageView) findViewById(R.id.imageView));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryButton.REQUEST_SELECT_IMAGE) {
            galleryButton.handleResultImage(resultCode, data);
        } else if (requestCode == CameraButton.REQUEST_IMAGE_CAPTURE) {
            pictureButton.handleImageCameraResult(resultCode, data);
        }
    }

    @Override
    public void goToMainActivity() {
        //startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showError(Exception e) {
        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}