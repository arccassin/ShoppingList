package com.example.shoppinglist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InstrumentsActivity extends AppCompatActivity {

    private static final String PREFERENCES = "PREFERENCES_INSTRUMENTS";
    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    int choisedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruments);

        arrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        for (int i = 0; i < preferences.getInt("length", 0); i++) {
            arrayList.add(preferences.getString(String.valueOf(i), ""));
        }
        listView = findViewById(R.id.list_view_instruments);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.my_simple_list_single_choice, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choisedItemPosition = position;
            }
        });
    }

    public void onClickButtonAdd(View view) {
        EditText editText = findViewById(R.id.edit_text_instruments);
        String item = editText.getText().toString();
        if (item.equals("")) {
            Toast toast = Toast.makeText(this, "Добавьте покупку!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            arrayList.add(item);
            arrayAdapter.notifyDataSetChanged();
            editText.setText("");
        }
    }

    public void onClickButtonRemove(View view) {
        if (arrayList.size() == 0) {
            Toast toast = Toast.makeText(this, "Список уже пуст!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            arrayList.remove(choisedItemPosition);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onSaveData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveData();
    }

    private void onSaveData(){
        String[] items = arrayList.toArray(new String[0]);
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < items.length; i++) {
            editor.putString(String.valueOf(i), items[i]);
        }
        editor.putInt("length", items.length);
        editor.apply();
    }
}
