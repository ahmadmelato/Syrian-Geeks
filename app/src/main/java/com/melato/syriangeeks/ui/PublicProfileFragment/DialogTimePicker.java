package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.DialogDatepickerBinding;
import com.melato.syriangeeks.databinding.DialogTimepickerBinding;

public class DialogTimePicker {

    private final Context context;
    private AlertDialog dialog;
    private DialogTimepickerBinding binding;// Replace with your actual ViewModel type

    public DialogTimePicker(Context context, EditText editText) {
        this.context = context;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_timepicker,
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
        traverseViewsAndSetEnglishFormat(binding.timePicker);
        binding.submitButton.setOnClickListener(v -> {
            String selectedTime = (binding.timePicker.getHour() > 9 ? binding.timePicker.getHour() : "0" + binding.timePicker.getHour()) + ":" + (binding.timePicker.getMinute() > 9 ? binding.timePicker.getMinute() : "0" + (binding.timePicker.getMinute())); // Format and display the date
            editText.setText(selectedTime);
            dialog.dismiss();
        });
        binding.cancelButton.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    private void traverseViewsAndSetEnglishFormat(View view) {
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
