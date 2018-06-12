package Bean;

import java.util.EventListener;
import java.util.EventObject;

public interface BloqueadorListener extends EventListener {
    void entradaContraseña(EventObject e);
}
