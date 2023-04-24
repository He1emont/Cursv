package com.example.cursv;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DatabaseUtils extends AppCompatActivity {
    //Создание БД
    public void createDatabase() {
        //Создание или открытие базы данных "ReaDocs"
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        //Создание таблиц БД, если они не существуют
        database.execSQL("CREATE TABLE IF NOT EXISTS Human (id INTEGER, Login TEXT, Password TEXT, FullName TEXT, Email TEXT, Phone TEXT, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS Pet (id INTEGER, Name TEXT, Type TEXT, Gender TEXT, DateOfBirth TEXT, IdHuman INTEGER, UNIQUE(id))");
    }

    //Добавление пользователя
    public int addHuman(String loginHuman, String passwordHuman, String fullNameHuman, String emailHuman, String phoneHuman) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        Cursor queryHuman = database.rawQuery("SELECT * FROM Human;", null);

        int id = queryHuman.getCount();

        database.execSQL("INSERT INTO Human VALUES " +
                "(" + id + ", '" + loginHuman + "', '" + passwordHuman + "', '" + fullNameHuman + "', '" + emailHuman + "', '" + phoneHuman + "');");

        queryHuman.close();
        database.close();

        return id;
    }

    //Добавление питомца
    public int addPet(String namePet, String typePet, String genderPet, String dateOfBirthPet, int idHuman) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        Cursor queryPet = database.rawQuery("SELECT * FROM Pet;", null);

        int id = queryPet.getCount();

        database.execSQL("INSERT INTO Pet VALUES " +
                "(" + id + ", '" + namePet + "', '" + typePet + "', '" + genderPet + "', '" + dateOfBirthPet + "', " + idHuman + ");");

        queryPet.close();
        database.close();

        return id;
    }

    //Получение имени пользователя
    public String getHumanName(int idHuman) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        Cursor queryHuman = database.rawQuery("SELECT * FROM Human;", null);

        String name = null;
        //Получение полдного имени пользователя
        while (queryHuman.moveToNext()) {
            int id = queryHuman.getInt(0);
            if(id == idHuman){
                name = queryHuman.getString(3);
                break;
            }
        }
        queryHuman.close();
        database.close();

        return name;
    }

    //Получение имени пользователя
    public ArrayList<String> getPetInfo(int idPet1) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        Cursor queryPet = database.rawQuery("SELECT * FROM Pet;", null);

        String name = null;
        String type = null;
        String gender = null;
        String dateOfBirth = null;

        //Получение полдного имени пользователя
        while (queryPet.moveToNext()) {
            int id = queryPet.getInt(0);
            if(id == idPet1){
                name = queryPet.getString(1);
                type = queryPet.getString(2);
                gender = queryPet.getString(3);
                dateOfBirth = queryPet.getString(4);
                break;
            }
        }
        queryPet.close();
        database.close();

        ArrayList<String> infoPet = new ArrayList<String>();
        infoPet.add(name);
        infoPet.add(type);
        infoPet.add(gender);
        infoPet.add(dateOfBirth);

        return infoPet;
    }
}
