package rogue;


public class InvalidMoveException extends Exception {
  private static final long serialVersionUID = 3141153130820755879L;

  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public InvalidMoveException(String error) {
    super(error);
  }
}
