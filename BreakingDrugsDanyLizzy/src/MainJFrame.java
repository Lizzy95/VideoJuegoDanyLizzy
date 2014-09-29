/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Norma Elizabeth Morales Cruz
 */
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class MainJFrame {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {
        JFrameBreaking juego = new JFrameBreaking(); // objeto de la clase applet
            juego.setSize(800,600);// tamaño del juego
            juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// para cerrar 
            //juego
            juego.setVisible(true); // para que se vea el juego
            
    }
}