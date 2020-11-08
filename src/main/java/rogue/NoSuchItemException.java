package rogue;

public class NoSuchItemException extends Exception {
  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public NoSuchItemException(String error) {
    super(error);
  }
}
