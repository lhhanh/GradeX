package com.example.gradex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.camera.core.*;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.FlowLayout;
import com.google.common.util.concurrent.ListenableFuture;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.sceneform.rendering.CameraProvider;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";

    private ImageView imgSettings, imgSupport, imgLibrary, imgBookmarked, cameraButton, arrowDown;
    private TextView txtLibrary, txtBookmarked;
    private LinearLayout libraryLinear, bookmarkLinear;
    private PreviewView viewFinder;

    private RecyclerView savedTestRecView;
    private SavedTestRecViewAdapter adapter;

    private File outputDirectory;
    private ExecutorService cameraExecutor;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();

        // Saved tests RecView + Adapter
        adapter = new SavedTestRecViewAdapter(this);
        savedTestRecView = findViewById(R.id.savedTestRecView);
        savedTestRecView.setAdapter(adapter);
        savedTestRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<Test> savedTests = new ArrayList<>();
        Test test = new Test("Kiểm tra Hóa 45ph", "13 Văn", "20/10/2021", "drawable/ic_chemistry.xml");
        test.setSaved(true);
        savedTests.add(test);

        adapter.setSavedTests(savedTests);

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        // Set up the listener for take photo button
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        //outputDirectory = getOutputDirectory();

        cameraExecutor = Executors.newSingleThreadExecutor();

        //Set up Library button listeners
        libraryLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(intent);
            }
        });

        // Set up Saved tests button listeners
        bookmarkLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedTestRecView.setVisibility(View.VISIBLE);
                arrowDown.setVisibility(View.VISIBLE);
                bookmarkLinear.setVisibility(View.GONE);
                libraryLinear.setVisibility(View.GONE);
            }
        });

        // Exit the saved test view
        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedTestRecView.setVisibility(View.GONE);
                arrowDown.setVisibility(View.GONE);
                bookmarkLinear.setVisibility(View.VISIBLE);
                libraryLinear.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

   private File getOutputDirectory() {
       Optional<File> mediaDirs = Arrays.stream(getExternalMediaDirs())
               .findFirst()
               .map(m -> new File(m, getResources().getString(R.string.app_name)));
       mediaDirs.ifPresent(File::mkdirs);
       return mediaDirs.filter(File::exists).orElse(getFilesDir());
   }

    private void takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        ImageCapture imageCapture = new ImageCapture.Builder().build();

        // Create time-stamped output file to hold the image
        File photoFile = new File(
                outputDirectory,
                new SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                        .format(System.currentTimeMillis()) + ".jpg");

        // Create output options object which contains file + metadata
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // Set up image capture listener, which is triggered after photo has been taken
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                        String savedUri = Uri.fromFile(photoFile).toString();
                        String msg = "Photo capture succeeded:" + savedUri;
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, msg);
                    }
                    @Override
                    public void onError(ImageCaptureException error) {
                        Log.d(TAG, "Photo capture failed" + error.toString());
                    }
                }
        );
    }

    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(MainActivity.this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        //Preview
        Preview preview = new Preview.Builder().build();

        ImageCapture imageCapture = new ImageCapture.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
        cameraProvider.unbindAll();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    private boolean allPermissionsGranted() {
        for(String permission: REQUIRED_PERMISSIONS) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

    private void initView() {
        Log.d(TAG, "initView: Started");

        imgSettings = findViewById(R.id.settings);
        imgSupport = findViewById(R.id.support);
        imgLibrary = findViewById(R.id.imgLibrary);
        imgBookmarked = findViewById(R.id.imgBookmarked);
        cameraButton = findViewById(R.id.camera_capture_button);
        arrowDown = findViewById(R.id.arrowDown);

        txtLibrary = findViewById(R.id.txtLibrary);
        txtBookmarked = findViewById(R.id.txtBookmarked);

        libraryLinear = findViewById(R.id.libraryLinear);
        bookmarkLinear = findViewById(R.id.bookmarkLinear);

        viewFinder = findViewById(R.id.viewFinder);

    }
}