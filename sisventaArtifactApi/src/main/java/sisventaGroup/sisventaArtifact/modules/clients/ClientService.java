package sisventaGroup.sisventaArtifact.modules.clients;

import org.springframework.stereotype.Service;
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
        return this.clientRepository.findAll().stream().map(client->{
          ClientResponse response=new ClientResponse();
          response.setName(client.getName());
          response.setLastName(client.getLastName());
          response.setDni(client.getDni());
          response.setPhone(client.getPhone());
          response.setAddress(client.getAddress());
          return response;
        }).toList();
    }

    public ClientResponse createClient(ClientRequest clientRequest){
        Client newclient=new Client();
        newclient.setName(clientRequest.getName());
        newclient.setLastName(clientRequest.getLastName());
        newclient.setDni(clientRequest.getDni());
        newclient.setPhone(clientRequest.getPhone());
        newclient.setAddress(clientRequest.getAddress());

        Client client=this.clientRepository.save(newclient);

        ClientResponse response=new ClientResponse();
        response.setName(client.getName());
        response.setLastName(client.getLastName());
        response.setDni(client.getDni());
        response.setPhone(client.getPhone());
        response.setAddress(client.getAddress());
        return response;
    }
}
