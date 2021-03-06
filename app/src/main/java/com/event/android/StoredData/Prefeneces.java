package com.event.android.StoredData;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefeneces {

    public Prefeneces(){}

    public static boolean saveUserData(String username, String password, String token, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context);
        SharedPreferences.Editor prefEditor =  prefs.edit();
        prefEditor.putString(Constants.PREF_USERNAME, username);
        prefEditor.putString(Constants.PREF_PASSWORD,password);
        prefEditor.putString(Constants.PREF_TOKEN,token);
        prefEditor.apply();
        return true;
    }

    public  static boolean ifExists(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getAll().isEmpty() ;
    }


    public  static String getUsername(Context context){
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.PREF_USERNAME,null);
    }
    public  static String getPassword(Context context){
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.PREF_PASSWORD,null);
    }
    public  static String getStoreToken(Context context){
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.PREF_TOKEN,null);
    }
}
