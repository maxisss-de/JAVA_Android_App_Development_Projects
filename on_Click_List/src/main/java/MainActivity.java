//------------------------------------------------------------------------
// Название программы: "on Click List" - "в списке кликов"
// Задание: Сделать кнопку "НЕТ", чтобы на экране показывалось уведомление неправильно
//------------------------------------------------------------------------

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Button trueBtn;  /* кнопка ДА */
    private Button falseBtn; /* кнопка НЕТ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);

        trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, R.string.correct_toast,Toast.LENGTH_SHORT).show();
            }
        });
        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
