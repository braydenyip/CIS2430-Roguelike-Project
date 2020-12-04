package rogue;

public class NoSuchItemException extends Exception {
  private static final long serialVersionUID = -6852729153921127811L;

  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public NoSuchItemException(String error) {
    super(error);
  }
}
