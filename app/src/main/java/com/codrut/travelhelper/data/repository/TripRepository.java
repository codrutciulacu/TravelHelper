package com.codrut.travelhelper.data.repository;

import com.codrut.travelhelper.data.OnResponseListener;
import com.codrut.travelhelper.data.model.Trip;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripRepository {

    private static TripRepository INSTANCE;
    private CollectionReference collection;

    private TripRepository(String collectionName) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        this.collection = firestore.collection(collectionName);
    }

    public static synchronized TripRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TripRepository(CollectionNames.tripsCollection);
        }
        return INSTANCE;
    }

    public void addTrip(Trip trip, OnResponseListener<Void> listener) {
        collection.add(trip)
                .addOnSuccessListener(response -> listener.onSuccess(null))
                .addOnFailureListener(listener::onFailure);
    }

    public void getTrips(OnResponseListener<List<Trip>> listener) {
        collection.get()
                .addOnSuccessListener(response -> {
                    List<Trip> tripList = response.toObjects(Trip.class);
                    listener.onSuccess(tripList);
                })
                .addOnFailureListener(listener::onFailure);
    }

    public void getTripById(String id, OnResponseListener<Trip> listener) {
        collection.document(id).get()
                .addOnSuccessListener(response -> {
                    Trip trip = response.toObject(Trip.class);
                    listener.onSuccess(trip);
                })
                .addOnFailureListener(listener::onFailure);
    }

    public void updateTrip(Trip trip, OnResponseListener<Void> listener) {
        Map<String, Object> tripMap = getTripAsMap(trip);

        collection.document(trip.getId()).update(tripMap)
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(listener::onFailure);
    }

    private Map<String, Object> getTripAsMap(Trip trip) {
        Map<String, Object> map = new HashMap<>();

        map.put("additionalInfo", trip.getAdditionalInfo());
        map.put("period", trip.getPeriod());
        map.put("Destination", trip.getDestination());

        return map;
    }

    public void deleteTrip(String id, OnResponseListener<Void> listener) {
        collection.document(id).delete()
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(listener::onFailure);
    }
}
