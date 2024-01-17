package ru.clevertec.ecl.knyazev.data.domain.pagination.exception;

public class PagingException extends RuntimeException {
    public static final String PAGING_NOT_USING_ERROR = "Searching not using";

    public PagingException() {
        super(PAGING_NOT_USING_ERROR);
    }

    public PagingException(String message) {
        super(message);
    }

    public PagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PagingException(Throwable cause) {
        super(cause);
    }
}
