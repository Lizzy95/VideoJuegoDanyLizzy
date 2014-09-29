/**
 * JFrameBreaking
 *
 * Personaje para juego previo Examen
 *
 * @author Norma Elizabeth Morales Cruz
 * @author Daniela Reyes 
 * @version 1.00 2008/6/13
 */
 
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JFrameBreaking extends JFrame implements Runnable, KeyListener {

    /* objetos para manejar el buffer del Applet y este no parpadee */
    private int iVidas; // variable para manejrar las vidas
    private int iDireccion;  // indica la direccion de Nena
    private int iScore; // score del juego
    private int iColisiones; // contador de las colisiones
    private int iCorre; // numero aletorio de corredores
    private int iCamina;  //numero aletorio de caminadores
    private Image    imaImagenJFrame;   // Imagen a proyectar en Applet	
    private Image imaGameOver; // imagen del fin del juego
    private Image imaFondo; // imagen de fondo
    private Image imaPausa; // imagen al pausar el juego
    private Graphics graGraficaJFrame;  // Objeto grafico de la Imagen
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Personaje perBolita; // objeto de la clase personaje
    private Personaje perSombrero; // objeto de la clase personaje
    private Personaje perMentafetamina; // objeto de la clase personaje
    private LinkedList objCaminadores; //objetos de la linkedlist de caminadores
    private LinkedList objCorredores; // objetos de la linkedlist de corredores
    private int iVelocidad; // velocidad de corre
    private SoundClip souCamina; // sonido alegre
    private SoundClip souCorre; //sonido triste de cuando choca
    private String strNombreArchivo; // Nombre del archivo
    private String strNombreJugador; // nombre del jugador
    private String[] strArr; // arreglo del archivo dividido
    private boolean booPausa; // booleana para activar pausar
    private boolean booGuardar; //booleana para activar guardad
    private boolean booSpace; // booleana para activar que se valla la bolita
    private JList jliScore; //Lista para desplegar el puntaje
    private long lonTiempoActual; //Tiempo de control de la animacion
    
   /** 
     * AppletExamen
     * 
     * Metodo constructor que llama a las clases init y start para que pueda
     * correr el juego
     * 
     */ 
    public JFrameBreaking() {
        setSize(800, 600); //define tamaño del JFrame
       init();
       start();
     
   }
	
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        
        // se inicializa la pausa en falso
        booPausa = false;
        
        //se inicializa guardar en false
        booGuardar = false;
        
        // se inicializa space en false
        booSpace = false;
        
        // se da el nombre del archivo
        strNombreArchivo = "Puntaje.txt";
        
        // incializo las vidas del juego
        iVidas = 8;//((int) (Math.random()* 3)) + 3;
        
        // inicializa la direccion en 0
        iDireccion = 0;
        
        // incializa velocidad en 1
        iVelocidad = 2;
        
        // incializa el score en 0
        iScore = 0;
        
        // incializa las colisiones en 0
        iColisiones = 0;

        // se crea el sombrero
        URL urlImagenSombrero = this.getClass().getResource("sombrero2.png");
        
        // posiciones del sombrero
        int posX = (getWidth()/2);
        int posY = (getHeight());
        
        // se crea el sombrero
        perSombrero = new Personaje(posX,posY,
                    Toolkit.getDefaultToolkit().getImage(urlImagenSombrero));
        
        perSombrero.setX(posX-perSombrero.getAncho()/2);
        perSombrero.setY(posY-perSombrero.getAlto());
 
                
                
        // se crea la changuita nena 
        int posXBol = (getWidth() / 2);
        int posYBol = (getHeight()-perSombrero.getAlto());
        
 	// se crea imagen de pelotita
        URL urlImagenNemo = this.getClass().getResource("pelotita.png");
        
        // se crea a Nena 
	perBolita = new Personaje(posXBol,posYBol,
                Toolkit.getDefaultToolkit().getImage(urlImagenNemo));

        perBolita.setX(posXBol-perBolita.getAncho()/2);
        perBolita.setY(posYBol-perBolita.getAlto()/2);
        
        int posXMen = (getWidth()/2);
        int posYMen = (getHeight()/2);
        
        URL urlImagenMentafetamina = this.getClass().getResource("mentafetamina.png");
        perMentafetamina = new Personaje( posXMen, posYMen,
                        Toolkit.getDefaultToolkit().getImage(urlImagenMentafetamina));
        perMentafetamina.setX(posXMen - perMentafetamina.getAncho()/2);
        perMentafetamina.setY(posYMen - perMentafetamina.getAlto()/2);
        
        addKeyListener(this);
           
    }
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        
        // se realiza el ciclo del juego en este caso nunca termina
        while (iVidas > 0) {
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */ 
            if(!booPausa){
                actualiza();
                checaColision();
                repaint();
            }
            
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError)	{
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }    
            
            if(booGuardar){
                booGuardar = false;
                booPausa = false; 
            }
	}
          
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto nena, camina y corredor
     * 
     */
    public synchronized void actualiza(){
        //Dependiendo de la iDireccion de nena es a donde se movera
        switch(iDireccion) {
            case 1: { //se mueve hacia izquierda
                perSombrero.setX(perSombrero.getX() - 2);
                if (!booSpace) {
                    perBolita.setX(perBolita.getX()-2);
                }
                break;    
            }
            case 2: { //se mueve hacia derecha
                perSombrero.setX(perSombrero.getX() + 2);
                if (!booSpace) {
                    perBolita.setX(perBolita.getX()+2);
                }
                break;    	
            }
            case 3: {// se deja de mover
                perSombrero.setX(perSombrero.getX());
                if (!booSpace) {
                    perBolita.setX(perBolita.getX());
                }
                break;
            }
        }
        
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto nena, camina y corre
     * con las orillas del <code>Applet</code>.
     * 
     */
    public synchronized void checaColision(){
        //Colision de Nena con el Applet dependiendo a donde se mueve.
        switch(iDireccion){
            case 1: { // si se mueve hacia arriba 
                if(perBolita.getY() < 0) { // y esta pasando el limite
                    // se queda en su lugar sin salirse del applet
                    perBolita.setX(perBolita.getX()); 
                    perBolita.setY(0);
                }
                break;    	
            }     
            case 2: { // si se mueve hacia abajo
                // y se esta saliendo del applet
                if(perBolita.getY() + perBolita.getAlto() > getHeight()) {
                    // se queda en su lugar sin salirse del applet
                    perBolita.setX(perBolita.getX()); 
                    perBolita.setY(getHeight()-perBolita.getAlto());
                  
                }
                break;    	
            } 
            case 3: { // si se mueve hacia izquierda 
                if(perBolita.getX() < 0) { // y se sale del applet
                    // se queda en su lugar sin salirse del applet
                    perBolita.setX(0); 
                    perBolita.setY((perBolita.getY()));
              
                }
                break;    	
            }    
            case 4: { // si se mueve hacia derecha 
                // si se esta saliendo del applet
                if(perBolita.getX() + perBolita.getAncho() > getWidth()) { 
                    // se queda en su lugar sin salirse del applet
                    perBolita.setX(getWidth() - perBolita.getAncho()); 
                    perBolita.setY((perBolita.getY()));
          
                }
                break;    	
            }			
        }
    }
 /**
     * reposiciona 
     * 
     * cambia las posiciones de camina al origen, arriba y afuera del
     * applet
     * 
     */

    public void reposiciona(Personaje perPildoraR) {
        // indica las nuevas posiciones de la galleta
        perPildoraR.setX(0-100);    
        perPildoraR.setY((int) (Math.random() * (getHeight()))); 
    }
    
   
     /**
     * keyPressed
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al dejar presionada
     * alguna tecla.
     * @param keyEvent es el <code>evento</code> generado al presionar.
     * 
     */
    public void keyPressed(KeyEvent keyEvent) {
        // no hay codigo pero se debe escribir el metodo
       if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {    
                iDireccion = 1;   // cambio la direccion a la izquierda
        }
        // si presiono la tecla D
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){    
                iDireccion = 2;   // cambio la direccion a la derecha
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            booSpace = true;
        }
 
    }
    
    /**
     * keyTyped
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una 
     * tecla que no es de accion.
     * @param e es el <code>evento</code> que se genera en al presionar.
     * 
     */
    public void keyTyped(KeyEvent e){
    	// no hay codigo pero se debe escribir el metodo
    }
    
    /**
     * keyReleased
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla.
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent keyEvent){
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {    
                iDireccion = 3;   // cambio la direccion a la izquierda
        }
        // si presiono la tecla D
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){    
                iDireccion = 3;   // cambio la direccion a la derecha
        }
    	if(keyEvent.getKeyCode() == KeyEvent.VK_P){
              booPausa=!booPausa;
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_C){
            try {
                leeArchivo();
                
            } catch (IOException ex) {
                Logger.getLogger(JFrameBreaking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // si se presiona la g se guarda el juego
        else if(keyEvent.getKeyCode() == KeyEvent.VK_G){
            if(!booGuardar){
            grabaArchivo();
            }
        }
        repaint();
    }
    
     /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenJFrame == null){
                imaImagenJFrame = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaJFrame = imaImagenJFrame.getGraphics ();
        }

        // creo imagen para el background
        URL urlImagenFondo = this.getClass().getResource("back_juego.jpg");
        Image imaImagenEspacio = Toolkit.getDefaultToolkit().
                                                getImage(urlImagenFondo);

        // Despliego la imagen
        graGraficaJFrame.drawImage(imaImagenEspacio, 0, 0, 
                getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaJFrame.setColor (getForeground());
        paint1(graGraficaJFrame);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenJFrame, 0, 0, this);
    }
    
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics g) {
        if (iVidas <= 0) { 
            // creo imagen para el background
            URL urlImagenFondo = this.getClass().getResource("game_over.jpg");
                Image imaImagenJuego = Toolkit.getDefaultToolkit().
                                        getImage(urlImagenFondo);

            // Despliego la imagen
            graGraficaJFrame.drawImage(imaImagenJuego, 0, 0, 
                    getWidth(), getHeight(), this);
        }
        else {
            g.setColor(Color.red);
            
            //se despliegan las vidas
            g.drawString("Vidas: "+ Integer.toString(iVidas), 40, 35);
            
            // se despliega el score
            g.drawString("Score: " + Integer.toString(iScore), 95, 35);
            
            // desplegar imagenes
            if (perBolita != null && perSombrero != null && perMentafetamina != null) {
                //Dibuja la imagen de Nena en la posicion actualizada
                g.drawImage(perBolita.getImagen(), perBolita.getX(),
                        perBolita.getY(), this);
                //Dibuja el sombrero
                g.drawImage(perSombrero.getImagen(), perSombrero.getX(),
                        perSombrero.getY(), this);
                //Dibuja la mentafetamina
                g.drawImage(perMentafetamina.getImagen(), perMentafetamina.getX(),
                        perMentafetamina.getY(), this);
             

            }
        }
    }
    
    /**
     * leeArchivo
     * 
     * Metodo sobrescrito de la clase Archivo
     * 
     * En este metodo se lee el archivo
     */
    public  void leeArchivo() throws IOException {
        BufferedReader fileIn;
        objCaminadores.clear();
        objCorredores.clear();
        try {
            fileIn = new BufferedReader(new FileReader(strNombreArchivo));
        } catch (FileNotFoundException e){
            File puntos = new File(strNombreArchivo);
            PrintWriter fileOut = new PrintWriter(puntos);
            fileOut.println("");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(strNombreArchivo));
        }
        String dato = fileIn.readLine();
        strArr = dato.split(",");
        /* lee cada una de las variables del arreglo y se las asigna 
        * score, vidas, posicion nena, camina, corre, colisiones y
        *velocidad segun corresponda
        */
        iScore = (Integer.parseInt(strArr[0]));
        iVidas = (Integer.parseInt(strArr[1]));
        perBolita.setX((Integer.parseInt(strArr[2])));
        perBolita.setY((Integer.parseInt(strArr[3])));
        iCamina = (Integer.parseInt(strArr[4]));
        iCorre = (Integer.parseInt(strArr[5]));
        iColisiones = (Integer.parseInt(strArr[6]));
        iVelocidad = (Integer.parseInt(strArr[7]));
        // lee otra linea del archivo
        dato = fileIn.readLine();
        URL urlImagenCamina = this.getClass().getResource("alien1Camina.gif");
               
        for ( int i = 0; i <= iCamina; i++) {
         // se posiciona a Susy en alguna parte al azar del cuadrante
            strArr = dato.split(",");  
            int iPosX = (Integer.parseInt(strArr[0]));
            int iPosY = (Integer.parseInt(strArr[1]));
            // se crea el objeto camina
            Personaje perPildoraR = new Personaje(iPosX, iPosY,
                        Toolkit.getDefaultToolkit().getImage(urlImagenCamina));
            objCaminadores.add(perPildoraR);  
                    // lee otra linea del archivo
            dato = fileIn.readLine();
        }
        
        URL urlImagenCorre = this.getClass().getResource("alien2Corre.gif");
        for (int i = 0; i <= iCorre; i++) {
            // se posiciona a Susy en alguna parte al azar del cuadrante
            int iPosCorreX = (Integer.parseInt(strArr[0]));
            int iPosCorreY = (Integer.parseInt(strArr[1]));
            // se crea el objeto camina
            Personaje perPildoraA = new Personaje(iPosCorreX,iPosCorreY ,
                    Toolkit.getDefaultToolkit().getImage(urlImagenCorre));
            objCorredores.add(perPildoraA);  
            // lee otra linea del archivo
            dato = fileIn.readLine();
            strArr = dato.split(",");
        }
        fileIn.close();
    }

    /**
     * grabaArchivo
     * 
     * Metodo sobrescrito de la clase Archivo
     * 
     * En este metodo se graba el archivo
     */
    public void grabaArchivo() {
        try{
            PrintWriter fileOut = new PrintWriter(new 
                                        FileWriter(strNombreArchivo));
            // graba en el archivo la informacion del juego
            fileOut.println(iScore+ ","+ iVidas+ ","+ perBolita.getX()
                    + ","+ perBolita.getY()+ ","+ iCamina+ ","+ iCorre+ ","
                                                +iColisiones+ ","+iVelocidad);
            // graba en el archivo la  posicion de caminadores
            for( Object objCamina :objCaminadores) {
                Personaje perPildoraR = (Personaje) objCamina;
                fileOut.println(perPildoraR.getX() + ","+perPildoraR.getY());
                
            }
            //graba en el archivo la poicion de corredores
            for( Object objCorre :objCorredores){
                Personaje perPildoraA = (Personaje) objCorre;
                //Dibuja la imagen de corre en la posicion actualizada
                fileOut.println(perPildoraA.getX() +"," + perPildoraA.getY());
            }
                fileOut.close();
            } catch (IOException ex){
                System.out.println("Error en "+ ex.toString());
            }
    }  
}
