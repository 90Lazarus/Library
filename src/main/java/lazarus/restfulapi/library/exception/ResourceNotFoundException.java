package lazarus.restfulapi.library.exception;

public class ResourceNotFoundException extends ResourceException {
    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType) {
        super(resourceType, "The database contains no entries of the following resource: " + resourceType.toString());
    }

    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType, Long id) {
        super(resourceType, resourceType.toString() + " with the id = " + id.toString() + " does not exist in the database!");
    }

    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType, Long id, ErrorInfo.ResourceType resourceType2) {
        super(resourceType, "Found no " + resourceType.toString() + " in the " + resourceType2.toString() + " with the id = " + id.toString());
    }

    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType, ErrorInfo.ResourceType resourceType2, Long id) {
        super(resourceType, "Found no " + resourceType.toString() + " in the " + resourceType2.toString() + " with the id = " + id.toString());
    }

    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType1, Long id1, ErrorInfo.ResourceType resourceType2, Long id2) {
        super(resourceType2, resourceType2.toString() + " with the id = " + id2.toString() + " does not belong to the " + resourceType1.toString() + " with the id = " + id1.toString() + "!");
    }

    public ResourceNotFoundException(ErrorInfo.ResourceType resourceType, String message) {
        super(resourceType, message);
    }
}