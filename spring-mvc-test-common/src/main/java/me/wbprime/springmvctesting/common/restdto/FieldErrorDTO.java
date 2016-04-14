package me.wbprime.springmvctesting.common.restdto;

/**
 * @author Petri Kainulainen
 */
public class FieldErrorDTO {

    private String path;
    private String message;

    public FieldErrorDTO(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}