package edu.ucsb.ece.ece150.pickture;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/*
 * Whatever you do, remember: whatever gets the job done is a good first solution.
 * Then start to redo it, keeping the job done, but the solutions more and more elegant.
 *
 * Don't satisfy yourself with the first thing that comes out.
 * Dig through the documentation, read your error logs.
 */
public class MainActivity extends AppCompatActivity {

    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = (ImageView) this.findViewById(R.id.profile_image);
        System.out.println("onCreate. SavedInstanceState is: " + savedInstanceState == null);

        if ( savedInstanceState != null && savedInstanceState.getByteArray("byteArray") == null)
            profileImage.setImageResource(R.drawable.mountain1);
        else if (savedInstanceState != null){

            byte[] byteArray = savedInstanceState.getByteArray("byteArray");
            System.out.println("byteArray is null? " + byteArray == null);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
            profileImage.setImageBitmap(bitmap);
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // TODO: this part may need some coding
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: this part may need some coding
    }

    // depending on how you are going to pass information back and forth, you might need this
    // uncommented and filled out:

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                profileImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        System.out.println("onSaveInstanceState");
        if (savedInstanceState != null) {
            BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            savedInstanceState.putByteArray("byteArray", byteArray);
        }
        else{
            System.out.println("onSaveState saved state is null");
        }
        super.onSaveInstanceState(savedInstanceState);
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        System.out.println("onRestoreInstanceState");
        if (savedInstanceState != null) {
            byte[] byteArray = savedInstanceState.getByteArray("byteArray");
            System.out.println("byteArray is null? " + byteArray == null);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            profileImage.setImageBitmap(bitmap);
        }
        else{
            System.out.println("onRestore saved state is null");
        }
    }
}
