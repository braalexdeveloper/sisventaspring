package sisventaGroup.sisventaArtifact.Errors;



public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
