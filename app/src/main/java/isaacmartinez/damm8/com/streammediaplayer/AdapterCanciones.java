package isaacmartinez.damm8.com.streammediaplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class AdapterCanciones extends RecyclerView.Adapter<AdapterCanciones.ViewHolderCanciones> {
    ArrayList<InfoCancion> listaDeCanciones;
    Bitmap portada;
    Intent intent;
    FirebaseStorage firebaseStorage;

    public AdapterCanciones(ArrayList<InfoCancion> listaDeCanciones) {
        this.listaDeCanciones = listaDeCanciones;
    }

    @NonNull
    @Override
    public AdapterCanciones.ViewHolderCanciones onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elemento_lista, null, false);
        return new ViewHolderCanciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCanciones.ViewHolderCanciones holder, int position) {
        holder.nombre.setText(listaDeCanciones.get(position).getNombreCancion());
        holder.artista.setText(listaDeCanciones.get(position).getNombreArtista());

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorage.getReferenceFromUrl(listaDeCanciones.get(position).getUrlPortada()).getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                portada = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.portadaCD.setImageBitmap(portada);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeCanciones.size();
    }

    public class ViewHolderCanciones extends RecyclerView.ViewHolder {
        TextView nombre, artista;
        ImageView portadaCD;

        public ViewHolderCanciones(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombreCancionId);
            artista = itemView.findViewById(R.id.artistaId);
            portadaCD = itemView.findViewById(R.id.portadaId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicionEnLista = getAdapterPosition();
                    intent = new Intent(v.getContext(), VistaDetalleCancion.class);

                    intent.putExtra("nombre_cancion", listaDeCanciones.get(posicionEnLista).getNombreCancion());
                    intent.putExtra("nombre_artista", listaDeCanciones.get(posicionEnLista).getNombreArtista());
                    intent.putExtra("url_portada", listaDeCanciones.get(posicionEnLista).getUrlPortada());
                    intent.putExtra("url_streaming", listaDeCanciones.get(posicionEnLista).getUrlStreaming());

                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
