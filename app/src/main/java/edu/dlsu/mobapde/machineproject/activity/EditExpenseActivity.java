package edu.dlsu.mobapde.machineproject.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class EditExpenseActivity extends AppCompatActivity {

    private EditText datetimeText, costText, nameText, vibrationText;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ImageView thumbnailView;
    private Spinner typeSpinner, regretLvlSpinner;
    private Button cancelBtn, saveBtn, deleteBtn;

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

        cancelBtn = findViewById(R.id.cancelBtn);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

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
    public void pickPhoto(View view) {
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
    public void capturePhoto(View view) {
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
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

                    // ImageView.setImageBitmap(selectedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            // Log.d("Bitmap", String.valueOf(bitmap.getByteCount()));
            // ImageView.setImageBitmap(bitmap);
        }
    }

    public void triggerDateTimePicker(View view) {
        dateTime = "";
        datePickerDialog.show();
    }

    public void saveInformation(View view) {

    }

    public void revertBack(View view) {

    }
}
