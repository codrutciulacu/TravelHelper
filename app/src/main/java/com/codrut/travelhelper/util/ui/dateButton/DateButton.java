package com.codrut.travelhelper.util.ui.dateButton;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.codrut.travelhelper.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateButton extends androidx.appcompat.widget.AppCompatButton implements OnDateChangedListener {

    private LocalDate currentDate = LocalDate.now();
    private CustomDatePickerDialog pickerDialog;
    private Context context;

    public DateButton(@NonNull Context context) {
        this(context, null);
    }

    public DateButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initPickerDialog();
    }

    private void initPickerDialog() {
        pickerDialog = new CustomDatePickerDialog(this, currentDate);
    }

    public void showDialog(FragmentManager manager) {
        pickerDialog.show(manager, "Date Picker");
    }

    public LocalDate getDate() {
        return currentDate;
    }

    @Override
    public void dateChanged(Date date) {
        currentDate = convertToLocalDateViaInstant(date);

        setDateAsButtonText();
    }

    @Override
    public void dateChanged(LocalDate date) {
        currentDate = date;

        setDateAsButtonText();
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void setDateAsButtonText() {
        String year = String.valueOf(currentDate.getYear());
        String month = String.valueOf(currentDate.getMonthValue());
        String day = String.valueOf(currentDate.getDayOfMonth());
        setText(context.getString(R.string.date_format, day, month, year));

    }
}
