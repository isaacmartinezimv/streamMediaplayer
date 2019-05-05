package isaacmartinez.damm8.com.streammediaplayer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<InfoCancion> listaDeCanciones;
    RecyclerView recyclerCanciones;
    FirebaseDatabase firebaseDatabase;
    AdapterCanciones adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeCanciones = new ArrayList<>();
        loadList();

        recyclerCanciones = findViewById(R.id.recyclerId);
        recyclerCanciones.setLayoutManager(new LinearLayoutManager((this)));
        adapter = new AdapterCanciones((listaDeCanciones));
        recyclerCanciones.setAdapter(adapter);
    }

    private void loadList(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("music").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                InfoCancion cancion;
                cancion = dataSnapshot.getValue(InfoCancion.class);

                listaDeCanciones.add(cancion);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

