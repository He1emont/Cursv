package com.example.cursv;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cursv.Models.Human;
import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.Models.VeterinarianType;
import com.example.cursv.Models.Veterinarians;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DatabaseUtils extends AppCompatActivity {
    //Создание БД
    public void createDatabase() {
        //Создание или открытие базы данных "ReaDocs"
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        //Создание таблиц БД, если они не существуют
        database.execSQL("CREATE TABLE IF NOT EXISTS Human (id INTEGER PRIMARY KEY AUTOINCREMENT, Login TEXT, Password TEXT, FullName TEXT, Email TEXT, Phone TEXT, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS Pet (id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Type TEXT, Gender TEXT, DateOfBirth TEXT, IdHuman INTEGER, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS VeterinarianTypes (id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS Veterinarians (id INTEGER PRIMARY KEY AUTOINCREMENT, FIO TEXT, Experience INTEGER, idVeterinarianType INTEGER, FOREIGN KEY (idVeterinarianType) REFERENCES VeterinarianTypes(id), UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS Services (id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Cost DECIMAL, idDoctor INTEGER, idType INTEGER, DurationMin INTEGER, FOREIGN KEY (idDoctor) REFERENCES Veterinarians(id), UNIQUE(id))");
        database.execSQL("CREATE TABLE IF NOT EXISTS SigningUpForServices (id LONG, Date DATETIME,  idService INTEGER, idHuman INTEGER, Address TEXT, FOREIGN KEY (idService) REFERENCES Services(id), FOREIGN KEY (idHuman) REFERENCES Human(id), UNIQUE(id))");
        dataSetForVeterinarianType();
        dataSetForVeterinarians();
        dataSetForService();
    }

    public long addVeterinarianType(VeterinarianType veterinarianType) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("Name", veterinarianType.getName());

        long result = db.insert("VeterinarianTypes", null, values);
        db.close();

        return result;
    }

    public VeterinarianType getVeterinarianTypeById(int id) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM VeterinarianTypes WHERE id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        VeterinarianType veterinarianType = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
            veterinarianType = new VeterinarianType(id, name);
        }

        cursor.close();
        db.close();

        return veterinarianType;
    }

    public long addVeterinarians(Veterinarians veterinarians) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("FIO", veterinarians.getFio());
        values.put("Experience", veterinarians.getExperience());
        values.put("idVeterinarianType", veterinarians.getIdVeterinarianType());

        long result = db.insert("Veterinarians", null, values);
        db.close();
        return result;
    }

    public Veterinarians getVeterinarianById(int id) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM Veterinarians WHERE  id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        Veterinarians Veterinarian = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String fio = cursor.getString(cursor.getColumnIndex("FIO"));
            @SuppressLint("Range") int experience = cursor.getInt(cursor.getColumnIndex("Experience"));
            @SuppressLint("Range") int idVeterinarianType = cursor.getInt(cursor.getColumnIndex("idVeterinarianType"));

            Veterinarian = new Veterinarians(id, fio, experience, idVeterinarianType);
        }

        cursor.close();
        db.close();

        return Veterinarian;
    }

    public List<Veterinarians> getAllVeterinarians() {
        List<Veterinarians> veterinarians = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM Veterinarians";
        Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String fio = cursor.getString(cursor.getColumnIndex("FIO"));
            @SuppressLint("Range") int experience = cursor.getInt(cursor.getColumnIndex("Experience"));
            @SuppressLint("Range") int idVeterinarianType = cursor.getInt(cursor.getColumnIndex("idVeterinarianType"));

            Veterinarians veterinarian = new Veterinarians(id, fio, experience, idVeterinarianType);
            veterinarians.add(veterinarian);
        }

        cursor.close();
        db.close();

        return veterinarians;
    }

    //Добавление пользователя
    public long addHuman(String loginHuman, String passwordHuman, String fullNameHuman, String emailHuman, String phoneHuman) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("Login", loginHuman);
        values.put("Password", passwordHuman);
        values.put("FullName", fullNameHuman);
        values.put("Email", emailHuman);
        values.put("Phone", phoneHuman);
        long id = database.insert("Human", null, values);
        database.close();

        return id;
    }

    public long addSigningUpForService(SigningUpForService signingUpForService) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("id", signingUpForService.getId());
        values.put("Date", signingUpForService.getDate().toString());
        values.put("idService", signingUpForService.getIdService());
        values.put("idHuman", signingUpForService.getIdHuman());
        values.put("Address", signingUpForService.getAddress());


        db.insert("SigningUpForServices", null, values);

        db.close();
        return signingUpForService.getId();
    }

    public List<SigningUpForService> getAllSigning() {
        List<SigningUpForService> signingUpForServices = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM SigningUpForServices";
        Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") long serviceId = cursor.getLong(cursor.getColumnIndex("idService"));
            @SuppressLint("Range") long humanId = cursor.getLong(cursor.getColumnIndex("idHuman"));
            @SuppressLint("Range") String dateString = cursor.getString(cursor.getColumnIndex("Date"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("Address"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime date = LocalDateTime.parse(dateString, formatter);
            SigningUpForService signingUpForService = new SigningUpForService(id, date, (int) serviceId, (int) humanId, address);
            signingUpForServices.add(signingUpForService);
        }

        cursor.close();
        db.close();

        return signingUpForServices;
    }

    public List<SigningUpForService> getSigningUpForServiceByHumanId(int idHuman) {
        List<SigningUpForService> signingUpForServices = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);

        String query = "SELECT * FROM SigningUpForServices WHERE idHuman = ?";
        String[] selectionArgs = {String.valueOf(idHuman)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") long serviceId = cursor.getLong(cursor.getColumnIndex("idService"));
            @SuppressLint("Range") long humanId = cursor.getLong(cursor.getColumnIndex("idHuman"));
            @SuppressLint("Range") String dateString = cursor.getString(cursor.getColumnIndex("Date"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("Address"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime date = LocalDateTime.parse(dateString, formatter);
            SigningUpForService signingUpForService = new SigningUpForService(id, date, (int) serviceId, (int) humanId, address);
            signingUpForServices.add(signingUpForService);
        }

        cursor.close();
        db.close();

        return signingUpForServices;
    }

    public SigningUpForService getSigningUpForServiceById(long id) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM SigningUpForServices WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);

        SigningUpForService signingUpForService = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") long serviceId = cursor.getLong(cursor.getColumnIndex("idService"));
            @SuppressLint("Range") long humanId = cursor.getLong(cursor.getColumnIndex("idHuman"));
            @SuppressLint("Range") String dateString = cursor.getString(cursor.getColumnIndex("Date"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("Address"));

            // Преобразование строки в LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime date = LocalDateTime.parse(dateString, formatter);

            signingUpForService = new SigningUpForService(id, date, (int) serviceId, (int) humanId, address);
        }

        cursor.close();
        db.close();

        return signingUpForService;
    }

    private static final String TABLE_NAME = "Services";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_COST = "Cost";
    private static final String COLUMN_DOCTOR = "idDoctor";
    private static final String COLUMN_TYPE = "idType";
    private static final String COLUMN_DURATION= "DurationMin";

    // Метод для добавления записи Service в таблицу Services
    public long addService(Service service) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, service.getNameService());
        values.put(COLUMN_COST, service.getCostService());
        values.put(COLUMN_DOCTOR, service.getIdDoctor());
        values.put(COLUMN_TYPE, service.getIdType());
        values.put(COLUMN_DURATION, service.getDurationMin());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public List<Service> getServicesByDoctorId(int idDoctor) {
        List<Service> servicesList = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DOCTOR + " = ?";
        String[] selectionArgs = {String.valueOf(idDoctor)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") double cost = cursor.getDouble(cursor.getColumnIndex(COLUMN_COST));
            @SuppressLint("Range") int doctor = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCTOR));
            @SuppressLint("Range") int idType = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") int durationMin = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));

            Service service = new Service(id, name, cost, doctor, idType, durationMin);
            servicesList.add(service);
        }

        cursor.close();
        db.close();

        return servicesList;
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
            @SuppressLint("Range") int doctor = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCTOR));
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
        Service service1 = new Service(1, "Услуга 1", 100.0, 1, 1, 5);
        Service service2 = new Service(2, "Услуга 2", 2000.0, 2, 1, 20);
        Service service3 = new Service(3, "Услуга 3", 300.0, 5, 2, 10);
        Service service4 = new Service(4, "Услуга 4", 500.0, 4, 1, 15);
        Service service5 = new Service(5, "Услуга 5", 800.0, 3, 2, 15);
        addService(service1);
        addService(service2);
        addService(service3);
        addService(service4);
        addService(service5);
    }

    private void dataSetForVeterinarianType(){
        VeterinarianType veterinarianType1 = new VeterinarianType(1, "Ветеринарный врач-хирург");
        VeterinarianType veterinarianType2 = new VeterinarianType(2, "Ветеринарный врач-эндокринолог");
        VeterinarianType veterinarianType3 = new VeterinarianType(3, "Ветеринарный врач-онколог");
        VeterinarianType veterinarianType4 = new VeterinarianType(4, "Ветеринарный врач-кардиолог");
        VeterinarianType veterinarianType5 = new VeterinarianType(5, "Ветеринарный врач-дерматолог");
        addVeterinarianType(veterinarianType1);
        addVeterinarianType(veterinarianType2);
        addVeterinarianType(veterinarianType3);
        addVeterinarianType(veterinarianType4);
        addVeterinarianType(veterinarianType5);
    }

    private void dataSetForVeterinarians(){
        Veterinarians veterinarian1 = new Veterinarians(1, "Иванов Иван Иванович", 5, 1);
        Veterinarians veterinarian2 = new Veterinarians(1, "Петров Петр Петрович", 3, 2);
        Veterinarians veterinarian3 = new Veterinarians(1, "Васильев Василий Васильевич", 2, 3);
        Veterinarians veterinarian4 = new Veterinarians(1, "Иваннько Евгений Андреевич", 8, 4);
        Veterinarians veterinarian5 = new Veterinarians(1, "Андреев Андрей Петрович", 1, 5);
        addVeterinarians(veterinarian1);
        addVeterinarians(veterinarian2);
        addVeterinarians(veterinarian3);
        addVeterinarians(veterinarian4);
        addVeterinarians(veterinarian5);
    }

    public Service getServiceById(int id) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        Service service = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") double cost = cursor.getDouble(cursor.getColumnIndex(COLUMN_COST));
            @SuppressLint("Range") int doctor = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCTOR));
            @SuppressLint("Range") int idType = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") int durationMin = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));

            service = new Service(id, name, cost, doctor, idType, durationMin);
        }

        cursor.close();
        db.close();

        return service;
    }
    public List<Service> getServicesByType(int type) {
        List<Service> servicesList = new ArrayList<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);


        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " IN ("+type+")";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") double cost = cursor.getDouble(cursor.getColumnIndex(COLUMN_COST));
            @SuppressLint("Range") int doctor = cursor.getInt(cursor.getColumnIndex(COLUMN_DOCTOR));
            @SuppressLint("Range") int idType = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") int durationMin = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));

            Service service = new Service(id, name, cost, doctor, idType, durationMin);
            servicesList.add(service);
        }

        cursor.close();
        db.close();

        return servicesList;
    }

    //Добавление питомца
    public long addPet(String namePet, String typePet, String genderPet, String dateOfBirthPet, int idHuman) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("Name", namePet);
        values.put("Type", typePet);
        values.put("Gender", genderPet);
        values.put("DateOfBirth", dateOfBirthPet);
        values.put("IdHuman", idHuman);

        long result = database.insert("Pet", null, values);
        database.close();

        return result;
    }

    @SuppressLint("Range")
    public String getHumanEmailById(int idHuman) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT Email FROM Human WHERE id = ?";
        String[] selectionArgs = {String.valueOf(idHuman)};
        Cursor cursor = database.rawQuery(query, selectionArgs);

        String email = null;

        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("Email"));
        }

        cursor.close();
        database.close();

        return email;
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

    public Human getHumanById(int id) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM Human WHERE id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        Human human = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String login = cursor.getString(cursor.getColumnIndex("Login"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("Password"));
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex("FullName"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("Email"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("Phone"));

            human = new Human(id, login, password, fullName, email, phone);
        }

        cursor.close();
        db.close();

        return human;
    }

    public boolean updateHuman(Human human) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Vet.db", MODE_PRIVATE, null);

        ContentValues values = new ContentValues();
        values.put("Login", human.getLogin());
        values.put("Password", human.getPassword());
        values.put("FullName", human.getFullName());
        values.put("Email", human.getEmail());
        values.put("Phone", human.getPhone());

        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(human.getId())};

        int rowsUpdated = db.update("Human", values, whereClause, whereArgs);

        db.close();

        return rowsUpdated > 0;
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
