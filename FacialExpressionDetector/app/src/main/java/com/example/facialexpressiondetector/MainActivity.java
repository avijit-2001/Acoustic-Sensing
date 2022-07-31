package com.example.facialexpressiondetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        dispatchTakePictureIntent();
    }


    private void dispatchTakePictureIntent() throws ActivityNotFoundException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,
                REQUEST_IMAGE_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String TAG = "onActivityResult";
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Face Detection Code
            FaceDetectorOptions realTimeOpts =
                    new FaceDetectorOptions.Builder()
                            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                            .build();
            InputImage image = InputImage.fromBitmap(imageBitmap, 0);
            FaceDetector detector = FaceDetection.getClient(realTimeOpts);
            detector.process(image)
                    .addOnSuccessListener(
                            faces -> {
                                Log.d(TAG, "Task completed successfully");

                                for (Face face : faces) {
                                    Rect bounds;
                                    bounds = face.getBoundingBox();
                                    Log.d(TAG, bounds.toString());
                                    Bitmap finalImage;
                                    finalImage = Bitmap.createBitmap(
                                            imageBitmap,
                                            bounds.left,
                                            bounds.top,
                                            bounds.width(),
                                            bounds.height()
                                    );
                                    imageView.setImageBitmap(finalImage);
                                }
                            })
                    .addOnFailureListener(
                            e -> {
                                Log.d(TAG, "Task Failed with an exception: " + e.toString());
                            });
        }
    }

}