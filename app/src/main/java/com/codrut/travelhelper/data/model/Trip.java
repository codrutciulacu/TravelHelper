package com.codrut.travelhelper.data.model;


import com.google.firebase.firestore.DocumentId;

import java.util.Objects;

public class Trip {
    @DocumentId
    private String id;

    private AdditionalInfo additionalInfo;
    private Period period;
    private Destination destination;

    private Trip() {
    }

    public Trip(String id, AdditionalInfo additionalInfo, Period period, Destination destination) {
        this.id = id;
        this.additionalInfo = additionalInfo;
        this.period = period;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id.equals(trip.id) &&
                Objects.equals(additionalInfo, trip.additionalInfo) &&
                Objects.equals(period, trip.period) &&
                Objects.equals(destination, trip.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, additionalInfo, period, destination);
    }

}
