package rogue;

public class NotEnoughDoorsException extends Exception {
  private static final long serialVersionUID = 3159502610986431304L;

  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public NotEnoughDoorsException(String error) {
    super(error);
  }
}
