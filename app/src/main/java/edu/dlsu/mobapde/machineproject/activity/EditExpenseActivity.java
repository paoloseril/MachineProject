package edu.dlsu.mobapde.machineproject.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.entity.Expense;
import edu.dlsu.mobapde.machineproject.values.Constants;
import edu.dlsu.mobapde.machineproject.values.Static;

public class EditExpenseActivity extends AppCompatActivity {

    private EditText datetimeText, costText, nameText, vibrationText;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ImageView thumbnailView;
    private Spinner typeSpinner, regretLvlSpinner;
    private Button saveBtn, deleteBtn;
    private Bitmap selectedImage;

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

        if (getIntent().getStringExtra("Status").equals("Existing")) {
            deleteBtn.setVisibility(View.VISIBLE);
            int id = getIntent().getIntExtra("Id", 0);
            Expense existingEntry = Static.getDatabaseInstance().dao().getExpense(id);

            List<String> types = Arrays.asList(getResources().getStringArray(R.array.types));
            List<String> regretLevels = Arrays.asList(getResources().getStringArray(R.array.regret_levels));

            Log.d("Expense", String.valueOf(existingEntry));

            nameText.setText(existingEntry.getName());
            costText.setText(String.valueOf(existingEntry.getCost()));

            Log.d("Date", Converter.toDate(existingEntry.getDateTimeMillis()));
            datetimeText.setText(Converter.toDate(existingEntry.getDateTimeMillis()));
            if (existingEntry.getDateTimeMillis() > System.currentTimeMillis()) {
                vibrationText.setEnabled(true);
                vibrationText.setText(String.valueOf(existingEntry.getVibratorSeconds()));
            }
            else {
                vibrationText.setEnabled(false);
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

        datetimeText = findViewById(R.id.datetime);

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
            if (Converter.toMilliseconds(dateTime) > System.currentTimeMillis()) {
                vibrationText.setEnabled(true);
            }
            else {
                vibrationText.setEnabled(false);
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

        }
        // add a new expense
        else {
            String name = nameText.getText().toString();
            double cost = Double.parseDouble(costText.getText().toString());
            int levelOfRegret = Integer.parseInt(regretLvlSpinner.getSelectedItem().toString());
            String type = (String) typeSpinner.getSelectedItem();
            long millis = Converter.toMilliseconds(datetimeText.getText().toString());

            Expense newEntry = new Expense(name, levelOfRegret, type, millis, cost);

            if (millis > System.currentTimeMillis()) {
                int jobId = Constants.JOB_ID;
                newEntry.setJobId(jobId);

                Intent alarmIntent = new Intent(Constants.UI_UPDATE_TAG);
                alarmIntent.putExtra("AlarmVibration", jobId);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1000000+jobId, alarmIntent, 0);
                Constants.JOB_ID++;
                Static.getManagerInstance().set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
            }

            if (selectedImage != null) {
                newEntry.setImage(Converter.toByteArray(selectedImage));
                selectedImage = null;
            }

            Static.getDatabaseInstance().dao().addExpense(newEntry);
        }
        Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void revertBack(View view) {
        Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                    Toast.makeText(this, "Expense successfully removed,", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });

        builder.show();
    }

    public void prompt(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Image Preference")
                .setMessage("Choose to take photo or get image from gallery")
                .setNegativeButton("Choose from Image Gallery", ((dialogInterface, i) -> pickPhoto()))
                .setPositiveButton("Take Photo", (dialogInterface, i) -> capturePhoto())
                .setNeutralButton("Cancel", (dialogInterface, i) -> {});
        builder.show();
    }
}