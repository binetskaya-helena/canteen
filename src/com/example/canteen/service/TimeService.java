package com.example.canteen.service;

import java.util.Date;

public interface TimeService {
    Date now();
    void schedule(Date when, Runnable action);
    void cancel(Runnable action);
}
