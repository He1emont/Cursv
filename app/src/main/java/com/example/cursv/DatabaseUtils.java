package com.example.cursv;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;

import java.util.ArrayList;
import java.util.List;


public class DatabaseUtils extends AppCompatActivity {
    //Создание БД
    public void createDatabase() {
        //Создание или открытие базы данных "ReaDocs"
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        //Создание таблиц БД, если они не существуют
        database.execSQL("CREATE TABLE IF NOT EXISTS Human (id INTEGER, Login TEXT, Password TEXT, FullName TEXT, Email TEXT, Phone TEXT, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS Pet (id INTEGER, Name TEXT, Type TEXT, Gender TEXT, DateOfBirth TEXT, IdHuman INTEGER, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS Services (id INTEGER, Name TEXT, Cost DECIMAL, Doctor TEXT, idType INTEGER, DurationMin INTEGER, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS SigningUpForServices (id INTEGER, Date TEXT,  idService INTEGER, idHuman INTEGER, FOREIGN KEY (idService) REFERENCES Services(id), FOREIGN KEY (idHuman) REFERENCES Human(id), UNIQUE(id))");
        dataSetForService();
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

    public long addSigningUpForService(SigningUpForService signingUpForService) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("Date", signingUpForService.getDate());
        values.put("idService", signingUpForService.getIdService());

        long result = db.insert("SigningUpForServices", null, values);

        db.close();
        return result;
    }

    private static final String TABLE_NAME = "Services";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_COST = "Cost";
    private static final String COLUMN_DOCTOR = "Doctor";
    private static final String COLUMN_TYPE = "idType";
    private static final String COLUMN_DURATION= "DurationMin";

    // Метод для добавления записи Service в таблицу Services
    public long addService(Service service) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, service.getNameService());
        values.put(COLUMN_COST, service.getCostService());
        values.put(COLUMN_DOCTOR, service.getDoctor());
        values.put(COLUMN_TYPE, service.getIdType());
        values.put(COLUMN_DURATION, service.getDurationMin());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
    // Метод для получения списка всех услуг из таблицы Services
    public List<Service> getAllServices() {
        List<Service> servicesList = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") double cost = cursor.getDouble(cursor.getColumnIndex(COLUMN_COST));
            @SuppressLint("Range") String doctor = cursor.getString(cursor.getColumnIndex(COLUMN_DOCTOR));
            @SuppressLint("Range") int idType = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") int durationMin = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));

            Service service = new Service(id, name, cost, doctor, idType, durationMin);
            servicesList.add(service);
        }

        cursor.close();
        db.close();

        return servicesList;
    }

    public List<Service> getServicesByType(int type) {
        List<Service> servicesList = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);


        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " IN ("+type+", 3)";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") double cost = cursor.getDouble(cursor.getColumnIndex(COLUMN_COST));
            @SuppressLint("Range") String doctor = cursor.getString(cursor.getColumnIndex(COLUMN_DOCTOR));
            @SuppressLint("Range") int idType = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") int durationMin = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));

            Service service = new Service(id, name, cost, doctor, idType, durationMin);
            servicesList.add(service);
        }

        cursor.close();
        db.close();

        return servicesList;
    }

    private void dataSetForService(){
        Service service1 = new Service(1, "Услуга 1", 100.0, "Иванов Иван Иванович", 1, 5);
        Service service2 = new Service(2, "Услуга 2", 2000.0, "Петров Петр Петрович", 1, 20);
        Service service3 = new Service(3, "Услуга 3", 300.0, "Васильев Василий Васильевич", 2, 10);
        Service service4 = new Service(4, "Услуга 4", 500.0, "Васильев Василий Васильевич", 3, 15);
        addService(service1);
        addService(service2);
        addService(service3);
        addService(service4);
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
        Log.d("Human", name);
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
