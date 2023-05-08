package sg.edu.rp.c346.id21038060.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button btnCalculate;
    Button btnReset;
    RadioGroup rgPaymentMethod;
    RadioButton rbtnCash;
    RadioButton rbtnPayNow;
    TextView tvTotalBill;
    TextView tvTotalBillSplit;
    EditText etnAmount;
    EditText etnPax;
    EditText etnDiscount;
    ToggleButton tbtnService;
    ToggleButton tbtnGst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        rbtnCash = findViewById(R.id.rbtnCash);
        rbtnPayNow = findViewById(R.id.rbtnPayNow);
        tvTotalBill = findViewById(R.id.tvTotalBill);
        tvTotalBillSplit = findViewById(R.id.tvTotalBillSplit);
        etnAmount = findViewById(R.id.etnAmount);
        etnPax = findViewById(R.id.etnPax);
        etnDiscount = findViewById(R.id.etnDiscount);
        tbtnService = findViewById(R.id.tbtnService);
        tbtnGst = findViewById(R.id.tbtnGst);

        //On Click Event - Calculate
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                double totalBill;
                double totalBillSplit;

                //Calculate Initial Total Bill
                totalBill = Double.parseDouble(String.valueOf(etnAmount.getText()));

                //Apply GST and Service Charge Multipliers to Total Bill (if applicable)
                if (tbtnService.isChecked()){
                    totalBill = totalBill * 1.1;
                }
                if (tbtnGst.isChecked()) {
                    totalBill = totalBill * 1.07;
                }

                //Apply Discount Multipliers to Total Bill (if applicable)
                if (!TextUtils.isEmpty(etnDiscount.getText().toString()))
                {
                    totalBill = totalBill * ((100-Double.parseDouble(String.valueOf(etnDiscount.getText())))/100);
                }

                //Calculate Split Bill
                totalBillSplit = totalBill/Integer.parseInt(String.valueOf(etnPax.getText()));

                //Print Total Bill
                tvTotalBill.setText(String.format("Total Bill: $%.2f", totalBill));

                //Print Split Bill
                int checkedRadioId = rgPaymentMethod.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.rbtnCash){
                    //Using Cash
                    tvTotalBillSplit.setText(String.format("Each Pays: $%.2f in cash", totalBillSplit));
                }
                else {
                    //Using PayNow
                    tvTotalBillSplit.setText(String.format("Each Pays: $%.2f in cash via PayNow to 9123 4567", totalBillSplit));
                }
            }
        });

        //On Click Event - Reset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etnAmount.setText("");
                etnPax.setText("");
                tbtnService.setChecked(false);
                tbtnGst.setChecked(false);
                etnDiscount.setText("");
                rbtnCash.setChecked(false);
                rbtnPayNow.setChecked(false);
                tvTotalBill.setText("");
                tvTotalBillSplit.setText("");
            }
        });
    }
}