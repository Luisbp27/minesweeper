import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
@SuppressWarnings("serial")

public class Buscaminas extends JFrame {

    // Dimensiones ventana
    private static final int ANCHURA_VENTANA = 310;
    private static final int ALTURA_VENTANA = 360;
    // Dimensiones tablero
    private static final int ANCHURA_TABLERO = 300;
    private static final int ALTURA_TABLERO = 300;

    // Declaración e instnciación de los dos JMenu que tendrá el JMenuBar
    private static final JMenu fichero = new JMenu();
    private static final JMenu acciones = new JMenu();
    // Declaración e instanciación de los items de nuestra barra de menu
    private static final JMenuItem abrir = new JMenuItem();
    private static final JMenuItem guardar = new JMenuItem();
    private static final JMenuItem nuevoJuego = new JMenuItem();
    private static final JMenuItem reiniciarJuego = new JMenuItem();
    private static final JMenuItem resolver = new JMenuItem();
    // Declaración de un JFileChooser y un File para abrir y guardar partidas
    private final JFileChooser seleccion;
    private File ficheroSeleccion;
    // Deaclaración de dos variables para gestionar la entrada y salida de datos
    // cuando guardamos y abrimos una partida
    private TableroIn tableroIn;
    private TableroOut tableroOut;

    // Variable entera para contar las casillas destapadas y saber si el usuario
    // gana o no la partida
    public static int tirada;

    /**
     * Constructor de la clase
     */
    public Buscaminas() {
        // Establecemos el título, ubicación y dimensiones de la ventana
        super("Juego del Buscaminas");
        this.setSize(ANCHURA_VENTANA, ALTURA_VENTANA);
        this.setLocationRelativeTo(null);

        // Objeto Tablero que nos permitirá cargar objetos Tablero al abrir una partida
        Tablero tablero = new Tablero();
        JMenuBar barraMenu = new JMenuBar();

        // Establecemos el tamaño del panel tablero
        tablero.setBounds(0, 0, ANCHURA_TABLERO, ALTURA_TABLERO);

        // Añadimos el panel al JFrame Buscaminas
        this.getContentPane().setLayout(null);
        this.getContentPane().add(tablero);

        // Establecemos el número de tiradas a 0
        tirada = 0;

        // Instanciamos y configuramos el JFileChooser
        seleccion = new JFileChooser();
        seleccion.setAcceptAllFileFilterUsed(false);
        seleccion.addChoosableFileFilter(new FileNameExtensionFilter("DAT", "dat"));

        // Configuramos que hace cada item del menu
        abrir.setText("Abrir juego");
        abrir.addActionListener((ActionEvent e) -> {
            try{
                if (seleccion.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    ficheroSeleccion = seleccion.getSelectedFile();
                    if (ficheroSeleccion.canRead()) {
                        // Creamos el enlace con el fichero y cargamos el tablero
                        tableroIn = new TableroIn(ficheroSeleccion);
                        tableroIn.abrirTablero(tablero);
                        tableroIn.close();
                    }
                }
            }catch(HeadlessException | IOException | ClassNotFoundException error) {
                System.out.println("ERROR: " + error.getMessage());
            }
        });

        guardar.setText("Guardar juego");
        guardar.addActionListener((ActionEvent e) -> {
            try{
                if (seleccion.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    ficheroSeleccion = new File(seleccion.getSelectedFile() + ".dat");
                    if (ficheroSeleccion.getName().endsWith("dat")) {
                        // Creamos el enlace con el fichero y guardamos el tablero
                        tableroOut = new TableroOut(ficheroSeleccion);
                        tableroOut.guardarTablero(tablero);
                        tableroOut.close();
                    }
                }
            }catch(HeadlessException | IOException error) {
                System.out.println("ERROR: " + error.getMessage());
            }
        });

        nuevoJuego.setText("Nuevo juego");
        nuevoJuego.addActionListener((ActionEvent e) -> {
            tirada = 0;
            tablero.tapar();
            tablero.colocarMinas();
        });

        reiniciarJuego.setText("Reiniciar juego");
        reiniciarJuego.addActionListener((ActionEvent e) -> {
            tirada = 0;
            tablero.taparReinicio();
        });

        resolver.setText("Resolver juego");
        resolver.addActionListener((ActionEvent e) -> {
            // Destapamos el tablero y por tanto resolvemos el juego
            tablero.destapar();
        });

        // Asignamos el nombre al primer menu desplegable y le añadimos sus
        // correspondientes items
        fichero.setText("Fichero");
        fichero.add(abrir);
        fichero.add(guardar);
        // Asignamos el nombre al segundo menu desplegable y le añadimos sus
        // correspondientes items
        acciones.setText("Acciones");
        acciones.add(nuevoJuego);
        acciones.add(reiniciarJuego);
        acciones.add(resolver);

        // Añadimos los JMenu a la barra de menu
        barraMenu.add(fichero);
        barraMenu.add(acciones);

        this.setJMenuBar(barraMenu);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Si no ha ganado y el tablero se puede editar
                if(tirada != 71 && tablero.isEditable()) {
                    // Hay que corregir las coordenadas obtenidas del mouse ya
                    // que hay que tener en cuenta los márgenes, la barra de menu
                    // y la propia cabezera de la ventana
                    if ((e.getX() < ANCHURA_TABLERO + 7) && (e.getY() < ALTURA_TABLERO + 45)) {
                        int fila = (e.getY() - 45) / 33;
                        int columna = (e.getX() - 7) / 33;

                        if(e.getButton() == MouseEvent.BUTTON1) {
                            if (tablero.isTapada(fila, columna) && !tablero.hayBandera(fila, columna)) {
                                // Si hay una mina, destapa el tablero y visualiza
                                // el mensaje
                                if (tablero.hayMina(fila, columna)) {
                                    tablero.destapar();
                                    tablero.colocarExplosion(fila, columna);

                                    JOptionPane.showMessageDialog(null, "Has perdido la partida!");

                                    // Reseteamos las tiradas
                                    tirada = 0;

                                    // Sino, destapa la casilla
                                } else {
                                    tablero.destaparCasilla(fila, columna);
                                    tirada++;
                                }
                            }

                        }else if(e.getButton() == MouseEvent.BUTTON3) {
                            if(tablero.isTapada(fila, columna) && !tablero.hayBandera(fila, columna)) {
                                tablero.colocarBandera(fila, columna);
                            }else if(tablero.hayBandera(fila, columna)) {
                                tablero.quitarBandera(fila, columna);
                            }
                        }
                    }
                }

                // Si se cumple, el jugador ha ganado y se visualiza el mensaje
                if(tirada == 71) {
                    JOptionPane.showMessageDialog(null, "Has ganado la partida!");

                    // A continuación se destapa el tablero entero
                    tablero.destapar();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        // Prohibe que la ventana sea redimensionada
        this.setResizable(false);

        // Añadimos una confirmación para salir de la ventana
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(Buscaminas.this,
                        "¿Estas seguro de que quieres cerrar la ventana?", "Cerrar ventana",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Main principal
     * 
     * @param args - String
     */
    public static void main(String[] args){
        // Este try catch permite que independientemente del sistema operativo
        // donde se ejecute el programa, su visualización sea correcta
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        new Buscaminas().setVisible(true);
    }
}
