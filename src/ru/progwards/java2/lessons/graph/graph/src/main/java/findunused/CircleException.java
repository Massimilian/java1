package findunused;

/**
 * Exception of finding unused objects
 */
public class CircleException extends RuntimeException {

    private final String reason;

    public CircleException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }

}
