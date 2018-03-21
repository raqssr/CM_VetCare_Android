package com.example.ritasantiago.vetcare.db.entity;

import java.io.Serializable;

/**
 * Created by raquelramos on 20-03-2018.
 */

public class Task implements Serializable {

    public String animal_name;
    public String task_name;
    public String task_hour;

    public Task(String animal_name, String task_name, String task_hour) {
        this.animal_name = animal_name;
        this.task_name = task_name;
        this.task_hour = task_hour;
    }
}
