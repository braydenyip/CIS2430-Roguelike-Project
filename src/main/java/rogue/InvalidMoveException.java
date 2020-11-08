package rogue;


public class InvalidMoveException extends Exception {
  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public InvalidMoveException(String error) {
    super(error);
  }
}
