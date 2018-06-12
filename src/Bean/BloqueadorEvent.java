package Bean;

import java.util.EventObject;

public class BloqueadorEvent extends EventObject {
    protected String contraseña;
    public BloqueadorEvent(Object fuente, String cadena){
        super(fuente);
        contraseña = cadena;
    }
    public String getContraseña(){return contraseña;}

}
