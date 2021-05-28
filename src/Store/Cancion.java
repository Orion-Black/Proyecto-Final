package Store;

public class Cancion {

    private int keyS;

    private String nameSong;
    private String nameAuthor;
    private String genre;
    private String coverPage;
    private String album;
    private String file;
    private double price;

    public Cancion(int clave, String nombreCancion, String nombreCantante, String genero,
                   String portada, String album, String archivo, double precio) {
        this.keyS = clave;
        this.nameSong = nombreCancion;
        this.nameAuthor = nombreCantante;
        this.genre = genero;
        this.coverPage = portada;
        this.album = album;
        this.file = archivo;
        this.price = precio;
    }

    public int getKeyS() {
        return keyS;
    }

    public void setKeyS(int keyS) {
        this.keyS = keyS;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String formatoArchivo() {
        return keyS + "," + nameSong + "," + nameAuthor + "," + genre + "," +
                coverPage + "," + album + "," + file + "," + price;
    }
}


