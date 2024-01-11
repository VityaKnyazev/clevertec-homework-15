package ru.clevertec.ecl.knyazev.dao.exception;

public class DAOException extends RuntimeException {

    public static final String UPDATING_ERROR = "Updating error";
    public static final String SAVING_ERROR = "Saving error";
    public static final String DELETING_ERROR = "Deleting error";

    public static final String ENTITY_NOT_FOUND = "Entity not found with uuid=";
    public static final String FIND_ALL_ERROR = "Error when searching all entities";

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
