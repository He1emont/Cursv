package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.JavaMailAPIHtml;
import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.Preference;
import com.example.cursv.R;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import javax.activation.DataSource;

public class CustomerServiceInfoActivity extends DatabaseUtils {

    private ImageButton iBtn_back;
    private Button btn_send_email;
    private TextView tv_id_service, tv_service_name,
            tv_service_duration, tv_service_doc,
            tv_signing_date_time, tv_signing_address,
            tv_service_cost;
    private SigningUpForService signing;
    private Service service;
    private int humanId;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_info);
        Intent intent = getIntent();
        if (intent != null) {
            signing = (SigningUpForService) intent.getSerializableExtra("signing");
        }
        initViews();
        initData();
        initListeners();
    }

    private void initViews(){
        iBtn_back = findViewById(R.id.iBtn_back);
        tv_id_service = findViewById(R.id.tv_id_service);
        tv_service_name = findViewById(R.id.tv_service_name);
        tv_service_duration = findViewById(R.id.tv_service_duration);
        tv_service_doc = findViewById(R.id.tv_service_doc);
        tv_signing_date_time = findViewById(R.id.tv_signing_date_time);
        tv_signing_address = findViewById(R.id.tv_signing_address);
        tv_service_cost = findViewById(R.id.tv_service_cost);
        btn_send_email = findViewById(R.id.btn_send_email);
    }

    private void initData(){
        humanId = Preference.getIntIdHuman("humanId", CustomerServiceInfoActivity.this);
        if(signing==null){
            return;
        }
        tv_id_service.setText(String.valueOf(signing.getId()));
        tv_signing_date_time.setText(signing.getDate().format(formatter));
        tv_signing_address.setText(signing.getAddress());
        service = getServiceById(signing.getIdService());
        if(service!=null){
            tv_service_name.setText(service.getNameService());
            tv_service_duration.setText(String.valueOf(service.getDurationMin()));
            tv_service_doc.setText(getVeterinarianById(service.getIdDoctor()).getFio());
            tv_service_cost.setText(String.valueOf(service.getCostService()));
        }
    }
    private void initListeners(){
        iBtn_back.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerServiceInfoActivity.this, CustomerServicesActivity.class);
            startActivity(intent);
        });
        btn_send_email.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        String mSubject = "Запись в VetClinic № " + signing.getId();
        String mEmail = getHumanEmailById(humanId);
        String gmail = "wendox00321@gmail.com";
        String password = "kxtceprldiidiwoz";
        StringBuilder htmlBody = generateHtmlBody(String.valueOf(signing.getId()),
                service.getNameService(), signing.getDate().format(formatter),
                String.valueOf(service.getCostService()), String.valueOf(service.getDurationMin()),
                getVeterinarianById(service.getIdDoctor()).getFio(), signing.getAddress());

        ByteArrayOutputStream logoOutputStream = new ByteArrayOutputStream();
        Bitmap bitmapLogo = BitmapFactory.decodeResource(CustomerServiceInfoActivity.this.getResources(), R.mipmap.ic_launcher_foreground);
        bitmapLogo.compress(Bitmap.CompressFormat.PNG, 100, logoOutputStream);
        byte[] logoBytes = logoOutputStream.toByteArray();
        DataSource fds = new JavaMailAPIHtml.ByteArrayDataSource(logoBytes, "image/png");

        JavaMailAPIHtml javaMailAPIHtml = new JavaMailAPIHtml(CustomerServiceInfoActivity.this, mEmail, mSubject, htmlBody, gmail, password, fds);
        javaMailAPIHtml.execute();
    }

    private StringBuilder generateHtmlBody(String id, String serviceName, String dateTime, String cost, String duration, String doc, String address){
        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("<html><body>");
        htmlBody.append("<table style=\"border: 1px solid gray; padding: 10px;\">");
        htmlBody.append("<tr>");
        htmlBody.append("<td colspan=\"3\" style=\"text-align: center;\">");
        htmlBody.append("<img src='cid:logo' alt='logo' width=\"120\" height=\"130\"/>"); // Добавление логотипа
        htmlBody.append("</td>");
        htmlBody.append("</tr>");
        htmlBody.append("<tr><td><p>Запись № ").append(id).append("</p></td></tr>");
        htmlBody.append("<tr><td colspan=\"3\"><hr color=\"#ffc24a\" size=\"2\" /></td></tr>");
        htmlBody.append("<tr><td><p>Услуга: ").append(serviceName).append("</p></td></tr>");
        htmlBody.append("<tr><td><p>Длительность: ").append(duration).append(" мин.").append("</p></td></tr>");
        htmlBody.append("<tr><td><p>Врач: ").append(doc).append("</p></td></tr>");
        htmlBody.append("<tr><td><p>Запись на: ").append(dateTime).append("</p></td></tr>");
        htmlBody.append("<tr><td><p>Адрес: ").append(address).append("</p></td></tr>");
        htmlBody.append("<tr><td><p>Стоимость: ").append(cost).append(" руб.").append("</p></td></tr>");
        return htmlBody;
    }

}