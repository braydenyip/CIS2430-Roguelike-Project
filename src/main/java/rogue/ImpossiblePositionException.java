package rogue;

public class ImpossiblePositionException extends Exception {
  /**
  * Constructs the exception.
  * @param error The error message.
  */
  public ImpossiblePositionException(String error) {
    super(error);
  }
}
