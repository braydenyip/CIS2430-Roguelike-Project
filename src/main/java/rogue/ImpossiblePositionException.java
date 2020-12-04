package rogue;

public class ImpossiblePositionException extends Exception {
  private static final long serialVersionUID = -5590908180544536704L;

  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public ImpossiblePositionException(String error) {
    super(error);
  }
}
