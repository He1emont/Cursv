package com.example.cursv;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class JavaMailAPIHtml extends AsyncTask<Void,Void,Boolean> {

    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final String mEmail;
    private final String mSubject;
    private final String email;
    private final String password;
    private ProgressDialog mProgressDialog;
    private final StringBuilder htmlBody;
    private final DataSource fds;

    public JavaMailAPIHtml(Context mContext, String mEmail, String mSubject , StringBuilder htmlBody, String email, String password, DataSource fds) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.email = email;
        this.password = password;
        this.htmlBody = htmlBody;
        this.fds = fds;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext,"Отправка чека", "Пожалуйста подождите...",false,false);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        mProgressDialog.dismiss();

        if (success) {
            Toast.makeText(mContext, "Чек отправлен", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Произошла ошибка. Обратитесь к официанту", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session mSession = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });

        try {
            MimeMessage mm = new MimeMessage(mSession);
            mm.setFrom(new InternetAddress(email));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject(mSubject);

            mm.setContent(htmlBody.toString(), "text/html");

            // Добавление логотипа встроенным изображением (CID)
            Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(htmlBody.toString(), "text/html; charset=utf-8");

            MimeBodyPart logoPart = new MimeBodyPart();
            logoPart.setDataHandler(new DataHandler(fds));
            logoPart.setHeader("Content-ID", "<logo>");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(logoPart);

            mm.setContent(multipart);
            Transport.send(mm);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class ByteArrayDataSource implements DataSource {
        private final byte[] data;
        private final String type;

        public ByteArrayDataSource(byte[] data, String type) {
            this.data = data;
            this.type = type;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(data);
        }

        @Override
        public OutputStream getOutputStream() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getContentType() {
            return type;
        }

        @Override
        public String getName() {
            return "ByteArrayDataSource";
        }
    }
}
