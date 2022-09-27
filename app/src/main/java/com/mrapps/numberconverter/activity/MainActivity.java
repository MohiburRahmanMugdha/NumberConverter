package com.mrapps.numberconverter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mrapps.numberconverter.R;
import com.mrapps.numberconverter.databinding.ActivityMainBinding;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // setup spinner

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                switch (item) {
                    case "Binary":
                        binding.edittext.setHint("Enter Binary ");
                        hideBinary();
                        String binary = binding.edittext.getText().toString();
                        if (!binary.isEmpty()) {
                            convertToBase(binary, 2);
                            if (!binary.matches("[0-1.]+")) {
                                binding.error.setText("Invalid Binary");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }
                        }

                        break;
                    case "Octal":
                        binding.edittext.setHint("Enter Octal ");
                        hideOctal();
                        String octal = binding.edittext.getText().toString();
                        if (!octal.isEmpty()) {
                            convertToBase(octal, 8);

                            if (!octal.matches("[0-7.]+")) {
                                binding.error.setText("Invalid Octal");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }
                        }


                        break;
                    case "Decimal":
                        binding.edittext.setHint("Enter Decimal ");
                        hideDecimal();
                        String decimal = binding.edittext.getText().toString();
                        if (!decimal.isEmpty()) {
                            convertToBase(decimal, 10);
                            if (!decimal.matches("[0-9.]+")) {
                                binding.error.setText("Invalid Decimal");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }
                        }


                        break;
                    case "Hexadecimal":
                        binding.edittext.setHint("Enter Hexadecimal ");
                        hideHexadecimal();
                        String hexadecimal = binding.edittext.getText().toString();
                        if (!hexadecimal.isEmpty()) {
                            convertToBase(hexadecimal, 16);
                            if (!hexadecimal.matches("[0-9A-Fa-f.]+")) {
                                binding.error.setText("Invalid Hexadecimal");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }
                        }


                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.copyBin.setOnClickListener(v -> {
            String bin = binding.editbinary.getText().toString();
            if (bin.isEmpty()) {
                Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show();
            } else {
                copy(bin);
            }
        });

        binding.copyOct.setOnClickListener(v -> {
            String oct = binding.editoctal.getText().toString();
            if (oct.isEmpty()) {
                Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show();
            } else {
                copy(oct);
            }
        });

        binding.copyDec.setOnClickListener(v -> {
            String dec = binding.editdecimal.getText().toString();
            if (dec.isEmpty()) {
                Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show();
            } else {
                copy(dec);
            }
        });

        binding.copyHex.setOnClickListener(v -> {
            String hex = binding.edithexa.getText().toString();
            if (hex.isEmpty()) {
                Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show();
            } else {
                copy(hex);
            }
        });


        binding.edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String input = binding.edittext.getText().toString();
                if (input.length() == 0) {
                    binding.editbinary.setText("");
                    binding.editoctal.setText("");
                    binding.editdecimal.setText("");
                    binding.edithexa.setText("");
                } else {
                    String item = binding.spinner.getSelectedItem().toString();
                    int base;
                    switch (item) {
                        case "Octal":
                            base = 8;

                            if (!input.matches("[0-7.]+")) {
                                binding.error.setText("Invalid Octal");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }

                            break;
                        case "Decimal":
                            base = 10;

                            if (!input.matches("[0-9.]+")) {
                                binding.error.setText("Invalid Decimal");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }

                            break;
                        case "Hexadecimal":
                            base = 16;

                            if (!input.matches("[0-9A-Fa-f.]+")) {
                                binding.error.setText("Invalid Hexadecimal");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case "Binary":
                        default:
                            base = 2;

                            if (!input.matches("[0-1.]+")) {
                                binding.error.setText("Invalid Binary");
                                binding.error.setVisibility(View.VISIBLE);
                            } else {
                                binding.error.setVisibility(View.INVISIBLE);
                            }

                    }
                    convertToBase(input, base);
                }
            }

        });


    }

    private void convertToBase(String input, int radix) {
        try {
            input = input.toUpperCase();
            int dots = 0;
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '.') {
                    dots++;
                }
            }

            if (dots > 1) throw new Exception();

            String[] parts = input.split("\\.");
            if (parts.length == 0 || parts.length > 2) throw new Exception();

            BigInteger integerResult = null;
            if (parts.length >= 1) {
                String integerPart = null;
                integerPart = parts[0];
                // Convert from base radix to base 10.
                BigInteger base = BigInteger.valueOf(radix);
                integerResult = new BigInteger("0");
                for (int i = 0; i < integerPart.length(); i++) {
                    char c = integerPart.charAt(i); // the
                    int u = c; // integer representation of the character in ascii
                    if ((u >= '0' && u <= '9') || (u >= 'A' && u <= 'Z')) { // if the character is a number or alphabetical letter
                        if ((u >= '0' && u <= '9')) u = u - '0';
                        else
                            u = (u - 'A') + 10; // change the integer representation to the correct value
                        if (u >= radix) throw new Exception();
                        BigInteger digit = new BigInteger(String.valueOf(u));
                        BigInteger value = digit.multiply(new BigInteger(String.valueOf(base.pow(integerPart.length() - (i + 1)))));
                        integerResult = integerResult.add(value);
                    } else {
                        throw new Exception();
                    }
                }
            }
            BigDecimal fractionResult = null;
            if (parts.length == 2) {
                String fractionPart = parts[1];
                BigDecimal base = BigDecimal.valueOf(radix);
                fractionResult = new BigDecimal("0");
                for (int i = 0; i < fractionPart.length(); i++) {
                    char c = fractionPart.charAt(i); // the
                    int u = c; // integer representation of the character in ascii
                    if ((u >= '0' && u <= '9') || (u >= 'A' && u <= 'Z')) { // if the character is a number or alphabetical letter
                        if ((u >= '0' && u <= '9')) u = u - '0';
                        else
                            u = (u - 'A') + 10; // change the integer representation to the correct value
                        if (u >= radix) throw new Exception();
                        BigDecimal digit = new BigDecimal(String.valueOf(u));
                        BigDecimal value = digit.multiply(new BigDecimal(String.valueOf(base.pow(-i - 1, MathContext.DECIMAL64))));
                        fractionResult = fractionResult.add(value);
                    } else {
                        throw new Exception();
                    }
                }
            }

            if (integerResult != null && fractionResult == null) {
                binding.editbinary.setText(convertIntegerToBase(integerResult, 2));
                binding.editoctal.setText(convertIntegerToBase(integerResult, 8));
                binding.editdecimal.setText(integerResult.toString());
                binding.edithexa.setText(convertIntegerToBase(integerResult, 16));

            }

            if (integerResult != null && fractionResult != null) {
                binding.editbinary.setText(convertIntegerToBase(integerResult, 2) + "." + convertFractionToBase(fractionResult, 2));
                binding.editoctal.setText(convertIntegerToBase(integerResult, 8) + "." + convertFractionToBase(fractionResult, 8));
                binding.editdecimal.setText(integerResult + "." + convertFractionToBase(fractionResult, 10));
                binding.edithexa.setText(convertIntegerToBase(integerResult, 16) + "." + convertFractionToBase(fractionResult, 16));

            }

        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private String convertFractionToBase(BigDecimal fraction, int radix) {
        String result = "";
        BigDecimal base = new BigDecimal(radix);
        for (int i = 0; i < 10; i++) {
            fraction = fraction.multiply(base);
            BigInteger x = fraction.toBigInteger();
            char c = (char) ('0' + x.intValue());
            if (x.compareTo(BigInteger.valueOf(9)) == 1) {
                c = (char) ('A' + (x.intValue() - 10));
            }
            result = result + c;
            fraction = fraction.subtract(BigDecimal.valueOf(x.intValue()));
            if (fraction.compareTo(BigDecimal.ZERO) == 0) break;
        }
        return result;
    }

    private String convertIntegerToBase(BigInteger integer, int radix) throws Throwable {
        String result = "";
        while (integer.compareTo(BigInteger.ZERO) != 0) {
            BigInteger[] division = integer.divideAndRemainder(BigInteger.valueOf(radix));
            int remainder = division[1].intValue();
            char c = (char) ('0' + remainder); // get the character using the remainder
            if (remainder > 9) { // if the character
                int u = remainder - 10; // find the index of the alphabetical letter
                c = (char) ('A' + u); // update the character
            }
            result = c + result; // append to beginning of result
            integer = division[0]; // use the new decimal value.
        }
        if (result.isEmpty()) result = "0";
        return result;
    }


    // copy to clipboard
    private void copy(String number) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", number);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
    }

    private void hideBinary() {
        binding.binaryLayout.setVisibility(View.GONE);
        binding.octalLayout.setVisibility(View.VISIBLE);
        binding.decimalLayout.setVisibility(View.VISIBLE);
        binding.hexadecimalLayout.setVisibility(View.VISIBLE);

        binding.editbinary.setKeyListener(DigitsKeyListener.getInstance("01."));

    }

    private void hideOctal() {
        binding.binaryLayout.setVisibility(View.VISIBLE);
        binding.octalLayout.setVisibility(View.GONE);
        binding.decimalLayout.setVisibility(View.VISIBLE);
        binding.hexadecimalLayout.setVisibility(View.VISIBLE);

        binding.editoctal.setKeyListener(DigitsKeyListener.getInstance("01234567."));
    }

    private void hideDecimal() {
        binding.binaryLayout.setVisibility(View.VISIBLE);
        binding.octalLayout.setVisibility(View.VISIBLE);
        binding.decimalLayout.setVisibility(View.GONE);
        binding.hexadecimalLayout.setVisibility(View.VISIBLE);

        binding.editdecimal.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
    }

    private void hideHexadecimal() {
        binding.binaryLayout.setVisibility(View.VISIBLE);
        binding.octalLayout.setVisibility(View.VISIBLE);
        binding.decimalLayout.setVisibility(View.VISIBLE);
        binding.hexadecimalLayout.setVisibility(View.GONE);

        binding.edithexa.setKeyListener(DigitsKeyListener.getInstance("0123456789ABCDEF."));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}