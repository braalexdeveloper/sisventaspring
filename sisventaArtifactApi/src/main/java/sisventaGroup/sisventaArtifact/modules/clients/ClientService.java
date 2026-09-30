package sisventaGroup.sisventaArtifact.modules.clients;

import org.springframework.stereotype.Service;
import sisventaGroup.sisventaArtifact.Errors.ResourceNotFoundException;
import sisventaGroup.sisventaArtifact.modules.clients.dtos.ClientRequest;
import sisventaGroup.sisventaArtifact.modules.clients.dtos.ClientResponse;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }

    public List<ClientResponse> getClients(){
        return this.clientRepository.findAll().stream().map(this::convertToClientResponse).toList();
    }

    public ClientResponse createClient(ClientRequest clientRequest){
        Client newclient=this.convertToClient(new Client(),clientRequest);

        return this.convertToClientResponse(this.clientRepository.save(newclient));

    }

    public ClientResponse updateClient(Long id,ClientRequest clientRequest){
        Client clientFound=this.clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cliente no encontrado"));

        Client clientUpdate=this.clientRepository.save(this.convertToClient(clientFound,clientRequest));
        return this.convertToClientResponse(clientUpdate);
    }

    public String deleteClient(Long id){
        this.clientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cliente no encontrado"));

        this.clientRepository.deleteById(id);
        return "Cliente eliminado correctamente";
    }

    private Client convertToClient(Client client,ClientRequest clientRequest){
        client.setName(clientRequest.getName());
        client.setLastName(clientRequest.getLastName());
        client.setDni(clientRequest.getDni());
        client.setPhone(clientRequest.getPhone());
        client.setAddress(clientRequest.getAddress());

        return client;
    }

    private ClientResponse convertToClientResponse(Client client){
        ClientResponse response=new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setLastName(client.getLastName());
        response.setDni(client.getDni());
        response.setPhone(client.getPhone());
        response.setAddress(client.getAddress());
        return response;
    }
}
