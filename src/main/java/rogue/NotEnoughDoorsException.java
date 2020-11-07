package rogue;

public class NotEnoughDoorsException extends Exception {
  public NotEnoughDoorsException(String error) {
    super(error);
  }
}
