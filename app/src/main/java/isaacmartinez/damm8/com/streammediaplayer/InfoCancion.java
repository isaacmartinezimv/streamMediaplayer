package isaacmartinez.damm8.com.streammediaplayer;

public class InfoCancion {
    String nombreCancion;
    String nombreArtista;
    String urlPortada;
    String urlStreaming;

    public InfoCancion() {
    }

    public InfoCancion(String nombreCancion, String nombreArtista, String urlPortada, String urlStreaming) {
        this.nombreCancion = nombreCancion;
        this.nombreArtista = nombreArtista;
        this.urlPortada = urlPortada;
        this.urlStreaming = urlStreaming;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }
}
