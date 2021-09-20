package com.example.myreadwritefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editIsi;
    EditText editJudul;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnSave = (Button) findViewById(R.id.btnSave);
        editIsi = (EditText) findViewById(R.id.editIsi);
        editJudul = (EditText) findViewById(R.id.editJudul);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnNew:
                newFile();
                break;
            case R.id.btnOpen:
                openFile();
                break;
            case R.id.btnSave:
                saveFile();
                break;
        }
    }

    public void newFile() {
        editJudul.setText("");
        editIsi.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }
    private void loadData(String title) {
        String text = Helper.readFromFile(this, title);
        editJudul.setText(title);
        editIsi.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }
    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file Anda yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (editJudul.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editJudul.getText().toString();
            String text = editIsi.getText().toString();
            Helper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editJudul.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }
}