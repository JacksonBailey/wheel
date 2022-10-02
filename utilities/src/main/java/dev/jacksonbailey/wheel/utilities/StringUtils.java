package dev.jacksonbailey.wheel.utilities;

import dev.jacksonbailey.wheel.list.LinkedList;

/**
 * Public wrapper for internal utilities. Included to show how multiple modules work within Gradle.
 */
public class StringUtils {

  /**
   * Concatenates the elements of the list separated by spaces.
   *
   * @param source the list to concatenate
   * @return the concatenated string
   */
  public static String join(LinkedList source) {
    return JoinUtils.join(source);
  }

  /**
   * Splits a string by spaces and adds each token to a list.
   *
   * @param source the string to split
   * @return the list of tokens
   */
  public static LinkedList split(String source) {
    return SplitUtils.split(source);
  }
}
