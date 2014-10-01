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
    private int iCae; // variable para manejrar las vidas
    private int iDireccion;  // indica la direccion de Nena
    private int iScore; // score del juego
    private int iColRoja; // contador de las colisiones
    private int iColAzul; // contador de colisones azul
    private int iColision;
    private int iColMenta; // contador de colisiones de mentafetamina
    private int iPosXBolita; // posicion x de bola
    private int iPosYBolita;// posicion de y de bola
    private int iPosXAnim; // coordenadas de la imagen anim
    private int iPosYAnim; //coordenadas de la imagen anim
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
    private LinkedList objBilletes; // objetos de la linkedlist de billetes
    private LinkedList objBilletes2; //objeto de la linkedLIST de billetes
    private int iVelocidad; // velocidad de corre
    private SoundClip souFondo; // sonido alegre
    private SoundClip souFinal; //sonido triste de cuando choca
    private SoundClip souColision; // sonido de que rompe pastilla
    private String strNombreArchivo; // Nombre del archivo
    private String strNombreJugador; // nombre del jugador
    private String[] strArr; // arreglo del archivo dividido
    private boolean booPausa; // booleana para activar pausar
    private boolean booAyuda; // booleano para ctivar la ayuda
    private boolean booGuardar; //booleana para activar guardad
    private boolean booSpace; // booleana para activar que se valla la bolita
    private boolean booPlay; // Control de la 
    private JList jliScore; //Lista para desplegar el puntaje
    private Animacion animAmbulancia; //animacion de ambulancia
    private Animacion animSombrero; // animacion de sombrero
    //variables de control de tiempo de la animacion
    private long lonTiempoActual;
    private long lonTiempoInicial;
    
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
        
        
        // incializo las iCae
        iCae = ((int) (Math.random() * 2)) + 4;
        
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
        URL urlImagenSombrero = this.getClass().getResource("sombrero.gif");
        
        // posiciones del sombrero
        int posX = (getWidth()/2);
        int posY = (getHeight());
        
        // se crea el sombrero
        perSombrero = new Personaje(posX,posY,
                    Toolkit.getDefaultToolkit().getImage(urlImagenSombrero));
        
        perSombrero.setX(posX-perSombrero.getAncho()/2);
        perSombrero.setY(posY-perSombrero.getAlto());
 
        perSombrero.setVelocidad(4);
                
        // posiciones de bolita
        int posXBol = (getWidth() / 2);
        int posYBol = (getHeight()-perSombrero.getAlto());
        
 	// se crea imagen de bolita
        URL urlImagenNemo = this.getClass().getResource("atomo.gif");
        
        // se crea a bolita
	perBolita = new Personaje(posXBol,posYBol,
                Toolkit.getDefaultToolkit().getImage(urlImagenNemo));

        perBolita.setX(posXBol-perBolita.getAncho()/2);
        perBolita.setY(posYBol-perBolita.getAlto()/2);
        
        perBolita.setVelocidad(4);
        
        int posXMen = (getWidth()/2);
        int posYMen = 150;
        
        // se crea la Mentafetamina
        URL urlImagenMentafetamina = this.getClass().
                                getResource("mentafetamina.png");
        perMentafetamina = new Personaje( posXMen, posYMen,
                Toolkit.getDefaultToolkit().getImage(urlImagenMentafetamina));
        perMentafetamina.setX(posXMen - perMentafetamina.getAncho()/2);
        perMentafetamina.setY(posYMen - perMentafetamina.getAlto()/2);
        
        int posXPil = 40;
        int posYPil = 190;
        
        
        // se crea la lista de pildora roja
        objPildoraRoja = new LinkedList();
        
        // inicializa la lista de pildora1
        for ( int iI = 0; iI < 9; iI++) {
            // se posiciona la pildora 1 en la tabla periodica
            for (int iJ = 0; iJ < 5; iJ++) {
                URL urlImagenPildora = this.getClass().
                        getResource("pildora1.png");
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
         // se crea la lista de pildora azul
        objPildoraAzul = new LinkedList();
        
        // inicializa la lista de pildora2
        for ( int iI = 0; iI < 9; iI++) {
            // se posiciona la pildora 2 en la tabla periodica
            for (int iJ = 0; iJ < 5; iJ++) {
                URL urlImagenPildora2 = this.getClass().
                        getResource("pildora2.png");
                Personaje perPildora2 = new Personaje(posXPil2, posYPil2,
                    Toolkit.getDefaultToolkit().getImage(urlImagenPildora2));
                objPildoraAzul.add(perPildora2);
                posYPil2= posYPil2+40;
            }    
            posXPil2= posXPil2 +40;
            posYPil2= 190;
        }
        
        // se crea la lista de los billetes
        objBilletes = new LinkedList();
        
        //se crea la lista de los billetes
        for( int iI = 1; iI <=20; iI++) {
            // se posicion la galleta en alguna parte al azar del cuadrante 
            // inferior derecho
            posX = (int) (Math.random() * (getWidth()- 60));    
            posY = (int) (Math.random() * (getHeight()/2))*(-1);    
            URL urlImagenBillete = this.getClass().getResource("billete1.png");

            // se crea el objeto galleta
            Personaje perBillete = new Personaje(posX,posY,
                Toolkit.getDefaultToolkit().getImage(urlImagenBillete));
            objBilletes.add(perBillete);
        }
        
        //se crea la lista de objetos de billetes2
        objBilletes2 = new LinkedList();
        
        //se crea la lista de los billetes
        for( int iI = 1; iI <=20; iI++) {
            // se posiciona el billete fuera del applet
            posX = (int) (Math.random() * (getWidth()- 60));    
            posY = (int) (Math.random() * (getHeight()/3))*(-1);    
            URL urlImagenBillete2 = this.getClass().getResource("billete2.png");

            // se crea el objeto billete
            Personaje perBillete2 = new Personaje(posX,posY,
                Toolkit.getDefaultToolkit().getImage(urlImagenBillete2));
            objBilletes2.add(perBillete2);
        }
        
        iPosXAnim= (int) (Math.random() * (getWidth()- 60));    
        iPosYAnim = (int) (Math.random() * (getHeight()/2))*(-1); 

        Image  ambulancia1 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("imagenes/sirena1.png"));
        Image  ambulancia2 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("imagenes/sirena2.png"));
            
        animAmbulancia = new Animacion();
        animAmbulancia.sumaCuadro(ambulancia1, 100);
        animAmbulancia.sumaCuadro(ambulancia2, 100);
 
        
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
        souColision = new SoundClip("rompe.wav");
        
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
     * para la animAmbulanciaacion este metodo es llamado despues del init o 
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
        
        //Guarda tiempo actual del sistema
        lonTiempoActual = System.currentTimeMillis();
        // se realiza el ciclo del juego en este caso nunca termina
        while (true) {
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
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su 
        //ejecución
         long tiempoTranscurrido =
             System.currentTimeMillis() - lonTiempoActual;
            
         //Guarda el tiempo actual
       	 lonTiempoActual += tiempoTranscurrido;

         //Actualiza la animAmbulanciaación en base al tiempo transcurrido
         animAmbulancia.actualiza(tiempoTranscurrido);
        
        //Dependiendo de la iDireccion de nena es a donde se movera
        switch(iDireccion) {
            case 1: { //se mueve hacia izquierda
                perSombrero.setX(perSombrero.getX() - iVelocidad);
                if (!booSpace) {
                    perBolita.setX(perBolita.getX()-iVelocidad);
                }
                break;    
            }
            case 2: { //se mueve hacia derecha
                perSombrero.setX(perSombrero.getX() + iVelocidad);
                if (!booSpace) {
                    perBolita.setX(perBolita.getX() + iVelocidad);
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
        //actualiza la botella dependiendo de la cantidad de pildoras existentes
        if((objPildoraRoja.size() < (45/2))){
            perVidas.setY(perVidas.getY() + perVidas.getVelocidad());
  
        }
        
        
        if(iCae < 4 && (objPildoraRoja.size()< 20)){
            iPosYAnim = iPosYAnim + 3;
        }
        else{   
            iCae--;         
        }
        //si se acaba el juego se sueltan los billetes
        if (objPildoraRoja.isEmpty() && objPildoraAzul.isEmpty()) {
            for(Object objBillete :objBilletes){
            Personaje perBillete = (Personaje) objBillete;
            perBillete.setY(perBillete.getY() + 6);
            }
            for(Object objBillete2 :objBilletes2){
            Personaje perBillete2 = (Personaje) objBillete2;
            perBillete2.setY(perBillete2.getY() + 6);
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
                
                for(int iI = 0; iI < objPildoraRoja.size(); iI++){
                    Personaje perPildora1 = (Personaje) objPildoraRoja.get(iI); 
                    if(perPildora1.colisiona(perBolita)){
                        iScore++;
                        souColision.play();
                        //dependiendo de la colision de la pelota con la pildora
                        // es como rebotara la pelota
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
                       // perPildora1.setX(-getWidth());
                        //perPildora1.setY(-(getHeight()/2));
                       objPildoraRoja.remove(perPildora1);
                    }
                }
                for(int iJ = 0; iJ < objPildoraAzul.size(); iJ++){
                    Personaje perPildora2 = (Personaje) objPildoraAzul.get(iJ); 
                    if(perPildora2.colisiona(perBolita)){
                        
                        iColAzul++;
                        //dependiendo de la colision de la pelota con la pildora
                        // es como rebotara la pelota
                        if (iColAzul == 2) {
                            souColision.play();
                            iScore=iScore+2;
                            //perPildora2.setX(-getWidth());
                            //perPildora2.setY(-(getHeight()/2));
                            objPildoraAzul.remove(perPildora2);
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
                
                //elimina la mentafetamina hasta que haya sido golpeada 5 veces
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
                    objPildoraAzul.remove(0);
                    objPildoraAzul.remove(1);
                    objPildoraAzul.remove(2);
                    perVidas.setX(-getWidth());
                }
                
                if(iPosYAnim + 51 > getHeight()) {
                    iPosXAnim= (int) (Math.random() * (getWidth()- 60));    
                    iPosYAnim = (int) (Math.random() * (getHeight()/2))*(-1); 
                }
                
                if (perSombrero.colisiona(iPosXAnim+121, iPosYAnim+51)) {
                    iPosXAnim= (int) (Math.random() * (getWidth()- 60));    
                    iPosYAnim = (int) (Math.random() * (getHeight()/2))*(-1); 
                    int posXPil = 40;
                    int posYPil = 190;
                    // se crea la vacia la lista de pildoras rojas
                    objPildoraRoja.clear();
                    
                    // inicializa la lista de pildora1
                    for ( int iI = 0; iI < 9; iI++) {
                    // se posiciona la pildora 1 en la tabla periodica
                        for (int iJ = 0; iJ < 5; iJ++) {
                            URL urlImagenPildora = this.getClass().
                            getResource("pildora1.png");
                            Personaje perPildora1 = new Personaje(posXPil, posYPil,
                            Toolkit.getDefaultToolkit().getImage(urlImagenPildora));
                            objPildoraRoja.add(perPildora1);
                            posYPil= posYPil+40;
                        }    
                        posXPil= posXPil +40;
                        posYPil= 190;
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
            iVelocidad++;
            iDireccion = 1;   // cambio la direccion a la izquierda
        }
        // si presiono la tecla D
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){ 
            iVelocidad++;
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
            iVelocidad = 2;
            iDireccion = 3;   // cambio la direccion a la izquierda
        }
        // si presiono la tecla D
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){    
                iDireccion = 3;   // cambio la direccion a la derecha
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_V){
            iVelocidad = 2;
        }
    	if(keyEvent.getKeyCode() == KeyEvent.VK_P){
              booPausa=!booPausa;
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_C){
            try {
                leeArchivo();
                
            } catch (IOException ex) {
                Logger.getLogger(JFrameBreaking.class.getName()).log(Level.
                        SEVERE, null, ex);
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
        //tecla que se aparece al inicio y al querer ayuda
        else if(keyEvent.getKeyCode() == KeyEvent.VK_A){
            
            booAyuda = !booAyuda;
        }
        // se reinicia el juego
        else if(keyEvent.getKeyCode() == KeyEvent.VK_R){
            try {
                leeArchivo();
                
            } catch (IOException ex) {
                Logger.getLogger(JFrameBreaking.class.getName()).log(Level.
                        SEVERE, null, ex);
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
        URL urlImagenFondo = this.getClass().getResource("backjuego.jpg");
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
            URL urlImagenAyuda = this.getClass().getResource("background_instrucciones.jpg");
            Image imaImagenJuego = Toolkit.getDefaultToolkit().
                                    getImage(urlImagenAyuda);
            graGraficaJFrame.drawImage(imaImagenJuego, 0, 0,
                    getWidth(), getHeight(), this);
        }
        else if ((objPildoraRoja.isEmpty()) && (objPildoraAzul.isEmpty()))  {
            
            // creo imagen para el background
            URL urlImagenFondo = this.getClass().getResource("backFinal.jpg");
                Image imaImagenJuego = Toolkit.getDefaultToolkit().
                                        getImage(urlImagenFondo);

            // Despliego la imagen
            graGraficaJFrame.drawImage(imaImagenJuego, 0, 0, 
                    getWidth(), getHeight(), this);
            if (objBilletes.size() != 0) {
                for (Object objBillete :objBilletes){
                    Personaje perBillete = (Personaje) objBillete;
                    //Dibuja la imagen de billete en la posicion actualizada
                    g.drawImage(perBillete.getImagen(), perBillete.getX(), 
                            perBillete.getY(), this);
                }
                
                for (Object objBillete2 :objBilletes2){
                    Personaje perBillete2 = (Personaje) objBillete2;
                    //Dibuja la imagen de billete en la posicion actualizada
                    g.drawImage(perBillete2.getImagen(), perBillete2.getX(), 
                            perBillete2.getY(), this);
                }
            }
            souFinal.play();
        }
        else {  
            g.setColor(Color.red);
            
            Font f = new Font("Helvetica",Font.ITALIC, 30);
            // se despliega el score
            g.drawString(Integer.toString(iScore), 490, 50);
            g.setFont(f);
            // desplegar imagenes
            if (perBolita != null && perSombrero != null && 
                    perMentafetamina != null && perVidas != null && 
                        animAmbulancia != null) {
                //Dibuja la imagen de Nena en la posicion actualizada
                g.drawImage(perBolita.getImagen(), perBolita.getX(),
                        perBolita.getY(), this);
                //Dibuja el sombrero
                g.drawImage(perSombrero.getImagen(), perSombrero.getX(),
                        perSombrero.getY(), this);
                //Dibuja la mentafetamina
                g.drawImage(perMentafetamina.getImagen(), perMentafetamina.getX(),
                        perMentafetamina.getY(), this);
                
                g.drawImage(animAmbulancia.getImagen(),iPosXAnim, iPosYAnim, this);
                
                //Dibuja las vidas
                g.drawImage(perVidas.getImagen(), perVidas.getX(), 
                        perVidas.getY(), this);
                if (!objPildoraRoja.isEmpty()) {
                    for( Object objRoja :objPildoraRoja){
                    Personaje perPildora1 = (Personaje) objRoja;
                    //Dibuja la imagen de pildora roja en la posicion actualizada
                    g.drawImage(perPildora1.getImagen(), perPildora1.getX(),
                           perPildora1.getY(), this);
                    }
                }
                
                if (!objPildoraRoja.isEmpty()) {
                    for(Object objAzul :objPildoraAzul){
                    Personaje perPildora2 = (Personaje) objAzul;
                    // Dibuja la imagen de pildora azul en la posicion actualizada
                    g.drawImage(perPildora2.getImagen(), perPildora2.getX(), 
                            perPildora2.getY(), this);
                    }
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
        * score, vidas, posicion sombreor, camina, corre, colisiones y
        *velocidad segun corresponda
        */
        iScore = (Integer.parseInt(strArr[0]));
        iCae = (Integer.parseInt(strArr[1]));
        perSombrero.setX((Integer.parseInt(strArr[2])));
        perSombrero.setY((Integer.parseInt(strArr[3])));
        perBolita.setX((Integer.parseInt(strArr[4])));
        perBolita.setY((Integer.parseInt(strArr[5])));
        iColRoja = (Integer.parseInt(strArr[6]));
        iColAzul = (Integer.parseInt(strArr[7]));
        iColMenta = (Integer.parseInt(strArr[8]));
        iVelocidad = (Integer.parseInt(strArr[9]));
        // lee otra linea del archivo
        dato = fileIn.readLine();
        URL urlImagenRoja = this.getClass().getResource("pildora1.png");
               
        for ( int i = 0; i < 45; i++) {
         // se posiciona a Pildora en alguna parte al azar del cuadrante
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
            // se posiciona a Pildora en alguna parte al azar del cuadrante
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
            fileOut.println(iScore+ ","+ iCae+ ","+ perSombrero.getX()
                    + ","+ perSombrero.getY()+ ","+ perBolita.getX()
                    + ","+ perBolita.getY()+","+ iColRoja+ ","+ iColAzul+ ","
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
