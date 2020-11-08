package rogue;

public class NotEnoughDoorsException extends Exception {
  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public NotEnoughDoorsException(String error) {
    super(error);
  }
}
