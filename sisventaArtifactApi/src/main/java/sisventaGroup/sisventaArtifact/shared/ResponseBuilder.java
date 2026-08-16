package sisventaGroup.sisventaArtifact.shared;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private final Map<String,Object> response;

    public ResponseBuilder(){
        this.response=new HashMap<>();
    }

    // ✅ Para agregar el estado de la respuesta (success, error, etc.)
    public ResponseBuilder status(String status){
        this.response.put("status",status);
        return this;
    }
    // ✅ Para agregar un mensaje genérico
    public ResponseBuilder msg(String msg) {
        this.response.put("msg", msg);
        return this;
    }

    // ✅ Para agregar datos dinámicos (ej: user, categories, errors, etc.)
    public ResponseBuilder add(String key, Object value) {
        this.response.put(key, value);
        return this;
    }

    public Map<String,Object> build(){
        return this.response;
    }
}
