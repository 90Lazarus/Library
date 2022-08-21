package lazarus.restfulapi.library.exception;

public class PasswordsDontMatchException extends ResourceException {
    public PasswordsDontMatchException(ErrorInfo.ResourceType resourceType) {
        super(resourceType, resourceType.toString() + " do not match!");
    }
}