package com.codrut.travelhelper.manage;

import com.codrut.travelhelper.BasePresenter;
import com.codrut.travelhelper.data.model.Trip;

public class ManageTripContract {

    public interface ManageTripView {
        void populateView(Trip data);

        void goToMainActivity();

        void showError(Exception e);
    }

    public interface ManageTripPresenter extends BasePresenter<ManageTripView> {
        void save(Trip trip);

        void update(Trip trip);

        void get(String tripId);
    }
}
