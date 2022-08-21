package lazarus.restfulapi.library.exception;

public class UniqueViolationException extends ResourceException {
    public UniqueViolationException(ErrorInfo.ResourceType resourceType) {
        super(resourceType, resourceType.toString() + " already exists in the database!");
    }

    public UniqueViolationException(ErrorInfo.ResourceType resourceType, String name) {
        super(resourceType, resourceType.toString() + ": '" + name + "' already exists in the database!");
    }

    public UniqueViolationException(ErrorInfo.ResourceType resourceType, Long id) {
        super(resourceType, resourceType.toString() + " with the id: '" + id + "' already exists in the database!");
    }

    public UniqueViolationException(ErrorInfo.ResourceType resourceType, Long id, ErrorInfo.ResourceType resourceType2, Long id2 ) {
        super(resourceType, resourceType.toString() + " with the id: '" + id + "' is already rented to a !" + resourceType2 + " with the id: '" + id2);
    }
}
