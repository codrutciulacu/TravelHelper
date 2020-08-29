package com.codrut.travelhelper.util.ui.dateButton;

import java.time.LocalDate;
import java.util.Date;

public interface OnDateChangedListener {
    void dateChanged(Date date);

    void dateChanged(LocalDate date);
}
