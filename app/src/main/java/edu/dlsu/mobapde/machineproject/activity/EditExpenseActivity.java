package edu.dlsu.mobapde.machineproject.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.values.Constants;
import edu.dlsu.mobapde.machineproject.values.Static;

import static edu.dlsu.mobapde.machineproject.values.Constants.UI_UPDATE_TAG;

public class EditExpenseActivity extends AppCompatActivity {

    private EditText datetimeText, costText, nameText, vibrationText;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ImageView thumbnailView;
    private Spinner typeSpinner, regretLvlSpinner;
    private Button saveBtn, deleteBtn;
    private Bitmap selectedImage;

    private TextWatcher watcher;

    private Expense existingEntry;

    private String dateTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        getSupportActionBar().hide();
        Calendar calendar = Calendar.getInstance();

        nameText = findViewById(R.id.name);
        costText = findViewById(R.id.cost);

        typeSpinner = findViewById(R.id.type);
        regretLvlSpinner = findViewById(R.id.regret_level);
        thumbnailView = findViewById(R.id.image_receipt);

        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        vibrationText = findViewById(R.id.time);
        datetimeText = findViewById(R.id.datetime);

        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    saveBtn.setEnabled(false);
                }
                else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (nameText.getText().length() == 0 || costText.getText().length() == 0 || datetimeText.getText().length() == 0) {
                    saveBtn.setEnabled(false);
                }
                else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        costText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (nameText.getText().length() == 0 || costText.getText().length() == 0 || datetimeText.getText().length() == 0) {
                    saveBtn.setEnabled(false);
                }
                else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        datetimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (nameText.getText().length() == 0 || costText.getText().length() == 0 || datetimeText.getText().length() == 0) {
                    saveBtn.setEnabled(false);
                }
                else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (getIntent().getStringExtra("Status").equals("Existing")) {
            saveBtn.setText(R.string.update_text);
            deleteBtn.setVisibility(View.VISIBLE);
            int id = getIntent().getIntExtra("Id", 0);
            existingEntry = Static.getDatabaseInstance().dao().getExpense(id);

            List<String> types = Arrays.asList(getResources().getStringArray(R.array.types));
            List<String> regretLevels = Arrays.asList(getResources().getStringArray(R.array.regret_levels));

            Log.d("Expense", String.valueOf(existingEntry));

            nameText.setText(existingEntry.getName());
            costText.setText(Constants.format.format(existingEntry.getCost()));

            datetimeText.setText(Converter.toDate(existingEntry.getDateTimeMillis()));
            dateTime = datetimeText.getText().toString();
            vibrationText.setText(String.valueOf(existingEntry.getVibratorSeconds()));

            try {
                if (new SimpleDateFormat("MM/dd/yyyy, h:mm a", Locale.ENGLISH).parse(dateTime).after(new Date())) {
                    vibrationText.setEnabled(true);
                    vibrationText.addTextChangedListener(watcher);
                }
                else {
                    vibrationText.setEnabled(false);
                    vibrationText.removeTextChangedListener(watcher);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            typeSpinner.setSelection(types.indexOf(existingEntry.getType()));

            regretLvlSpinner.setSelection(regretLevels.indexOf(String.valueOf(existingEntry.getRegretLevel())));

            if (existingEntry.getImage() != null) {
                selectedImage = Converter.toImage(existingEntry.getImage());
                thumbnailView.setImageBitmap(selectedImage);
            }
        }
        // if adding a new expense entry
        else {
            deleteBtn.setVisibility(View.GONE);
        }

        timePickerDialog = new TimePickerDialog(this, (timePicker, hour, minute) -> {
            String label, minutes;
            if (hour > 12) {
                hour -= 12;
                label = "pm";
            }
            else if (hour == 0) {
                hour += 12;
                label = "am";
            }
            else if (hour == 12) {
                label = "pm";
            }
            else {
                label = "am";
            }

            if (minute < 10)
                minutes = "0".concat(String.valueOf(minute));
            else
                minutes = String.valueOf(minute);
            dateTime = dateTime.concat(", ").concat(String.valueOf(hour)).concat(":").concat(String.valueOf(minutes).concat(" ").concat(label));
            timePickerDialog.dismiss();
            try {
                if (new SimpleDateFormat("MM/dd/yyyy, h:mm a", Locale.ENGLISH).parse(dateTime).after(new Date())) {
                    vibrationText.setEnabled(true);
                    vibrationText.addTextChangedListener(watcher);
                    saveBtn.setEnabled(false);
                }
                else {
                    saveBtn.setEnabled(true);
                    vibrationText.setEnabled(false);
                    vibrationText.removeTextChangedListener(watcher);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            datetimeText.setText(dateTime);
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);

        datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            dateTime = dateTime.concat(String.valueOf(month + 1)).concat("/").concat(String.valueOf(day))
                    .concat("/").concat(String.valueOf(year));
            timePickerDialog.show();
            datePickerDialog.dismiss();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }

    // Display image gallery
    public void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);

        intent.setDataAndType(data, "image/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, Constants.ACCESS_PHOTO_LIBRARY_REQUEST_CODE);
        }
    }

    // opens the camera and lets user take a photo
    public void capturePhoto() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        // Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // when accessing photo library
        if (requestCode == Constants.ACCESS_PHOTO_LIBRARY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri photoUri = data.getData();
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    thumbnailView.setImageBitmap(selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            selectedImage = (Bitmap) data.getExtras().get("data");
            Log.d("Bitmap", String.valueOf(selectedImage.getByteCount()));
            thumbnailView.setImageBitmap(selectedImage);
        }
    }

    public void triggerDateTimePicker(View view) {
        dateTime = "";
        datePickerDialog.show();
    }

    public void saveInformation(View view) {
        // update expense
        if (getIntent().getStringExtra("Status").equals("Existing")) {
            String name = nameText.getText().toString();
            double cost = Double.parseDouble(costText.getText().toString());
            int levelOfRegret = Integer.parseInt(regretLvlSpinner.getSelectedItem().toString());
            String type = (String) typeSpinner.getSelectedItem();
            long millis = Converter.toMilliseconds(dateTime);

            existingEntry.setDateTimeMillis(millis);
            // cancel previous intent
            Intent intent = new Intent(UI_UPDATE_TAG);
            PendingIntent oldPendingIntent = PendingIntent.getBroadcast(this, 1000000+existingEntry.getJobId(), intent, 0);
            Static.getManagerInstance().cancel(oldPendingIntent);

            if (millis > System.currentTimeMillis()) {
                long vibration = Long.parseLong(vibrationText.getText().toString());
                existingEntry.setPast(false);
                int jobId = Constants.JOB_ID;
                existingEntry.setJobId(jobId);
                existingEntry.setVibratorSeconds(vibration);

                Intent alarmIntent = new Intent(UI_UPDATE_TAG);
                alarmIntent.putExtra("Name", name);
                Log.d("Name", name);
                alarmIntent.putExtra("Id", existingEntry.getId());
                alarmIntent.putExtra("Vibration", vibration);

                PendingIntent newPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1000000+jobId, alarmIntent, 0);
                Constants.JOB_ID++;
                Static.getManagerInstance().set(AlarmManager.RTC_WAKEUP, millis, newPendingIntent);
            }
            else {
                existingEntry.setPast(true);
                existingEntry.setJobId(0);
            }

            if (selectedImage != null) {
                existingEntry.setImage(Converter.toByteArray(selectedImage));
                selectedImage = null;
            }

            existingEntry.setName(name);
            existingEntry.setCost(cost);
            existingEntry.setRegretLevel(levelOfRegret);
            existingEntry.setType(type);
            Static.getDatabaseInstance().dao().updateExpense(existingEntry);
        }
        // add a new expense
        else {
            String name = nameText.getText().toString();
            double cost = Double.parseDouble(costText.getText().toString());
            int levelOfRegret = Integer.parseInt(regretLvlSpinner.getSelectedItem().toString());
            String type = (String) typeSpinner.getSelectedItem();
            long millis = Converter.toMilliseconds(dateTime);

            Expense newEntry = new Expense(name, levelOfRegret, type, millis, cost);

            if (selectedImage != null) {
                newEntry.setImage(Converter.toByteArray(selectedImage));
                selectedImage = null;
            }

            Long id = Static.getDatabaseInstance().dao().addExpense(newEntry);
            existingEntry = Static.getDatabaseInstance().dao().getExpense(id.intValue());

            if (millis > System.currentTimeMillis()) {
                long vibration = Long.parseLong(vibrationText.getText().toString());
                existingEntry.setPast(false);
                int jobId = Constants.JOB_ID;
                existingEntry.setJobId(jobId);
                existingEntry.setVibratorSeconds(vibration);

                Intent alarmIntent = new Intent(UI_UPDATE_TAG);
                alarmIntent.putExtra("Name", name);
                alarmIntent.putExtra("Id", existingEntry.getId());
                alarmIntent.putExtra("Vibration", vibration);
                Log.d("NewId", String.valueOf(existingEntry.getId()));

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1000000+jobId, alarmIntent, 0);
                Constants.JOB_ID++;
                Static.getManagerInstance().set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);

            }

            else {
                existingEntry.setPast(true);
                existingEntry.setJobId(0);
            }

            Static.getDatabaseInstance().dao().updateExpense(existingEntry);

        }
        Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String fragmentName = getIntent().getStringExtra("FragmentName");
        if (fragmentName != null) {
            intent.putExtra("FragmentName", fragmentName);
        }
        startActivity(intent);
        finish();
    }

    public void deleteExpense(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to permanently erase this expense?")
                .setNegativeButton("No", ((dialogInterface, i) -> {

                }))
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Expense e = Static.getDatabaseInstance().dao().getExpense(getIntent().getIntExtra("Id", 0));
                    Static.getDatabaseInstance().dao().deleteExpense(e);
                    Toast.makeText(this, "Expense successfully removed.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                    String fragmentName = getIntent().getStringExtra("FragmentName");
                    if (fragmentName != null) {
                        intent.putExtra("FragmentName", fragmentName);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });

        builder.show();
    }

    public void prompt(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Image Preference?")
                .setNegativeButton("Choose from Image Gallery", ((dialogInterface, i) -> pickPhoto()))
                .setPositiveButton("Take Photo", (dialogInterface, i) -> capturePhoto())
                .setNeutralButton("Cancel", (dialogInterface, i) -> {});
        builder.show();
    }

    public void accessLvlRegretInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("About the Regret Level")
                .setMessage("A regret level is the opposite of having satisfaction. ")
                .setPositiveButton("OK, Got it", (dialogInterface, i) -> {});
        builder.show();
    }

    public void revertBack(View view) {
        Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
        String fragmentName = getIntent().getStringExtra("FragmentName");
        if (fragmentName != null) {
            intent.putExtra("FragmentName", fragmentName);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private long cleanVibrationSeconds(String val) {
        return (val.equals("") || Long.parseLong(val) < 0) ?
                1 : Long.parseLong(val);
    }
}