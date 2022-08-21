package lazarus.restfulapi.library.exception;

public class InvalidRoleException extends ResourceException {
    public InvalidRoleException(ErrorInfo.ResourceType resourceType) {
        super(resourceType, "Invalid " + resourceType.toString() + "! The role must be one of the following strings: ADMIN, STAFF, USER, GUEST.");
    }
}
