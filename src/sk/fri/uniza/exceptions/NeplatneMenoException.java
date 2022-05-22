package sk.fri.uniza.exceptions;

/**
 * 27. 2. 2022 - 15:54
 *
 * @author richa
 */
public class NeplatneMenoException extends Exception {
    public NeplatneMenoException() {
    }
    @Override
    public String getMessage() {
        return "Meno hraca musi obsahovat len cisla alebo pismena alebo ich kombinaciu";
    }
}
