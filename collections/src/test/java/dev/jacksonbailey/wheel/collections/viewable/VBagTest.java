package dev.jacksonbailey.wheel.collections.viewable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VBagTest {

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  VBag<Integer> bag;

  @Test
  void emptyTest() {
    given(bag.size()).willReturn(0);
    assertTrue(bag.isEmpty());
  }

  @Test
  void nonEmptyTest() {
    given(bag.size()).willReturn(3);
    assertFalse(bag.isEmpty());
  }

  @Test
  void contains() {
    given(bag.iterator()).willReturn(List.of(1, 2).iterator());
    assertTrue(bag.contains(1));
  }

  @Test
  void notCotains() {
    given(bag.iterator()).willReturn(List.of(1, 2).iterator());
    assertFalse(bag.contains(3));
  }

  @Test
  @SuppressWarnings("unchecked")
  void containsAll() {
    given(bag.iterator()).willReturn(List.of(1, 2).iterator());
    VBag<Integer> input = mock(VBag.class);
    given(input.iterator()).willReturn(List.of(1, 2).iterator());
    assertTrue(bag.containsAll(input));
  }

  @Test
  @SuppressWarnings("unchecked")
  void notCotainsAll() {
    given(bag.iterator()).willReturn(List.of(1, 2).iterator());
    VBag<Integer> input = mock(VBag.class);
    given(input.iterator()).willReturn(List.of(1, 2, 3).iterator());
    assertFalse(bag.containsAll(input));
  }
}
