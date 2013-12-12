package com.github.mpi.time_registration.domain;

import java.util.Arrays;
import java.util.Iterator;

public class WorkLog implements Iterable<WorkLogEntry>{

    @Override
    public Iterator<WorkLogEntry> iterator() {
        return Arrays.<WorkLogEntry>asList().iterator();
    }

}
