package lazarus.restfulapi.library.exception;

import lombok.*;

@Value @Builder
public class ErrorInfo {
    ErrorType errorType;
    ResourceType resourceType;
    String message;

    public enum ErrorType {
        RESOURCE_NOT_FOUND, UNIQUE_VIOLATION, PASSWORDS_DONT_MATCH, INVALID_ROLE
    }

    public enum ResourceType {
        LIBRARY_WORKING_TIME { @Override public String toString() { return "'Working time'"; } },
        LIBRARY { @Override public String toString() { return "'Library'"; } },
        LANGUAGE { @Override public String toString() { return "'Language'"; } },
        GENRE { @Override public String toString() { return "'Genre'"; } },
        PUBLISHER { @Override public String toString() { return "'Publisher'"; } },
        AUTHOR { @Override public String toString() { return "'Author'"; } },
        BOOK { @Override public String toString() { return "'Book'"; } },
        USER { @Override public String toString() { return "'User'"; } },
        EMAIL { @Override public String toString() { return "'Email'"; } },
        PASSWORDS { @Override public String toString() { return "'Passwords'"; } },
        RENTING_INFO { @Override public String toString() { return "'Renting info'"; } },
        RENTING_HISTORY { @Override public String toString() { return "'Renting history'"; } },
        ROLE { @Override public String toString() { return "'Role'"; } }
    }
}