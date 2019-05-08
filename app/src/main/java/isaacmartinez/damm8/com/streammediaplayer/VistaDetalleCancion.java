package isaacmartinez.damm8.com.streammediaplayer;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;

public class VistaDetalleCancion extends AppCompatActivity {
    TextView nombre, artista;
    ImageView portada;
    ImageButton botonPlay;
    MediaPlayer mediaPlayer;
    CardView cardPortada;

    FirebaseStorage firebaseStorage;
    Bitmap portadaGrande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_detalle_cancion);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        obtenerDatos();
    }

    private void cargarReproductor(String urlStreaming) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(urlStreaming);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareView(String nombreCancion, String nombreArtista, String urlPortada) {
        nombre = findViewById(R.id.detalleNombreCancionId);
        nombre.setText(nombreCancion);

        artista = findViewById(R.id.detalleArtistaId);
        artista.setText(nombreArtista);

        botonPlay = findViewById(R.id.detalleBotonPlayId);
        portada = findViewById(R.id.detallePortadaId);
        cardPortada = findViewById(R.id.fullPortadaId);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage.getReferenceFromUrl(urlPortada).getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                portadaGrande = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                portada.setImageBitmap(portadaGrande);
            }
        });

        portada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    animacionAparecer(botonPlay);
                    cardPortada.animate().scaleY((float) 1.0).scaleX((float) 1.0).setDuration(900);
                }
            }
        });

        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                animacionDesaparecer(botonPlay);
                cardPortada.animate().scaleY((float) 1.50).scaleX((float) 1.50).setDuration(900);
            }
        });
    }

    private void animacionAparecer(View view) {
        AlphaAnimation animacion = new AlphaAnimation(0.0f, 1.0f);
        animacion.setDuration(900);
        view.startAnimation(animacion);
        view.setVisibility(View.VISIBLE);
    }

    private void animacionDesaparecer(View view) {
        AlphaAnimation animacion = new AlphaAnimation(1.0f, 0.0f);
        animacion.setDuration(900);
        view.startAnimation(animacion);
        view.setVisibility(View.INVISIBLE);
    }

    private void obtenerDatos() {
        if(getIntent().hasExtra("nombre_cancion") &&
                getIntent().hasExtra("nombre_artista" ) &&
                getIntent().hasExtra("url_portada") &&
                getIntent().hasExtra("url_streaming")) {

            String nombreCancion = getIntent().getStringExtra("nombre_cancion");
            String nombreArtista = getIntent().getStringExtra("nombre_artista");
            String urlPortada = getIntent().getStringExtra("url_portada");
            String urlStreaming = getIntent().getStringExtra("url_streaming");

            prepareView(nombreCancion, nombreArtista, urlPortada);
            cargarReproductor(urlStreaming);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
