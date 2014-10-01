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
    private int iColRoja; // contador de las colisiones
    private int iColAzul; // contador de colisones azul
    private int iColision;
    private int iColMenta; // contador de colisiones de mentafetamina
    private int iPosXBolita;
    private int iPosYBolita;
    private Image imaImagenJFrame;   // Imagen a proyectar en Applet	
    private Image imaGameOver; // imagen del fin del juego
    private Image imaFondo; // imagen de fondo
    private Image imaPausa; // imagen al pausar el juego
    private Graphics graGraficaJFrame;  // Objeto grafico de la Imagen
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Personaje perBolita; // objeto de la clase personaje
    private Personaje perSombrero; // objeto de la clase personaje
    private Personaje perMentafetamina; // objeto de la clase personaje
    private Personaje perVidas; // objeto de la clase personaje de vidas
    private LinkedList objPildoraRoja; //objetos de la linkedlist de caminadores
    private LinkedList objPildoraAzul; // objetos de la linkedlist de corredores
    private int iVelocidad; // velocidad de corre
    private SoundClip souFondo; // sonido alegre
    private SoundClip souFinal; //sonido triste de cuando choca
    private String strNombreArchivo; // Nombre del archivo
    private String strNombreJugador; // nombre del jugador
    private String[] strArr; // arreglo del archivo dividido
    private boolean booPausa; // booleana para activar pausar
    private boolean booAyuda; // booleano para ctivar la ayuda
    private boolean booGuardar; //booleana para activar guardad
    private boolean booSpace; // booleana para activar que se valla la bolita
    private boolean booPlay; // Control de la 
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
        
        // se incializa la ayuda en true para que se muestre la informacion
        booAyuda = true;
             
        // se da el nombre del archivo
        strNombreArchivo = "Inicio.txt";
        
        
        // incializo las vidas del juego
        iVidas = 8;//((int) (Math.random()* 3)) + 3;
        
        // inicializa la direccion en 0
        iDireccion = 0;
        
        // incializa velocidad en 1
        iVelocidad = 2;
        
        // incializa el score en 0
        iScore = 0;
        
        // incializa las colisiones en 0
        iColRoja = 0;
        
        //inicializa las colisiones en 0
        iColAzul = 0;
        
        // inicializa las colision 1
        iColision = 1;
        
        // inicializa el contador de colisiones de mentafetamina en 0
        iColMenta = 0;

        // se crea el sombrero
        URL urlImagenSombrero = this.getClass().getResource("sombrero3.png");
        
        // posiciones del sombrero
        int posX = (getWidth()/2);
        int posY = (getHeight());
        
        // se crea el sombrero
        perSombrero = new Personaje(posX,posY,
                    Toolkit.getDefaultToolkit().getImage(urlImagenSombrero));
        
        perSombrero.setX(posX-perSombrero.getAncho()/2);
        perSombrero.setY(posY-perSombrero.getAlto());
 
        perSombrero.setVelocidad(4);
                
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
        
        perBolita.setVelocidad(4);
        
        int posXMen = (getWidth()/2);
        int posYMen = 150;
        
        URL urlImagenMentafetamina = this.getClass().getResource("mentafetamina.png");
        perMentafetamina = new Personaje( posXMen, posYMen,
                        Toolkit.getDefaultToolkit().getImage(urlImagenMentafetamina));
        perMentafetamina.setX(posXMen - perMentafetamina.getAncho()/2);
        perMentafetamina.setY(posYMen - perMentafetamina.getAlto()/2);
        
        int posXPil = 40;
        int posYPil = 190;
        
        
        // se crea la lista de pildora roja
        objPildoraRoja = new LinkedList();
        
        // inicializa la lista de pildora1
        for ( int i = 0; i < 9; i++) {
            // se posiciona la pildora 1 en la tabla periodica
            for (int j = 0; j < 5; j++) {
                URL urlImagenPildora = this.getClass().getResource("pildora1.png");
                Personaje perPildora1 = new Personaje(posXPil, posYPil,
                        Toolkit.getDefaultToolkit().getImage(urlImagenPildora));
                objPildoraRoja.add(perPildora1);
                posYPil= posYPil+40;
            }    
            posXPil= posXPil +40;
            posYPil= 190;
        }
        
        int posXPil2 = 410;
        int posYPil2 = 190;
         // se crea la lista de pildora roja
        objPildoraAzul = new LinkedList();
        
        // inicializa la lista de pildora2
        for ( int i = 0; i < 9; i++) {
            // se posiciona la pildora 2 en la tabla periodica
            for (int j = 0; j < 5; j++) {
                URL urlImagenPildora2 = this.getClass().getResource("pildora2.png");
                Personaje perPildora2 = new Personaje(posXPil2, posYPil2,
                        Toolkit.getDefaultToolkit().getImage(urlImagenPildora2));
                objPildoraAzul.add(perPildora2);
                posYPil2= posYPil2+40;
            }    
            posXPil2= posXPil2 +40;
            posYPil2= 190;
        }
        
        // se crea la imagend e las vidas
        int posXVidas = (int) (Math.random() * (getWidth()- 60));    
        int posYVidas = (int) (Math.random() * (getHeight()/2))*(-1);  
        URL urlImagenVidas = this.getClass().getResource("vidas.png");
        perVidas = new Personaje(posXVidas, posYVidas,
                        Toolkit.getDefaultToolkit().getImage(urlImagenVidas));
        
        
        // establece la velocidad en la que caera la botella
        perVidas.setVelocidad(4);        
        
        souFondo = new SoundClip("Tema.wav");
        souFinal = new SoundClip("Final.wav");
        
        souFondo.setLooping(true);
        souFondo.play();
        
        grabaArchivo();
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
            if(!booPausa && !booAyuda){
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
        if (booSpace) {
            switch(iColision){
                case 1: { // si se mueve hacia arriba 
                    perBolita.setX(perBolita.getX()+perBolita.getVelocidad());
                    perBolita.setY(perBolita.getY()-perBolita.getVelocidad());
                    break;    	
                }     
                case 2: { // si se mueve hacia abajo y sale de la ventana
                    // del cuadrante 3             
                        perBolita.setX(perBolita.getX()-perBolita.getVelocidad());
                        perBolita.setY(perBolita.getY()+ perBolita.getVelocidad());
                    // se queda en su lugar sin salirse del applet
  
                break;    	
                } 
                case 3: {// si se mueve hacia abajo cuadrante 4
                    perBolita.setY(perBolita.getY()+ perBolita.getVelocidad());
                    perBolita.setX(perBolita.getX()+ perBolita.getVelocidad());
                    
                    break;
                }    
                case 4: { // si se hacia arriba cuadrante 2
                        perBolita.setX(perBolita.getX()-perBolita.getVelocidad());
                        perBolita.setY(perBolita.getY()- perBolita.getVelocidad());                  
                    break;    	
                }
            }   
        }
        
        if(iVidas <= 4){
            perVidas.setY(perVidas.getY() + perVidas.getVelocidad());
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
            // si se mueve hacia arriba 
                if(perBolita.getY() < 0) { // y esta pasando el limite
                    
                    // se queda en su lugar sin salirse del applet                  
                    if(perBolita.getX() <= (getWidth()/2)) {
                        iColision = 3;                     
                    }
                    else
                    {
                        iColision = 2;
                    } 
                }
               // si se mueve hacia abajo
                // y se esta saliendo del applet
                else if(perBolita.getY() + perBolita.getAlto() > getHeight()) {
                    // se queda en su lugar sin salirse del applet
                    booSpace = false;
                    iVidas--;
                    reposiciona();               
                }
                
                else if(perBolita.getX() < 0) { // y se sale del applet
                    // se queda en su lugar sin salirse del applet
                    if(iColision == 2){                    
                         iColision = 3;
                    }
                    else if(iColision == 3 ){  
                        iColision = 2;
                    }
                    else if(iColision == 1) {
                        iColision = 4;
                    }
                    else if(iColision == 4){
                        iColision = 1;
                    }
                    
              
                }
               
                // si se esta saliendo del applet
                else if(perBolita.getX() + perBolita.getAncho() > getWidth()) { 
                    // se queda en su lugar sin salirse del applet
                    if(iColision== 2){
                         iColision = 3;
                    }
                    else if(iColision == 3 ){
                        iColision = 2;
                    }
                    else if(iColision == 1) {
                        iColision = 4;
                    }
                    else if(iColision == 4){
                        iColision = 1;
                    }
                }
                else if(perBolita.colisiona(perSombrero)){
                   
                    if(perBolita.getX() <= (perSombrero.getX()+ (perSombrero.getAncho()/2))){
                        
                        iColision = 4;
                    }
                    else
                    {  
                        iColision=1;
                    }        
                }
                
                
                
                switch(iDireccion){
                    case 1: { // si se mueve hacia izquierda
                        if(perSombrero.getX() < 0) { // y se sale del applet
                    // se queda en su lugar sin salirse del applet
                            perSombrero.setX(0); 
                            perSombrero.setY((perSombrero.getY()));
                            if(!booSpace) {
                                perBolita.setX((perSombrero.getX()+(perSombrero.getAncho()/2)) - (perBolita.getAncho()/2));
                            }
                        }
                    break;    	
                    }    
                    case 2: { // si se mueve hacia derecha 
                            // si se esta saliendo del applet
                        if(perSombrero.getX() + perSombrero.getAncho() > getWidth()) { 
                             // se queda en su lugar sin salirse del applet
                            perSombrero.setX(getWidth() - perSombrero.getAncho()); 
                            perSombrero.setY((perSombrero.getY()));
                            if(!booSpace) {
                                perBolita.setX((perSombrero.getX()+(perSombrero.getAncho()/2)) - (perBolita.getAncho()/2));
                            }
                            
                        }
                    break;    	
                    }			
                }
                
                for(Object objRoja :objPildoraRoja){
                    Personaje perPildora1 = (Personaje) objRoja; 
                    if(perPildora1.colisiona(perBolita)){
                        iScore++;
                        if(iColision== 1){
                            iColision = 2;
                        }
                        else if(iColision == 4 ){
                            iColision = 3;
                        }
                        else if(iColision == 3) {
                            iColision = 4;
                        }
                        else if(iColision == 2){
                            iColision = 1;
                        }
                        perPildora1.setX(-getWidth());
                        perPildora1.setY(-(getHeight()/2));
                    }
                }
                for(Object objAzul :objPildoraAzul){
                    Personaje perPildora2 = (Personaje) objAzul; 
                    if(perPildora2.colisiona(perBolita)){
                        
                        iColAzul++;
                        if (iColAzul == 2) {
                            iScore=iScore+2;
                            perPildora2.setX(-getWidth());
                            perPildora2.setY(-(getHeight()/2));
                            iColAzul=0;
                        }
                        else{
                            if(iColision== 1){
                                iColision = 2;
                            }
                            else if(iColision == 4 ){
                                iColision = 3;
                            }
                            else if(iColision == 3) {
                                iColision = 4;
                            }
                            else if(iColision == 2){
                                iColision = 1;
                            }
                        }
                        
                        
                    }
                }
                
                if (perMentafetamina.colisiona(perBolita)) {
                    iColMenta++;
                    if (iColMenta == 5) {
                        perMentafetamina.setX(-getWidth()/2);
                        perMentafetamina.setY(-getHeight()/2);
                    }
                    else{
                        
                        if(iColision== 1){
                            iColision = 2;
                        }
                        else if(iColision == 4 ){
                            iColision = 3;
                        }
                        else if(iColision == 3) {
                            iColision = 4;
                        }
                        else if(iColision == 2){
                           iColision = 1;
                        }
                    }
                }
                if(perVidas.colisiona(perSombrero)){
                    iVidas++;
                }
    }
 /**
     * reposiciona 
     * 
     * cambia las posiciones de camina al origen, arriba y afuera del
     * applet
     * 
     */

    public void reposiciona() {
        // indica las nuevas posiciones de la galleta
        perSombrero.setX((getWidth()/2) - (perSombrero.getAncho()/2));
        perSombrero.setY((getHeight())- (perSombrero.getAlto()));
        perBolita.setX((getWidth()/2)- (perBolita.getAncho()/2));
        perBolita.setY((getHeight()-perSombrero.getAlto())-(perBolita.getAlto()));
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
            // se da el nombre del archivo
            strNombreArchivo = "Puntaje.txt";
            grabaArchivo();
            }
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_A){
            
            booAyuda = !booAyuda;
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
            try {
                leeArchivo();
                
            } catch (IOException ex) {
                Logger.getLogger(JFrameBreaking.class.getName()).log(Level.SEVERE, null, ex);
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
        if(booAyuda){
            URL urlImagenAyuda = this.getClass().getResource("Inicio.jpg");
            Image imaImagenJuego = Toolkit.getDefaultToolkit().
                                    getImage(urlImagenAyuda);
            graGraficaJFrame.drawImage(imaImagenJuego, 0, 0,
                    getWidth(), getHeight(), this);
        }
        else if (iVidas <= 0) {
            
            // creo imagen para el background
            URL urlImagenFondo = this.getClass().getResource("game_over.jpg");
                Image imaImagenJuego = Toolkit.getDefaultToolkit().
                                        getImage(urlImagenFondo);

            // Despliego la imagen
            graGraficaJFrame.drawImage(imaImagenJuego, 0, 0, 
                    getWidth(), getHeight(), this);
            
            souFinal.play();
        }
        else {  
            g.setColor(Color.red);
            
            //se despliegan las vidas
            g.drawString("Vidas: "+ Integer.toString(iVidas), 40, 35);
            
            // se despliega el score
            g.drawString("Score: " + Integer.toString(iScore), 95, 35);
            
            // desplegar imagenes
            if (perBolita != null && perSombrero != null && perMentafetamina != null && objPildoraRoja.size() != 0 && objPildoraAzul.size() != 0 && perVidas != null) {
                //Dibuja la imagen de Nena en la posicion actualizada
                g.drawImage(perBolita.getImagen(), perBolita.getX(),
                        perBolita.getY(), this);
                //Dibuja el sombrero
                g.drawImage(perSombrero.getImagen(), perSombrero.getX(),
                        perSombrero.getY(), this);
                //Dibuja la mentafetamina
                g.drawImage(perMentafetamina.getImagen(), perMentafetamina.getX(),
                        perMentafetamina.getY(), this);
                
                //Dibuja las vidas
                g.drawImage(perVidas.getImagen(), perVidas.getX(), 
                        perVidas.getY(), this);
          
                for( Object objRoja :objPildoraRoja){
                    Personaje perPildora1 = (Personaje) objRoja;
                    //Dibuja la imagen de pildora roja en la posicion actualizada
                    g.drawImage(perPildora1.getImagen(), perPildora1.getX(),
                           perPildora1.getY(), this);
                }
                
                for(Object objAzul :objPildoraAzul){
                    Personaje perPildora2 = (Personaje) objAzul;
                    // Dibuja la imagen de pildora azul en la posicion actualizada
                    g.drawImage(perPildora2.getImagen(), perPildora2.getX(), 
                            perPildora2.getY(), this);
                }
                

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
        objPildoraRoja.clear();
        objPildoraAzul.clear();
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
        iColRoja = (Integer.parseInt(strArr[4]));
        iColAzul = (Integer.parseInt(strArr[5]));
        iColMenta = (Integer.parseInt(strArr[6]));
        iVelocidad = (Integer.parseInt(strArr[7]));
        // lee otra linea del archivo
        dato = fileIn.readLine();
        URL urlImagenRoja = this.getClass().getResource("pildora1.png");
               
        for ( int i = 0; i < 45; i++) {
         // se posiciona a Susy en alguna parte al azar del cuadrante
            strArr = dato.split(",");  
            int iPosX = (Integer.parseInt(strArr[0]));
            int iPosY = (Integer.parseInt(strArr[1]));
            // se crea el objeto camina
            Personaje perPildoraR = new Personaje(iPosX, iPosY,
                        Toolkit.getDefaultToolkit().getImage(urlImagenRoja));
            objPildoraRoja.add(perPildoraR);  
                    // lee otra linea del archivo
            dato = fileIn.readLine();
        }
        
        URL urlImagenAzul = this.getClass().getResource("pildora2.png");
        for (int i = 0; i < 45; i++) {
            // se posiciona a Susy en alguna parte al azar del cuadrante
            strArr = dato.split(",");
            int iPosCorreX = (Integer.parseInt(strArr[0]));
            int iPosCorreY = (Integer.parseInt(strArr[1]));
            // se crea el objeto camina
            Personaje perPildoraA = new Personaje(iPosCorreX,iPosCorreY ,
                    Toolkit.getDefaultToolkit().getImage(urlImagenAzul));
            objPildoraAzul.add(perPildoraA);  
            // lee otra linea del archivo
            dato = fileIn.readLine();
            
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
                    + ","+ perBolita.getY()+ ","+ iColRoja+ ","+ iColAzul+ ","
                                                +iColMenta+ ","+iVelocidad);
            // graba en el archivo la  posicion de caminadores
            for( Object objCamina :objPildoraRoja) {
                Personaje perPildoraR = (Personaje) objCamina;
                fileOut.println(perPildoraR.getX() + ","+perPildoraR.getY());
                
            }
            //graba en el archivo la poicion de corredores
            for( Object objCorre :objPildoraAzul){
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
