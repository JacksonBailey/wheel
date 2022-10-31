package dev.jacksonbailey.wheel.collections.assumptions;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * Makes tests to confirm or deny assumptions I have. I will leave them in and reference them if
 * it is ever relevant.
 */
public class AssumptionTest {

  @Test
  void cloneTest() {
    var car1 = new Car();
    car1.add("Wow");
    assertEquals(List.of("Wow"), car1.list);
    assertEquals(1, car1.size);

    var car2 = car1.clone();
    car2.add("Hooray");
    assertEquals(List.of("Wow", "Hooray"), car2.list);
    assertEquals(2, car2.size);
    assertEquals(List.of("Wow"), car1.list);
    assertEquals(1, car1.size);
  }

  public interface Vehicle extends Cloneable {
    void add(String s);
    Optional<String> remove();
  }

  public static class Car implements Vehicle {

    protected ArrayList<String> list;
    protected int size;

    public Car() {
      list = new ArrayList<>();
      size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Car clone() {
      try {
        var car = (Car) super.clone();
        car.list = (ArrayList<String>) list.clone(); // If you do not call this then cloneTest fails
        return car;
      } catch (CloneNotSupportedException e) {
        throw new AssertionError("Cannot happen");
      }
    }

    @Override
    public void add(String s) {
      list.add(s);
      size++;
    }


    @Override
    public Optional<String> remove() {
      if (size == 0) {
        return Optional.empty();
      }
      size--;
      return Optional.of(list.get(size));
    }
  }
}
