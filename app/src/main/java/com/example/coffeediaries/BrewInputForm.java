package com.example.coffeediaries;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrewInputForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrewInputForm extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrewInputForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrewInputForm.
     */
    // TODO: Rename and change types and number of parameters
    public static BrewInputForm newInstance(String param1, String param2) {
        BrewInputForm fragment = new BrewInputForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brew_input_form, container, false);

        // Auto-fills the date field with current date
        setCurrentDate(view);

        // Setting up the touch listener so we can detect specific touch events
        setupTouchListener(view);

        // Calls a method to clear all of the form fields upon clicking the clear button
        view.findViewById(R.id.btnClearBrewInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all input fields
                clearAllFields(view);
            }
        });
        
        // Sets up the submit button so it navigates back to the previous screen after submission. 
        // We still need to add the logic for saving the data to the Room database.
        view.findViewById(R.id.btnSubmitBrewInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
        
        return view;
    }

    // Function to set the date field to  in MM/dd/yyyy format
    private void setCurrentDate(View view) {
        TextInputEditText brewDateInput = view.findViewById(R.id.brewDateInput);
        if (brewDateInput != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            String currentDate = dateFormat.format(new Date());
            brewDateInput.setText(currentDate);
        }
    }

    // Actual function to handle clearing all of the form fields
    private void clearAllFields(View view) {
        TextInputEditText brewDateInput = view.findViewById(R.id.brewDateInput);
        TextInputEditText brewMethodInput = view.findViewById(R.id.brewMethodInput);
        TextInputEditText brewCoffeeNameInput = view.findViewById(R.id.brewCoffeeNameInput);
        TextInputEditText brewTimeInput = view.findViewById(R.id.brewTimeInput);
        TextInputEditText brewGramsCoffeeInput = view.findViewById(R.id.brewGramsCoffeeInput);
        TextInputEditText brewCaloriesInput = view.findViewById(R.id.brewCaloriesInput);
        TextInputEditText brewRatingInput = view.findViewById(R.id.brewRatingInput);
        TextInputEditText brewCommentInput = view.findViewById(R.id.brewCommentInput);

        // Reset date to current date instead of clearing it
        if (brewDateInput != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            String currentDate = dateFormat.format(new Date());
            brewDateInput.setText(currentDate);
        }
        // Clear all other fields
        if (brewMethodInput != null) brewMethodInput.setText("");
        if (brewCoffeeNameInput != null) brewCoffeeNameInput.setText("");
        if (brewTimeInput != null) brewTimeInput.setText("");
        if (brewGramsCoffeeInput != null) brewGramsCoffeeInput.setText("");
        if (brewCaloriesInput != null) brewCaloriesInput.setText("");
        if (brewRatingInput != null) brewRatingInput.setText("");
        if (brewCommentInput != null) brewCommentInput.setText("");
    }

    // Function to set up the touch listener for the fragment view
    // Calls the clearFocusAndHideKeyboard method when a touch event occurs
    private void setupTouchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Clears focus from all input fields and hide keyboard
                    clearFocusAndHideKeyboard(view);
                }
                return false;
            }
        });
    }

    // Actual method to clear the focus from an input field and hide the keyboard
    private void clearFocusAndHideKeyboard(View view) {
        // Clears focus
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }

        // Hides the keyboard
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }
}