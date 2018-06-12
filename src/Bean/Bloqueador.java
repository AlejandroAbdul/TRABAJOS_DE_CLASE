package Bean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.EventObject;
import java.util.Vector;
import java.util.function.Predicate;

public class Bloqueador extends JComponent implements Serializable, MouseListener, Runnable {

    private int ancho = 50;
    private int alto = 60;
    private boolean mouseOver = false;
    private final Dimension PREFERRED_DIMENSION = new Dimension(ancho, alto);
    private boolean blockActivo = false;
    private String contraseña = "hola";
    private Vector BloqueadorListeners = new Vector();
    private Vector <String> contraseñas = new Vector <String> ();
    private boolean conexion = false;

    public Bloqueador(){
        this.setVisible(true);
        this.setPreferredSize(PREFERRED_DIMENSION);
        this.setMaximumSize(PREFERRED_DIMENSION);
        this.setMinimumSize(PREFERRED_DIMENSION);
        this.addMouseListener(this);
        this.repaint();
    }

    public void setConexion(boolean conexion){
        this.conexion = conexion;
    }

    public void setContraseñas(Vector contraseñas){
        this.contraseñas = contraseñas;
    }
    public void paintComponent(Graphics g){

        //DIBUJADO DEL COMPONENTE

        g.setColor(Color.black);
        g.fillRect(0,0,this.getWidth()-1,this.getHeight()-1);
        g.setColor(new Color(218, 165, 32));
        g.fillOval(10, 5, this.getWidth()-21, this.getHeight()-30);
        g.setColor(Color.black);
        g.fillOval(14, 9, this.getWidth()-29, this.getHeight()-30);
        g.setColor(new Color(218, 165, 32));
        g.fillRect(10, 20, this.getWidth()-21, this.getHeight()-30);
        if(mouseOver == false){
            g.setColor(Color.black);
            g.fillRect(10, 14, 10, 6);
        }
    }
    public void run(){
        Graphics g = this.getGraphics();
        for(byte n = 0; n<this.getHeight()-29; n++){
            /*g.setColor(Color.black);
            g.fillRect(0,0,this.getWidth()-1,this.getHeight()-1);*/
            g.setColor(Color.GRAY);
            g.fillRect(0, -30+n, this.getWidth()-1, 30);
            g.fillRect(0, 60-n, this.getWidth()-1, 30);
            g.setColor(Color.darkGray);
            g.fillRect(0, -5+n, this.getWidth()-1, 5);
            g.fillRect(0, 60-n, this.getWidth()-1, 5);
            g.setColor(Color.lightGray);
            g.fillRect(0, -1+n, 5, 2);
            g.fillRect(10, -1+n, 5, 2);
            g.fillRect(20, -1+n, 5, 2);
            g.fillRect(30, -1+n, 5, 2);
            g.fillRect(40, -1+n, 5, 2);
            g.fillRect(5, 59-n, 5, 2);
            g.fillRect(15, 59-n, 5, 2);
            g.fillRect(25, 59-n, 5, 2);
            g.fillRect(35, 59-n, 5, 2);
            g.fillRect(45, 59-n, 5, 2);
            if(!blockActivo){
                this.repaint();
                break;
            }
            try{
                Thread.currentThread().sleep(50);
            }
            catch(InterruptedException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(conexion){
            blockActivo = true;
            Thread hilo = new Thread(this);
            hilo.start();
            do{
                JPasswordField pfContraseña = new JPasswordField();
                JLabel labTitulo = new JLabel ("Ingrese su contraseña");
                JOptionPane.showMessageDialog(null, new Object[]{labTitulo, pfContraseña}, "Inicio de sesión", JOptionPane.NO_OPTION);
                String entradaContraseña = pfContraseña.getText();
                Vector <String> copiaPass = (Vector) contraseñas.clone();
                //System.out.println(copiaPass);
                for(int cont = 0; cont < copiaPass.size(); cont++){
                    String cadena = copiaPass.elementAt(cont);
                    //System.out.println(cadena+'3');
                    if(entradaContraseña.equals(cadena)){
                        blockActivo = false;
                    }
                }
                BloqueadorEvent event = new BloqueadorEvent(this, entradaContraseña);
                notificarEntrada(event);
            }while(blockActivo);
            mouseOver = false;
            this.repaint();
        }
        else{
            JOptionPane.showMessageDialog(this, "No se a realizado una conexion a una base de datos");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseOver = true;
        this.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseOver = false;
        this.repaint();
    }
    //METODOS DEL VECTOR BLOQUEADORLISTENERS
    public synchronized  void removeBloqueadorListener(BloqueadorListener listener){
        BloqueadorListeners.remove(listener);
    }
    public synchronized void addBloqueadorListener(BloqueadorListener listener){
        BloqueadorListeners.add(listener);
    }
    //METODO QUE LLAMA A LOS LISTENER
    public void notificarEntrada(BloqueadorEvent event){
        Vector lista;
        synchronized (this){
            lista = (Vector) BloqueadorListeners.clone();
        }
        for(int i = 0;i < lista.size();i++){
            BloqueadorListener listener = (BloqueadorListener) lista.elementAt(i);
            listener.entradaContraseña(event);
        }
    }
}

