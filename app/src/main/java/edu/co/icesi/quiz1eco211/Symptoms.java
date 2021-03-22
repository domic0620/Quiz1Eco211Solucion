package edu.co.icesi.quiz1eco211;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Symptoms extends AppCompatActivity {

    private Button finishBtn;
    private CheckBox symptom1, symptom2, symptom3, symptom4, symptom5, symptom6, none;
    private String name;
    private int risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        finishBtn = findViewById(R.id.finishBtn);
        symptom1 = findViewById(R.id.symptom1);
        symptom2 = findViewById(R.id.symptom2);
        symptom3 = findViewById(R.id.symptom3);
        symptom4 = findViewById(R.id.symptom4);
        symptom5 = findViewById(R.id.symptom5);
        symptom6 = findViewById(R.id.symptom6);
        none = findViewById(R.id.none);

        validateCheck();

        name = getIntent().getExtras().getString("name");
        risk = getIntent().getExtras().getInt("risk");

        finishBtn.setOnClickListener(
                (v) -> {
                    finishForm();
                }
        );
    }

    public void finishForm() {

        int finalRiskScore = riskScore(risk);
        saveNewRecord(name, finalRiskScore);

        Toast.makeText(this, "Risk Value: " + riskScore(risk), Toast.LENGTH_LONG).show();
        finish();
    }

    public void saveNewRecord(String name, int risk) {

        String newRecordInfo = name + " / " + risk + "\n";

        SharedPreferences preferences = getSharedPreferences("records", MODE_PRIVATE);
        String allRecords = preferences.getString("allRecords", "");
        preferences.edit().putString("allRecords", allRecords + newRecordInfo).apply();
    }

    public int riskScore(int i) {

        int value = 0;

        if (symptom1.isChecked()) {
            value += 4;
        }

        if (symptom2.isChecked()) {
            value += 4;
        }

        if (symptom3.isChecked()) {
            value += 4;
        }

        if (symptom4.isChecked()) {
            value += 4;
        }

        if (symptom5.isChecked()) {
            value += 4;
        }

        if (symptom6.isChecked()) {
            value += 4;
        }

        if (none.isChecked()) {
            value = 0;
        }

        return i + value;
    }

    public void validateCheck() {

        new Thread(
                () -> {
                    while (true) {

                        try {
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (none.isChecked()) {
                            runOnUiThread(
                                    () -> {
                                        symptom1.setChecked(false);
                                        symptom2.setChecked(false);
                                        symptom3.setChecked(false);
                                        symptom4.setChecked(false);
                                        symptom5.setChecked(false);
                                        symptom6.setChecked(false);
                                    }
                            );
                        }

                        if (symptom1.isChecked() || symptom2.isChecked() || symptom3.isChecked() || symptom4.isChecked() || symptom5.isChecked() || symptom6.isChecked() || none.isChecked()) {
                            runOnUiThread(
                                    () -> {
                                        finishBtn.setEnabled(true);
                                        finishBtn.setBackgroundColor(Color.parseColor("#534EE1"));
                                    }
                            );
                        } else {
                            runOnUiThread(
                                    () -> {
                                        finishBtn.setEnabled(false);
                                        finishBtn.setBackgroundColor(Color.parseColor("#ACACAC"));
                                        finishBtn.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                            );
                        }
                    }
                }
        ).start();
    }
}