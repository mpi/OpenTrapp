package com.github.mpi.time_registration.domain;

import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;

public interface EntryIDSequence {

    public EntryID nextID();

}
