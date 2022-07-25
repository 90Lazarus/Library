package lazarus.restfulapi.library.exception;

public class ResourceNotFoundException extends ResourceException {
    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType) {
        super(resourceType, "Resource '" + resourceType.toString() + "' not found");
    }

    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType, String message) {
        super(resourceType, message);
    }
}