import java.io.*;

public class TableroIn {
    // ATRIBUTOS

    private final ObjectInputStream objectInputStream;

    /**
     * Constructor de la clase
     * @param file - File
     * @throws IOException
     */
    public TableroIn(File file) throws IOException {
        // Establecemos enlace con el fichero
        objectInputStream = new ObjectInputStream(new FileInputStream(file));
    }

    /**
     * Método que carga el objeto Tablero del fichero
     * @param tablero - Objeto Tablero
     * @throws IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void abrirTablero(Tablero tablero) throws IOException, ClassNotFoundException {
        // Leemos las matrices y el valor tirada del fichero
        int[][] numeros = (int[][])objectInputStream.readObject();
        int[][] estados = (int[][])objectInputStream.readObject();
        int [][] banderas = (int[][])objectInputStream.readObject();
        Buscaminas.tirada = (int)objectInputStream.readObject();

        // Pasamos las variables al tablero para que las actualize
        tablero.cargarTablero(numeros, estados, banderas);
    }

    /**
     * Método que cierra el enlace con el fichero
     * @throws IOException
     */
    public void close() throws IOException {
        // Cerramos el enlace con el fichero
        objectInputStream.close();
    }
}
