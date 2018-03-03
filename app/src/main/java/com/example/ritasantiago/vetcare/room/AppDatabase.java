package com.example.ritasantiago.vetcare.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by ritasantiago on 01-03-2018.
 */

@Database(entities = {Animal.class, Internment.class, Historic.class, Procedure.class, Medicine.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase INSTANCE;
    public abstract Animal_dao animalDao();

    public static AppDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,  "animal-database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}

