package com.codingstuff.shoeapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codingstuff.shoeapp.dao.CartDAO;
import com.codingstuff.shoeapp.utils.model.ShoeCart;
//this one copy of the local database Room
@Database(entities = {ShoeCart.class} , version = 1)  //definition the database and the entities in it contain one table shoeCart
public abstract class CartDatabase extends RoomDatabase {
//interface to can access to databasecontain queries relatid available database
    public abstract CartDAO cartDAO();

    //variable to store databaek
    private static CartDatabase instance;
//way to get single instance of database singleton approach verifies and creates it if it does not already exist
    public static synchronized  CartDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                            , CartDatabase.class , "ShoeDatabase")
                            .fallbackToDestructiveMigration()   //This means that if changes occur in the table structure (migration),
                                                                // it deletes the current data and completely rebuilds the table. This is used for development purposes, not production.
                            .build();// ينشئ قاعدة البيانات بناءً على الإعدادات السابق
        }
        return instance;
    }
}
