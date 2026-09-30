package sisventaGroup.sisventaArtifact.modules.clients;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisventaGroup.sisventaArtifact.modules.clients.dtos.ClientRequest;
import sisventaGroup.sisventaArtifact.modules.clients.dtos.ClientResponse;
import sisventaGroup.sisventaArtifact.shared.ResponseBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public  ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getClients(){
        return ResponseEntity.ok(this.clientService.getClients());
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> createClient(@Valid @RequestBody ClientRequest request){
        ClientResponse client=this.clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseBuilder().msg("Cliente creado con éxito").add("client",client).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updateClient(@PathVariable("id") Long id,@Valid @RequestBody ClientRequest request){
        ClientResponse client=this.clientService.updateClient(id,request);
        return ResponseEntity.ok(new ResponseBuilder().msg("Cliente actualizado con éxito").add("client",client).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteClient(@PathVariable("id") Long id){
        String response=this.clientService.deleteClient(id);
        return ResponseEntity.ok(new ResponseBuilder().msg(response).build());
    }
}
