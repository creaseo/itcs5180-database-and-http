package edu.charlotte.httppractice;

import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // createContact("test1", "test1@email.com", "111-222-3333", "HOME");
        getContacts();
        String name = "b";
        String type = "CELL";

        // First method to building get URL
        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/contacts/search").newBuilder()
                .addQueryParameter("name", name)
                .addQueryParameter("type", type)
                .build();


        // Second method to building url
        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url1 = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("contacts")
                .addPathSegment("search")
                .addQueryParameter("name", name)
                .addQueryParameter("type", type)
                .build();

        // This is how you call (you need everything all the way to line 81)
        Request request = new Request.Builder()
                .url(url1)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("demo", "on Response: " + body);
                }
            }
        });
    }

    // POST
    void createContact(String name, String email, String phone, String type) {

        FormBody fb = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("type", type)
                .build();

        HttpUrl.Builder builder = new HttpUrl.Builder();
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/create")
                .post(fb)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("demo", "on Response: " + body);
                }
            }
        });
    }
    // Example of getting result from callback
    void getContacts() {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();

                    final String[] contacts = body.split("\n");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tvc = findViewById(R.id.textViewCount);
                            tvc.setText(contacts.length + " Contacts");
                        }
                    });
                    Log.d("demo", "on Response: " + body);
                }
            }
        });
    }
}