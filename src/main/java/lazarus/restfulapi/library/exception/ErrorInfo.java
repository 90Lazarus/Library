package lazarus.restfulapi.library.exception;

import lombok.*;

@Value @Builder
public class ErrorInfo {
    ErrorType errorType;
    ResourceType resourceType;
    String message;

    public enum ErrorType {
        RESOURCE_NOT_FOUND
    }

    public enum ResourceType {
        LIBRARY_WORKING_TIME { @Override public String toString() { return "'Working time'"; } },
        LIBRARY { @Override public String toString() { return "'Library'"; } }
    }
}