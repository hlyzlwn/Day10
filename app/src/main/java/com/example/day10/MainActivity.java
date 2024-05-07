package com.example.day10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    RadioGroup radiogroup1, radiogroup2;
    RadioButton rbpajero, rbalphard, rbinova, rbbrio, rbinsadecity, rboutsidecity;
    TextInputEditText dayofrent; // Ganti totalday menjadi dayofrent
    Button btnrent;
    double cartype, city, totalAmount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radiogroup1 = findViewById(R.id.radiogroup1);
        rbpajero = findViewById(R.id.rbpajero);
        rbalphard = findViewById(R.id.rbalphard);
        rbinova = findViewById(R.id.rbinova);
        rbbrio = findViewById(R.id.rbbrio);
        radiogroup2 = findViewById(R.id.radiogroup2);
        rbinsadecity = findViewById(R.id.rbinsadecity);
        rboutsidecity = findViewById(R.id.rboutsidecity);
        dayofrent = findViewById(R.id.ettotalday); // Ganti totalday menjadi dayofrent
        btnrent = findViewById(R.id.btnrent);

        btnrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAmount();
            }
        });
    }

    private void calculateAmount() {
        int selectedCarId = radiogroup1.getCheckedRadioButtonId();
        int selectedCityId = radiogroup2.getCheckedRadioButtonId();
        String totalDay = dayofrent.getText().toString(); // Ganti totalday menjadi dayofrent

        if (selectedCarId == -1 || selectedCityId == -1 || totalDay.isEmpty()) {
            Toast.makeText(this, "Please select car type, city, and enter total days.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedCarRadioButton = findViewById(selectedCarId);
        RadioButton selectedCityRadioButton = findViewById(selectedCityId);

        String carType = selectedCarRadioButton.getText().toString();
        String cityType = selectedCityRadioButton.getText().toString();
        int totalDays = Integer.parseInt(totalDay);

        switch (carType) {
            case "Pajero":
                cartype = 2400000;
                break;
            case "Alphard":
                cartype = 1550000;
                break;
            case "Inova":
                cartype = 850000;
                break;
            case "Brio":
                cartype = 550000;
                break;
        }

        switch (cityType) {
            case "Inside City": // Perbaikan pada penulisan
                city = 135000;
                break;
            case "Outside City":
                city = 250000;
                break;
        }

        totalAmount = (cartype + city) * totalDays;

        // Check for discount
        double discount = 0;
        if (totalAmount > 10000000) {
            discount = totalAmount * 0.07; // 7% discount
        } else if (totalAmount > 5000000) {
            discount = totalAmount * 0.05; // 5% discount
        }

        double totalPayment = totalAmount - discount;

        tampilkanBon(cartype, city, totalDays, discount, totalPayment); // Ganti totalday menjadi totalDays
    }

    private void tampilkanBon(double cartype, double city, int totalDays, double discount, double totalPayment) { // Ganti totalday menjadi totalDays
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String carTypePriceFormatted = "Rp " + decimalFormat.format(cartype);
        String cityPriceFormatted = " " + decimalFormat.format(city);
        String totalDaysFormatted = String.valueOf(totalDays);
        String discountFormatted = "Rp " + decimalFormat.format(discount);
        String totalPaymentFormatted = "Rp " + decimalFormat.format(totalPayment);

        String bonText =
                getString(R.string.car_type) + carTypePriceFormatted + "\n\n"
                        + getString(R.string.city) + cityPriceFormatted + "\n\n"
                        + getString(R.string.total_day) + totalDaysFormatted + "\n\n" // Ganti total_days menjadi total_payment
                        + getString(R.string.rent) + discountFormatted + "\n\n" // Ganti rent menjadi discount
                        + getString(R.string.total_day) + totalPaymentFormatted; // Ganti totaldays menjadi total_payment

        Intent intent = new Intent(MainActivity.this, BonActivity.class);
        intent.putExtra("BonText", bonText);
        startActivity(intent);
    }

}