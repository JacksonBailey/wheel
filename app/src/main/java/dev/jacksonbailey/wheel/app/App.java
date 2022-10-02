package dev.jacksonbailey.wheel.app;

import static dev.jacksonbailey.wheel.app.MessageUtils.getMessage;
import static dev.jacksonbailey.wheel.utilities.StringUtils.join;
import static dev.jacksonbailey.wheel.utilities.StringUtils.split;

import dev.jacksonbailey.wheel.list.LinkedList;
import org.apache.commons.text.WordUtils;

/**
 * Contains the application's main method
 */
public class App {

  /**
   * The main method of the application.
   *
   * @param args ignored
   */
  public static void main(String[] args) {
    LinkedList tokens;
    tokens = split(getMessage());
    String result = join(tokens);
    System.out.println(WordUtils.capitalize(result));
  }
}
