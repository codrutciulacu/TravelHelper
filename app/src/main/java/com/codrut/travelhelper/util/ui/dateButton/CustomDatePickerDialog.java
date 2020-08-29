package com.codrut.travelhelper.util.ui.dateButton;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.Objects;

public class CustomDatePickerDialog extends DialogFragment {

    private LocalDate date;
    private OnDateChangedListener listener;

    private DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
        date = LocalDate.of(year, month, day);
        listener.dateChanged(date);
    };

    public CustomDatePickerDialog(OnDateChangedListener listener, LocalDate date) {
        this.listener = listener;
        this.date = date;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();

        return new DatePickerDialog(Objects.requireNonNull(getActivity()), dateSetListener, year, month, day);
    }

    public LocalDate getDate() {
        return date;
    }

}
