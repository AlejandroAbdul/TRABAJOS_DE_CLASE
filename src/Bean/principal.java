package Bean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Vector;

public class principal extends JFrame implements BloqueadorListener{
    private JButton btnConexion;
    private BaseDatos bd;

    public static void main(String[]args){
        principal ventana = new principal();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.pack();

    }
    public principal(){
        super("prueba bloqueador");
        Container contenedor = this.getContentPane();
        JPanel panelBlock = new JPanel();
        Bloqueador btnBlock = new Bloqueador();
        btnBlock.addBloqueadorListener(this);
        btnConexion = new JButton("Conexion");
        btnConexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bd = new BaseDatos();
                Connection conexionSQL = bd.Conectar();
                if(conexionSQL != null){
                    JOptionPane.showMessageDialog(null, "Conexion exitosa");
                    btnBlock.setConexion(true);
                    ResultSet rsContraseñas = bd.consultarContraseñas();
                    try{
                        Vector contraseñas = new Vector();
                        while(rsContraseñas.next()){
                            String cadena = rsContraseñas.getString(1);
                            contraseñas.add(cadena);
                            System.out.println(contraseñas);
                            btnBlock.setContraseñas(contraseñas);
                        }
                    }
                    catch(SQLException er){}
                }
            }
        });
        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bd = new BaseDatos();
                Connection conexionSQL = bd.Conectar();
                String nuevaContraseña = JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña");
                System.out.println(nuevaContraseña);
                bd.insertarContraseña(nuevaContraseña);
                JOptionPane.showMessageDialog(null, "base de datos modificada, favor de conectar de nuevo");
            }
        });
        panelBlock.add(btnInsertar);
        panelBlock.add(btnConexion);
        panelBlock.add(btnBlock);
        contenedor.add(panelBlock);
    }
    public void entradaContraseña(EventObject e){
        JOptionPane.showMessageDialog(this, "se a ingresado una contraseña");
    }
}
