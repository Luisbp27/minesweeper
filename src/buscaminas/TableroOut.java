import java.io.*;

public class TableroOut {
    // ATRIBUTOS

    private final ObjectOutputStream objectOutputStream;

    /**
     * Constructor de la clase
     * @param file - File
     * @throws IOException
     */
    public TableroOut(File file) throws IOException {
        // Establecemos enlace con el fichero
        objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
    }

    /**
     * Método para guardar los atributos necesarios de un objeto Tablero pasado 
     * por parámetro
     * @param tablero - Objeto Tablero
     * @throws IOException
     */
    public void guardarTablero(Tablero tablero) throws IOException {
        // Declaramos las matrices y les asignamos el valor correspondiente
        int[][] numeros = tablero.getNumerosCasillas();
        int[][] estados = tablero.getEstadoCasillas();
        int[][] banderas = tablero.getEstadoBanderas();

        // Escribimos las variables en el fichero
        objectOutputStream.writeObject(numeros);
        objectOutputStream.writeObject(estados);
        objectOutputStream.writeObject(banderas);
        objectOutputStream.writeObject(Buscaminas.tirada);
    }

    /**
     * Método que cierra el enlace con el fichero
     * @throws IOException
     */
    public void close() throws IOException {
        // Cerramos enlace con el fichero
        objectOutputStream.close();
    }
}
