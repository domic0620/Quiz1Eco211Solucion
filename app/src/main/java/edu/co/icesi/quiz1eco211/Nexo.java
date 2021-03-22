package edu.co.icesi.quiz1eco211;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Nexo extends AppCompatActivity {

    private Button nextBtn;
    private CheckBox nex1, nex2, nex3, nex4, nex5;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nexo);

        nextBtn = findViewById(R.id.nextBtn);
        nex1 = findViewById(R.id.nex1);
        nex2 = findViewById(R.id.nex2);
        nex3 = findViewById(R.id.nex3);
        nex4 = findViewById(R.id.nex4);
        nex5 = findViewById(R.id.nex5);

        validateCheck();

        name = getIntent().getExtras().getString("name");

        nextBtn.setOnClickListener(
                (v) -> {
                    nextForm();
                }
        );
    }

    public void nextForm() {

        Toast.makeText(this, "Risk Value: " + riskScore(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, Symptoms.class);
        i.putExtra("risk", riskScore());
        i.putExtra("name", name);
        startActivity(i);
        finish();

    }

    public int riskScore() {

        int value = 0;

        if (nex1.isChecked()) {
            value += 3;
        }

        if (nex2.isChecked()) {
            value += 3;
        }

        if (nex3.isChecked()) {
            value += 3;
        }

        if (nex4.isChecked()) {
            value += 3;
        }

        if (nex5.isChecked()) {

            value = 0;
        }

        return value;
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

                        if (nex5.isChecked()) {
                            runOnUiThread(
                                    () -> {
                                        nex1.setChecked(false);
                                        nex2.setChecked(false);
                                        nex3.setChecked(false);
                                        nex4.setChecked(false);
                                    }
                            );
                        }

                        if (nex1.isChecked() || nex2.isChecked() || nex3.isChecked() || nex4.isChecked() || nex5.isChecked()) {
                            runOnUiThread(
                                    () -> {
                                        nextBtn.setEnabled(true);
                                        nextBtn.setBackgroundColor(Color.parseColor("#534EE1"));
                                    }
                            );
                        } else {
                            runOnUiThread(
                                    () -> {
                                        nextBtn.setEnabled(false);
                                        nextBtn.setBackgroundColor(Color.parseColor("#ACACAC"));
                                        nextBtn.setTextColor(Color.parseColor("#FFFFFF"));
                                    }
                            );
                        }
                    }
                }
        ).start();
    }
}