package edu.co.icesi.quiz1eco211;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView recordsTextView;
    private Button addBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordsTextView = findViewById(R.id.recordsTextView);
        deleteBtn = findViewById(R.id.deleteBtn);
        addBtn = findViewById(R.id.addBtn);

        getRecords();

        addBtn.setOnClickListener(
                (v) -> {
                    Intent i = new Intent(this, AddRecord.class);
                    startActivity(i);

                }
        );

        deleteBtn.setOnClickListener(
                (v) -> {
                    deleteRecords();
                }
        );
    }

    public void deleteRecords() {

        SharedPreferences preferences = getSharedPreferences("records", MODE_PRIVATE);
        String allRecords = preferences.getString("allRecords", "");

        if (allRecords == "") {
            Toast.makeText(this, "La lista de registros está vacía", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(this, "Registros eliminados", Toast.LENGTH_LONG).show();
        }

    }

    public void getRecords() {

        new Thread(
                () -> {
                    while (true) {

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        String records = getSharedPreferences("records", MODE_PRIVATE).getString("allRecords", "No hay ningún registro aún");

                        runOnUiThread(
                                () -> {
                                    recordsTextView.setText(records);
                                }
                        );
                    }
                }

        ).start();
    }

    public void onResume() {

        super.onResume();
        getRecords();

    }

}