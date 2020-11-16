import javax.swing.*;
import java.awt.*;
@SuppressWarnings("serial")

public class Tablero extends JPanel {
    // ATRIBUTOS

    // Matriz de objetos Casilla
    private final Casilla[][] c;
    // Matriz de enteros con el valor de cada casilla
    private int[][] numerosCasillas;
    // Matriz de enteros con el estado de cada casilla (0 = tapada, 1 = destapada)
    private int[][] estadoCasillas;
    // Matriz de enteros con las banderas (0 = no bandera, 1 = bandera)
    private int[][] estadoBanderas;
    // Variable booleana que permite o no editar el tablero al usuario
    private boolean editable;

    /**
     * Constructor de la clase
     */
    public Tablero() {
        c = new Casilla[9][9];
        numerosCasillas = new int[9][9];
        estadoCasillas = new int[9][9];
        estadoBanderas = new int[9][9];

        colocarCasillas();
        colocarMinas();

        this.setLayout(new GridLayout(9, 9));

        // Permitimos la edición del tablero
        editable = true;

        this.setVisible(true);
    }

    // MÉTODOS FUNCIONALES

    /**
     * Método que coloca los 81 objetos Casilla en el panel
     */
    private void colocarCasillas() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                c[i][j] = new Casilla();
                c[i][j].setHorizontalAlignment(JLabel.CENTER);

                // Inicializamos las tres matrices a 0
                estadoCasillas[i][j] = 0;
                estadoBanderas[i][j] = 0;
                numerosCasillas[i][j] = 0;

                this.add(c[i][j]);
            }
        }
    }

    /**
     * Método que coloca las minas aleatoriamente en el tablero
     */
    public void colocarMinas() {
        for (int i = 0; i < 10; i++) {
            int fila = (int)(Math.random() * 9);
            int columna = (int)(Math.random() * 9);

            // Si no hay una mina ya colocada, colocamos una mina
            if(numerosCasillas[fila][columna] != -1) {
                numerosCasillas[fila][columna] = -1;
                colocarNumeros(fila, columna);

            // Sino decrementamos el contador de minas para así forzar otro bucle
            }else {
                i--;
            }
        }
    }

    /**
     * Método que pasadas las coordenadas de una bomba, coloca los correspondientes 
     * números en las casillas adyacentes
     * @param fila - Número de fila de la casilla
     * @param columna - Número de columna de la casilla
     */
    public void colocarNumeros(int fila, int columna) {
        // Si está en el medio de la matriz
        if(fila != 0 && fila != 8 && columna != 0 && columna != 8) {
            // Decrementamos fila y columna para ir a la fila de arriba
            colocarTresUnos(--fila, --columna);

            // Incrementamos para ir a la fila de abajo
            fila += 2;
            colocarTresUnos(fila, columna);

            // Decrementamos la fila y la columna para ir a la columna izquierda
            colocarUnUno(--fila, columna);

            // Decrementamos la columna para ir a la columna derecha
            columna += 2;
            colocarUnUno(fila, columna);

        // Si está en la columna de la izquierda
        }else if(columna == 0 && fila != 0 && fila != 8) {
            // Decrementamos la fila para ir a la fila de arriba
            colocarDosUnos(--fila, columna);

            // Incrementamos la fila y decrementamos la columna para ir a la fila 
            // de abajo
            fila += 2;
            colocarDosUnos(fila, columna);

            // Decrementamos la fila y la columna para ir a la columna derecha
            colocarUnUno(--fila, ++columna);

        // Si está en la columna de la derecha
        }else if(columna == 8 && fila != 0 && fila != 8) {
            // Decrementamos la fila y la columna para ir a la fila de arriba
            colocarDosUnos(--fila, --columna);

            // Incrementamos la fila y decrementamos la columna para ir a la fila 
            // de abajo
            fila += 2;
            colocarDosUnos(fila, columna);

            // Decrementamos la fila y la columna para ir a la columna de la izquierda
            colocarUnUno(--fila, columna);

        // Si está en la fila de arriba
        }else if(fila == 0 && columna != 0 && columna != 8) {
            // Incrementamos la fila y decrementamos la columna para ir a la fila 
            // de abajo
            colocarTresUnos(++fila, --columna);

            // Decrementamos la fila y la columna para ir a la columna izquierda
            colocarUnUno(--fila, columna);

            // Decrementamos la columna para ir a la columna derecha
            columna += 2;
            colocarUnUno(fila, columna);

        // Si está en la fila de abajo
        }else if(fila == 8 && columna != 0 && columna != 8) {
            // Decrementamos la fila y la columna para ir a la fila de arriba
            colocarTresUnos(--fila, --columna);

            // Incrementamos la fila  y decrementamos la columna para ir a la 
            // columna de la izquierda
            colocarUnUno(++fila, columna);

            // Decrementamos la columna para ir a la columna derecha
            columna += 2;
            colocarUnUno(fila, columna);

        // Si está en la esquina superior izquierda
        }else if(fila == 0 && columna == 0) {
            if(numerosCasillas[fila][columna + 1] != -1) numerosCasillas[fila][columna + 1] += 1;
            if(numerosCasillas[fila + 1][columna + 1] != -1) numerosCasillas[fila + 1][columna + 1] += 1;
            if(numerosCasillas[fila + 1][columna] != -1) numerosCasillas[fila + 1][columna] += 1;

        // Si está en la esquina superior derecha
        }else if(fila == 0 && columna == 8) {
            if(numerosCasillas[fila + 1][columna] != -1) numerosCasillas[fila + 1][columna] += 1;
            if(numerosCasillas[fila + 1][columna - 1] != -1) numerosCasillas[fila + 1][columna - 1] += 1;
            if(numerosCasillas[fila][columna - 1] != -1) numerosCasillas[fila][columna - 1] += 1;

        // Si está en la esquina inferior izquierda
        }else if(fila == 8 && columna == 0) {
            if(numerosCasillas[fila - 1][columna] != -1) numerosCasillas[fila - 1][columna] += 1;
            if(numerosCasillas[fila - 1][columna + 1] != -1) numerosCasillas[fila - 1][columna + 1] += 1;
            if(numerosCasillas[fila][columna + 1] != -1) numerosCasillas[fila][columna + 1] += 1;

        // Si está en la esquina inferior derecha
        }else if(fila == 8 && columna == 8) {
            if(numerosCasillas[fila - 1][columna] != -1) numerosCasillas[fila - 1][columna] += 1;
            if(numerosCasillas[fila][columna - 1] != -1) numerosCasillas[fila][columna - 1] += 1;
            if(numerosCasillas[fila - 1][columna - 1] != -1) numerosCasillas[fila - 1][columna - 1] += 1;
        }
    }

    /**
     * Método que pasada una fila y una columna, suma unos en las tres casillas 
     * siguientes
     * @param fila - Nº de fila
     * @param columna - Nº de columna
     */
    public void colocarTresUnos(int fila, int columna) {
        for (int i = 0; i < 3; i++) {
            if(numerosCasillas[fila][columna] != -1) numerosCasillas[fila][columna]++;
            columna++;
        }
    }

    /**
     * Método que pasada una fila y una columna, suma unos en las dos casillas 
     * siguientes
     * @param fila - Nº de fila
     * @param columna - Nº de columna
     */
    public void colocarDosUnos(int fila, int columna) {
        for (int i = 0; i < 2; i++) {
            if(numerosCasillas[fila][columna] != -1) numerosCasillas[fila][columna]++;
            columna++;
        }
    }

    /**
     * Método que pasada una fila y una columna, suma uno a la casilla correspondiente
     * @param fila - Nº de fila
     * @param columna - Nº de columna
     */
    public void colocarUnUno(int fila, int columna) {
        if(numerosCasillas[fila][columna] != -1) numerosCasillas[fila][columna]++;
    }

    /**
     * Método que destapa el tablero entero
     */
    public void destapar() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Si no es una bomba destapa la casilla
                if(numerosCasillas[i][j] != -1) {
                    destaparCasilla(i, j);
                // Si ha explotado coloca la explosion
                }else if(c[i][j].isExplotada()){
                    c[i][j].colocarImagenExplosion();
                    estadoCasillas[i][j] = 1;
                // Sino coloca la mina
                }else {
                    c[i][j].colocarImagenMina();
                    estadoCasillas[i][j] = 1;
                }
            }
        }
        editable = false;
    }

    /**
     * Método que tapa el tablero en su totalidad
     */
    public void tapar() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                c[i][j].quitarImagen();
                estadoBanderas[i][j] = 0;
                estadoCasillas[i][j] = 0;
                numerosCasillas[i][j] = 0;
            }
        }
        editable = true;
    }

    /**
     * Método que tapa el tablero en su totalidad cuando reiniciamos la partida
     */
    public void taparReinicio() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                c[i][j].quitarImagen();
                estadoBanderas[i][j] = 0;
                estadoCasillas[i][j] = 0;
                c[i][j].setExplotada(false);
            }
        }
        editable = true;
    }

    /**
     * Método que destapa la casilla correspondiente segun las coordenadas pasadas 
     * por parámetro. Según que número tenga en el numeroCasillas[][], 
     * mostrará la imágen de un número u otro.
     * @param x - Coordenada X
     * @param y - Coordenada Y
     */
    public void destaparCasilla(int x, int y) {
        c[x][y].colocarImagenNumero(numerosCasillas[x][y]);
        estadoCasillas[x][y] = 1;
    }

    /**
     * Método que pasados tres matrices de enteros por parámetro, actualiza los 
     * atributos numeroCasillas, estadoCasillas
     * y estadoBanderas.
     * @param numeros - Matriz de números
     * @param estados - Matriz de estados de las casillas
     * @param banderas - Matriz de banderas
     */
    public void cargarTablero(int[][] numeros, int[][] estados, int[][] banderas) {
        tapar();

        numerosCasillas = numeros;
        estadoCasillas = estados;
        estadoBanderas = banderas;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(estadoCasillas[i][j] == 1) {
                    c[i][j].colocarImagenNumero(numerosCasillas[i][j]);
                }
                if(estadoBanderas[i][j] == 1) {
                    c[i][j].colocarImagenBandera();
                }
            }
        }

        editable = true;
    }

    /**
     * Método que pasadas la fila y la columna de la casilla, coloca la imagen 
     * de la explosion
     * @param fila - Nº de fila
     * @param columna Nº de columna
     */
    public void colocarExplosion(int fila, int columna) {
        c[fila][columna].colocarImagenExplosion();
    }

    /**
     * Método que pasadas la fila y la columna de la casilla, coloca la imagen 
     * de la bandera
     * @param fila - Nº de fila
     * @param columna Nº de columna
     */
    public void colocarBandera(int fila, int columna) {
        estadoBanderas[fila][columna] = 1;
        c[fila][columna].colocarImagenBandera();
    }

    /**
     * Método que pasadas la fila y la columna de la casilla, quita la imágen 
     * de la bandera
     * @param fila - Nº de fila
     * @param columna Nº de columna
     */
    public void quitarBandera(int fila, int columna) {
        estadoBanderas[fila][columna] = 0;
        c[fila][columna].quitarImagenBandera();
    }

    /**
     * Método que pasadas la fila y la columnad de una casilla, devuelve si esta 
     * está tapada o no
     * @param fila - Nº fila
     * @param columna - Nº de columna
     * @return
     */
    public boolean isTapada(int fila, int columna) {
        return estadoCasillas[fila][columna] == 0;
    }

    /**
     * Método que pasadas la fila y la columna de la casilla, verifica si hay o 
     * no una bandera
     * @param fila - Nº de fila
     * @param columna - Nº de columna
     * @return
     */
    public boolean hayBandera(int fila, int columna) {
        return estadoBanderas[fila][columna] == 1;
    }

    /**
     * Método que pasadas la fila y la columna de la casilla, verifica si hay o 
     * no una mina
     * @param fila - Nº de fila
     * @param columna - Nº de columna
     * @return
     */
    public boolean hayMina(int fila, int columna) {
        return numerosCasillas[fila][columna] == -1;
    }

    // GETTERS Y SETTERS

    /**
     * Método que devuelve el valor del atriubuto numerosCasillas
     * @return
     */
    public int[][] getNumerosCasillas() {
        return numerosCasillas;
    }

    /**
     * Método que devuelve el atributo estadoCasillas
     * @return
     */
    public int[][] getEstadoCasillas() {
        return estadoCasillas;
    }

    /**
     * Método que devuelve el atributo estadoBanderas
     * @return
     */
    public int[][] getEstadoBanderas() {
        return estadoBanderas;
    }

    /**
     * Método que devuelve el valor del atrivuto editable
     * @return
     */
    public boolean isEditable() {
        return editable;
    }
}
