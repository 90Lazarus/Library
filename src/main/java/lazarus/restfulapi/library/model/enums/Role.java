package lazarus.restfulapi.library.model.enums;

public enum Role {
    ADMIN { @Override public String toString() { return "ADMIN"; } },
    STAFF { @Override public String toString() { return "STAFF"; } },
    USER { @Override public String toString() { return "USER"; } },
    GUEST { @Override public String toString() { return "GUEST"; } }
}