package edu.dlsu.mobapde.machineproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

import edu.dlsu.mobapde.machineproject.R;
import edu.dlsu.mobapde.machineproject.converter.Converter;
import edu.dlsu.mobapde.machineproject.database.ExpenseDatabase;
import edu.dlsu.mobapde.machineproject.values.Constants;

public class MainActivity extends AppCompatActivity {

    private ExpenseDatabase expenseDatabase;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide title bar
        getSupportActionBar().hide();

        // initialize database
        // expenseDatabase = ExpenseDatabase.getDatabase(this);

        // initialize components









        ////////////////////////////////////////////////////////////////////////////////////////
        // Testing if can insert and retrieve values properly

        /*Expense expense = new Expense();
        expense.setName("Hello");
        expense.setType(Constants.TYPE_BILL);
        expense.setRegretLevel(1);
        expense.setDateTimeMillis(Converter.toMilliseconds("01/21/1998 4:56 am"));

        expenseDatabase = ExpenseDatabase.getDatabase(this);

        expenseDatabase.dao().addExpense(expense);

        for (Expense e: expenseDatabase.dao().getAllExpenses()) {
            Log.d("Expense", e.getName());
            Log.d("Expense RL", String.valueOf(e.getRegretLevel()));
            Log.d("Expense Date", Converter.toDate(e.getDateTimeMillis()));
            Log.d("Expense Type", e.getType());
        }*/

        // Testing to drop the table
        // expenseDatabase = ExpenseDatabase.getDatabase(this);
        // expenseDatabase.clearAllTables();
        //////////////////////////////////////////////////////////

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
        //Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
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
            Bundle extras = data.getExtras();

            Bitmap bitmap = (Bitmap) extras.get("data");

            // Log.d("Bitmap", String.valueOf(bitmap.getByteCount()));
            // ImageView.setImageBitmap(bitmap);
        }
    }
}
