package edu.co.icesi.quiz1eco211;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecord extends AppCompatActivity {

    private EditText nameInput, idInput;
    private Button startRecordBtn;
    private String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        nameInput = findViewById(R.id.nameInput);
        idInput = findViewById(R.id.idInput);
        startRecordBtn = findViewById(R.id.startRecordBtn);

        startRecordBtn.setOnClickListener(

                (v) -> {

                    id = idInput.getText().toString();
                    name = nameInput.getText().toString();

                    if (validateInputsCompletion() && validateIdRecord(id)) {
                        startForm();
                    }

                }
        );
    }

    public void startForm() {

        Toast.makeText(this, "Id " + id, Toast.LENGTH_LONG).show();
        //  String idRecords = getSharedPreferences("records", MODE_PRIVATE).getString("idRecords", "");
        //   Log.e("", idRecords);
        Log.d("", id);
        //  validateIdRecord(id);

        Intent i = new Intent(this, Nexo.class);
        i.putExtra("name", name);
        i.putExtra("id", id);
        startActivity(i);
        finish();

    }

    public boolean validateInputsCompletion() {

        boolean validate;

        if (nameInput.getText().toString().equals("") || idInput.getText().toString().equals("")) {
            Toast.makeText(this, "Completa la información para continuar", Toast.LENGTH_LONG).show();
            validate = false;
        } else {
            validate = true;
        }

        return validate;
    }

    public boolean validateIdRecord(String s) {

        boolean validate;

        SharedPreferences preferences = getSharedPreferences("records", MODE_PRIVATE);
        String idRecords = preferences.getString("idRecords", "");

        if (idRecords.contains(s)) {
            Toast.makeText(this, "Ya existe un registro con este Id", Toast.LENGTH_LONG).show();
            validate = false;
        } else {

            validate = true;
            String idRecord = s + "\n";
            preferences.edit().putString("idRecords", idRecords + idRecord).apply();

        }

        return validate;

    }
}