package Bean;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class BaseDatos {
    public String db = "bloqueador";
    public String url = "jdbc:mysql://localhost/" + db
            + "?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC";
    public String user = "root";
    public String pass = "password";

    Connection link;

    public Connection Conectar(){
        link = null;

        try{
            Class.forName("org.gjt.mm.mysql.Driver");  //version 5
            //Class.forName("com.mysql.cj.jdbc.Driver");   //version 8

            link = DriverManager.getConnection(this.url, this.user, this.pass);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

        return link;
    }
    public ResultSet consultarContraseñas(){
        ResultSet rsContraseñas = null;
        try{
            PreparedStatement rsConsultar = link.prepareStatement("Select contraseña from cadena");
            rsContraseñas = rsConsultar.executeQuery();
        }
        catch(SQLException e){
            System.out.println(e.getErrorCode());
        }
        return rsContraseñas;
    }
    public void insertarContraseña(String contraseña){
        try{
            PreparedStatement stInsertar = link.prepareStatement("insert into cadena(contraseña)" + " values(?)");
            stInsertar.setString(1, contraseña);
            stInsertar.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getErrorCode());
        }
    }
}
