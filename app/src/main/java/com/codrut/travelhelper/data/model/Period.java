package com.codrut.travelhelper.data.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class Period {
    private Date startDate;
    private Date endDate;

    private Period() {
    }

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.endDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(startDate, period.startDate) &&
                Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

}
