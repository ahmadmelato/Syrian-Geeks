package com.melato.syriangeeks.ui.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogAddSkillBinding;
import com.melato.syriangeeks.databinding.DialogDatepickerBinding;
import com.melato.syriangeeks.ui.MainViewModel;

public class DialogDatePicker {

    private final Context context;
    private AlertDialog dialog;
    private DialogDatepickerBinding binding;// Replace with your actual ViewModel type

    public DialogDatePicker(Context context, EditText editText) {
        this.context = context;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_datepicker,
                null,
                true
        );

        dialog = new AlertDialog.Builder(context)
                .setView(binding.getRoot())
                .setCancelable(true)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        traverseViewsAndSetEnglishFormat(binding.datePicker);
        binding.submitButton.setOnClickListener(v -> {
            String selectedDate = binding.datePicker.getYear() + "-" + (binding.datePicker.getMonth()+1 > 9 ? binding.datePicker.getMonth()+1 : "0" + (binding.datePicker.getMonth()+1)) + "-" + (binding.datePicker.getDayOfMonth() > 9 ? binding.datePicker.getDayOfMonth() : "0" + binding.datePicker.getDayOfMonth()); // Format and display the date
            editText.setText(selectedDate);
            dialog.dismiss();
        });
        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    private void traverseViewsAndSetEnglishFormat(android.view.View view) {
        if (view instanceof android.widget.TextView) {
            android.widget.TextView textView = (android.widget.TextView) view;
            textView.setText(formatToEnglishNumbers(textView.getText().toString()));
        } else if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                traverseViewsAndSetEnglishFormat(group.getChildAt(i));
            }
        }
    }

    private String formatToEnglishNumbers(String input) {
        if (input == null || input.isEmpty()) return input;

        // Convert to English digits
        char[] arabicDigits = new char[]{'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        char[] englishDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            boolean isReplaced = false;
            for (int i = 0; i < arabicDigits.length; i++) {
                if (ch == arabicDigits[i]) {
                    result.append(englishDigits[i]);
                    isReplaced = true;
                    break;
                }
            }
            if (!isReplaced) {
                result.append(ch); // Add other non-numeric characters as-is
            }
        }
        return result.toString();
    }

}
