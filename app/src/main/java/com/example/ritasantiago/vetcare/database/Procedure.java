package com.example.ritasantiago.vetcare.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ritasantiago on 01-03-2018.
 */

@Entity(tableName = "procedure")
public class Procedure {
    @PrimaryKey(autoGenerate = true)
    private  int proc_id;

    public int getProc_id(){
        return proc_id;
    }

    public void setProc_id(int proc_id){
        this.proc_id = proc_id;
    }

    @ColumnInfo(name = "dateProcedure")
    private String dateProcedure;

    public String getDateProcedure(){
        return dateProcedure;
    }

    public void setDateProcedure(String dateProcedure){
        this.dateProcedure = dateProcedure;
    }

    @ColumnInfo(name = "procedure_picture")
    private int procedure_pic_id;

    public int getProcedure_pic_id() {
        return procedure_pic_id;
    }

    public void setProcedure_pic_id(int procedure_pic_id){
        this.procedure_pic_id = procedure_pic_id;
    }
}
