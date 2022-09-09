package com.example.propelrrhandsonexam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etFullName, etEmailAddress, etMobileNumber, etDateOfBirth, etAge;
    Spinner spGender;
    TextView tvFullNameError, tvEmailAddressError, tvMobileNumberError, tvDateOfBirthError,
            tvAgeError, tvGenderError;
    Button btnSubmit, btnResponses;
    ImageButton btnPickDate;

    Context context;

    LoadingDialog loadingDialog;
    MessageDialog messageDialog;
    ResponseDialog responseDialog;

    String fullName, emailAddress, mobileNumber, dateOfBirth, gender;
    int age = 0;

    Calendar calendar = Calendar.getInstance();

    Repository repository;

    List<Response> responseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnResponses = findViewById(R.id.btnResponses);

        etFullName = findViewById(R.id.etFullName);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etAge = findViewById(R.id.etAge);
        spGender = findViewById(R.id.spGender);

        tvFullNameError = findViewById(R.id.tvFullNameError);
        tvEmailAddressError = findViewById(R.id.tvEmailAddressError);
        tvMobileNumberError = findViewById(R.id.tvMobileNumberError);
        tvDateOfBirthError = findViewById(R.id.tvDateOfBirthError);
        tvAgeError = findViewById(R.id.tvAgeError);
        tvGenderError = findViewById(R.id.tvGenderError);

        btnPickDate = findViewById(R.id.btnPickDate);

        context = MainActivity.this;

        loadingDialog = new LoadingDialog(context);
        messageDialog = new MessageDialog(context);
        responseDialog = new ResponseDialog(context);

        if (Credentials.isEmpty(dateOfBirth)) age = 0;
        etAge.setText(getString(R.string.age_value, age));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        initRetroFit();

        componentEvents();
    }

    private void initRetroFit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        repository = retrofit.create(Repository.class);

        getResponses();
    }

    private void getResponses() {
        loadingDialog.showDialog();

        Call<Responses> call = repository.getResponses();

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(@NonNull Call<Responses> call, @NonNull retrofit2.Response<Responses> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    responseList = response.body().getResponseList();

                    messageDialog.setMessage("Response Success (Code: " + response.code() +")");
                    messageDialog.showDialog();
                } else {
                    messageDialog.setMessage("Response Failed (Code: " + response.code() +")");
                    messageDialog.showDialog();
                }

                loadingDialog.dismissDialog();
            }

            @Override
            public void onFailure(@NonNull Call<Responses> call, @NonNull Throwable t) {
                messageDialog.setMessage(t.getMessage());
                messageDialog.showDialog();

                loadingDialog.dismissDialog();
            }
        });
    }

    private void submitResponse(Response response) {
        loadingDialog.showDialog();

        Call<Response> call = repository.submitResponse(response);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Response response1 = response.body();

                    if (response1 != null && response1.getDateOfBirth() != null)
                        responseList.add(response1);
                    
                    responseDialog.showDialog(responseList);

                    messageDialog.setMessage("Post Success (Code: " + response.code() +")");
                    messageDialog.showDialog();
                } else {
                    messageDialog.setMessage("Post Failed (Code: " + response.code() +")");
                    messageDialog.showDialog();
                }

                loadingDialog.dismissDialog();
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                messageDialog.setMessage(t.getMessage());
                messageDialog.showDialog();

                loadingDialog.dismissDialog();
            }
        });
    }

    private void componentEvents() {
        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fullName = editable != null ? Credentials.fullTrim(editable.toString()) : "";

                tvFullNameError.setVisibility(View.INVISIBLE);
                etFullName.setBackgroundResource(R.drawable.input_bg);
                checkFullNameError();
            }
        });

        etEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailAddress = editable != null ? Credentials.fullTrim(editable.toString()) : "";

                tvEmailAddressError.setVisibility(View.INVISIBLE);
                etEmailAddress.setBackgroundResource(R.drawable.input_bg);
                checkEmailAddressError();
            }
        });

        etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mobileNumber = editable != null ? Credentials.fullTrim(editable.toString()) : "";

                tvMobileNumberError.setVisibility(View.INVISIBLE);
                etMobileNumber.setBackgroundResource(R.drawable.input_bg);
                checkMobileNumberError();
            }
        });

        etDateOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvDateOfBirthError.setVisibility(View.INVISIBLE);
                etDateOfBirth.setBackgroundResource(R.drawable.input_bg);
                checkDateOfBirthError();

                String stringDateTime = dateOfBirth + " 00:00:00";
                DateTime dateTime = new DateTime(stringDateTime);

                age = (int) Math.floor(Units.msToYear(
                        new DateTime().getDateTimeValue() - dateTime.getDateTimeValue())
                );
                etAge.setText(getString(R.string.age_value, age));
            }
        });

        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvAgeError.setVisibility(View.INVISIBLE);
                etAge.setBackgroundResource(R.drawable.input_bg);
                checkAgeError();
            }
        });

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = getResources().getStringArray(R.array.genders)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPickDate.setOnClickListener(view -> showDatePicker());

        btnSubmit.setOnClickListener(view -> {
            hideErrors();

            checkFullNameError();
            checkEmailAddressError();
            checkMobileNumberError();
            checkDateOfBirthError();
            checkAgeError();
            checkGenderError();

            if (isNoError()) {
                Response response = new Response(
                        Credentials.getUniqueId(),
                        fullName,
                        emailAddress,
                        mobileNumber,
                        dateOfBirth,
                        gender,
                        age
                );

                submitResponse(response);
            }
        });

        btnResponses.setOnClickListener(view -> responseDialog.showDialog(responseList));
    }

    private void hideErrors() {
        tvFullNameError.setVisibility(View.INVISIBLE);
        tvEmailAddressError.setVisibility(View.INVISIBLE);
        tvMobileNumberError.setVisibility(View.INVISIBLE);
        tvDateOfBirthError.setVisibility(View.INVISIBLE);
        tvAgeError.setVisibility(View.INVISIBLE);
        tvGenderError.setVisibility(View.INVISIBLE);

        etFullName.setBackgroundResource(R.drawable.input_bg);
        etEmailAddress.setBackgroundResource(R.drawable.input_bg);
        etMobileNumber.setBackgroundResource(R.drawable.input_bg);
        etDateOfBirth.setBackgroundResource(R.drawable.input_bg);
        etAge.setBackgroundResource(R.drawable.input_bg);
        spGender.setBackgroundResource(R.drawable.input_bg);
    }

    private void checkFullNameError() {
        if (Credentials.isEmpty(fullName)) {
            tvFullNameError.setText(getString(R.string.required_error, "Full Name"));
            tvFullNameError.setVisibility(View.VISIBLE);
            etFullName.setBackgroundResource(R.drawable.input_error_bg);
        } else if (!Credentials.isValidPersonName(fullName)) {
            tvFullNameError.setText(getString(R.string.invalid_error, "Full Name"));
            tvFullNameError.setVisibility(View.VISIBLE);
            etFullName.setBackgroundResource(R.drawable.input_error_bg);
        } else if (!Credentials.isValidLength(fullName, Credentials.REQUIRED_PERSON_NAME_LENGTH, 0)) {
            tvFullNameError.setText(getString(R.string.length_error, "Full Name", Credentials.REQUIRED_PERSON_NAME_LENGTH));
            tvFullNameError.setVisibility(View.VISIBLE);
            etFullName.setBackgroundResource(R.drawable.input_error_bg);
        }
    }

    private void checkEmailAddressError() {
        if (Credentials.isEmpty(emailAddress)) {
            tvEmailAddressError.setText(getString(R.string.required_error, "Email Address"));
            tvEmailAddressError.setVisibility(View.VISIBLE);
            etEmailAddress.setBackgroundResource(R.drawable.input_error_bg);
        } else if (!Credentials.isValidEmailAddress(emailAddress)) {
            tvEmailAddressError.setText(getString(R.string.invalid_error, "Email Address"));
            tvEmailAddressError.setVisibility(View.VISIBLE);
            etEmailAddress.setBackgroundResource(R.drawable.input_error_bg);
        }
    }

    private void checkMobileNumberError() {
        if (Credentials.isEmpty(mobileNumber)) {
            tvMobileNumberError.setText(getString(R.string.required_error, "Mobile Number"));
            tvMobileNumberError.setVisibility(View.VISIBLE);
            etMobileNumber.setBackgroundResource(R.drawable.input_error_bg);
        } else if (!Credentials.isValidPhoneNumber(mobileNumber, Credentials.REQUIRED_PHONE_NUMBER_LENGTH_PH) ||
                !mobileNumber.startsWith("09")) {
            tvMobileNumberError.setText(getString(R.string.invalid_error, "Mobile Number"));
            tvMobileNumberError.setVisibility(View.VISIBLE);
            etMobileNumber.setBackgroundResource(R.drawable.input_error_bg);
        }
    }

    private void checkDateOfBirthError() {
        if (Credentials.isEmpty(dateOfBirth)) {
            tvDateOfBirthError.setText(getString(R.string.required_error, "Date of Birth"));
            tvDateOfBirthError.setVisibility(View.VISIBLE);
            etDateOfBirth.setBackgroundResource(R.drawable.input_error_bg);
        }
    }

    private void checkAgeError() {
        if (age < 18) {
            tvAgeError.setText(getString(R.string.age_error));
            tvAgeError.setVisibility(View.VISIBLE);
            etAge.setBackgroundResource(R.drawable.input_error_bg);
        }
    }

    private void checkGenderError() {
        if (Credentials.isEmpty(gender)) {
            tvGenderError.setText(getString(R.string.required_error, "Gender"));
            tvGenderError.setVisibility(View.VISIBLE);
            spGender.setBackgroundResource(R.drawable.input_error_bg);
        }
    }

    private boolean isNoError() {
        return tvFullNameError.getVisibility() != View.VISIBLE &&
                tvEmailAddressError.getVisibility() != View.VISIBLE &&
                tvMobileNumberError.getVisibility() != View.VISIBLE &&
                tvDateOfBirthError.getVisibility() != View.VISIBLE &&
                tvAgeError.getVisibility() != View.VISIBLE &&
                tvGenderError.getVisibility() != View.VISIBLE;
    }

    private void showDatePicker() {
        int calendarYear = calendar.get(Calendar.YEAR);
        int calendarMonth = calendar.get(Calendar.MONTH);
        int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, i, i1, i2) -> {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth() + 1;
                    int day = datePicker.getDayOfMonth();

                    dateOfBirth = year + "/" + month + "/" + day;

                    String stringDateTime = dateOfBirth + " 00:00:00";
                    DateTime dateTime = new DateTime(stringDateTime);

                    dateOfBirth = dateTime.getDate();

                    calendar.set(year, month - 1, day);

                    etDateOfBirth.setText(dateOfBirth);

                }, calendarYear, calendarMonth, calendarDay);

        datePickerDialog.show();
    }

    private void clear() {
        etFullName.getText().clear();

        etEmailAddress.getText().clear();

        etMobileNumber.getText().clear();

        etDateOfBirth.getText().clear();

        age = 0;
        etAge.setText(getString(R.string.age_value, age));

        spGender.setSelection(0);

        hideErrors();

        checkFullNameError();
        checkEmailAddressError();
        checkMobileNumberError();
        checkDateOfBirthError();
        checkAgeError();
        checkGenderError();
    }
}