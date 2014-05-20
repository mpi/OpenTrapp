package com.github.mpi.time_registration.infrastructure.persistence.mongo;

import com.github.mpi.time_registration.domain.time.Day;

public class Interval {
    public final Day start,end;

    public Interval(Day start, Day end) {
        this.start = start;
        this.end = end;
    }
}
