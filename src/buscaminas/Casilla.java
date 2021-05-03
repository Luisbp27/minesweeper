import javax.swing.*;
import java.awt.*;
@SuppressWarnings("serial")

public class Casilla extends JLabel {

    // Declaración de dos enteros que corresponden a las medidas del objeto Casilla
    private static final int ANCHO = 30;
    private static final int ALTO = 30;
    // Declaración de una variables booleana para saber si una explosion en el
    // objeto Casilla correspondiente
    private boolean explotada;

    /**
     * Constructor de la clase que directamente visualiza la imagen vacía en la
     * casilla
     * 
     */
    public Casilla() {
        explotada = false;
        colocarImagen("imagenes/imagenVacia.jpg");
    }

    /**
     * Método que coloca la imágen de la mina
     * 
     */
    public void colocarImagenMina() {
        colocarImagen("imagenes/bomba.jpg");
    }

    /**
     * Método que coloca la imágen de la bandera
     * 
     */
    public void colocarImagenBandera() {
        colocarImagen("imagenes/bandera.png");
    }

    /**
     * Método que quita la imágen de la bandera
     * 
     */
    public void quitarImagenBandera() {
        colocarImagen("imagenes/imagenVacia.jpg");
    }

    /**
     * Método que coloca la imágen de la explosión
     * 
     */
    public void colocarImagenExplosion() {
        colocarImagen("imagenes/explosion.png");
        explotada = true;
    }

    /**
     * Método que quita una imágen
     * 
     */
    public void quitarImagen() {
        colocarImagen("imagenes/imagenVacia.jpg");
    }

    /**
     * Método que pasado un número por parámetro, coloca la imágen del
     * correspondiente número en la casilla
     * 
     * @param numeroImagen
     */
    public void colocarImagenNumero(int numeroImagen) {
        String numero = Integer.toString(numeroImagen);

        colocarImagen("imagenes/" + numero + ".jpg");
    }

    /**
     * Método que pasada la ruta por parámetro, coloca la imágen en la casilla
     * 
     * @param ruta
     */
    public void colocarImagen(String ruta) {
        ImageIcon imagen = new ImageIcon(ruta);
        this.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(ANCHO, ALTO, Image.SCALE_DEFAULT)));
    }

    /**
     * Método que pasado unm booleano por parámetro, lo asigna al atributo explotada
     * 
     * @param b
     */
    public void setExplotada(boolean b) {
        this.explotada = b;
    }

    /**
     * Método que devuelve el valor del atributo explotada
     * 
     * @return
     */
    public boolean isExplotada() {
        return explotada;
    }
}
