package lazarus.restfulapi.library.exception;

public class UniqueViolationException extends ResourceException {
    public UniqueViolationException(ErrorInfo.ResourceType resourceType, String name) {
        super(resourceType, resourceType.toString() + ": '" + name + "' already exists in the database!");
    }
}
