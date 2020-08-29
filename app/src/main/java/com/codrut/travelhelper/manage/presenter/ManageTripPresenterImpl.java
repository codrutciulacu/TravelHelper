package com.codrut.travelhelper.manage.presenter;

import com.codrut.travelhelper.data.OnResponseListener;
import com.codrut.travelhelper.data.model.Trip;
import com.codrut.travelhelper.data.repository.TripRepository;
import com.codrut.travelhelper.manage.ManageTripContract;

public class ManageTripPresenterImpl implements ManageTripContract.ManageTripPresenter {

    private static ManageTripPresenterImpl instance;
    private ManageTripContract.ManageTripView view;
    private TripRepository repository;

    public ManageTripPresenterImpl(TripRepository repository) {
        this.repository = repository;
    }

    public static synchronized ManageTripPresenterImpl getInstance() {
        if (instance == null) {
            instance = new ManageTripPresenterImpl(TripRepository.getInstance());
        }

        return instance;
    }

    @Override
    public void attachView(ManageTripContract.ManageTripView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        repository = null;
    }

    @Override
    public void save(Trip trip) {
        repository.addTrip(trip, new OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                view.goToMainActivity();
            }

            @Override
            public void onFailure(Exception e) {
                view.showError(e);
            }
        });
    }

    @Override
    public void update(Trip trip) {
        repository.updateTrip(trip, new OnResponseListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                view.goToMainActivity();
            }

            @Override
            public void onFailure(Exception e) {
                view.showError(e);
            }
        });
    }

    @Override
    public void get(String tripId) {
        repository.getTripById(tripId, new OnResponseListener<Trip>() {
            @Override
            public void onSuccess(Trip data) {
                view.populateView(data);
            }

            @Override
            public void onFailure(Exception e) {
                view.showError(e);
            }
        });
    }
}
