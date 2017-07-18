package com.epam.range;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RangeTest {
    private Range one;
    private Range equalsToOne;
    private Range another;
    private Range another2;
    private Range negative;
    private Range fullRange;
    private Range fromMinToZero;
    private Range fromZeroToMax;

    @BeforeEach
    public void init() throws Exception {
        one = new Range(1, 25);
        equalsToOne = new Range(1, 25);
        another = new Range(5, 50);
        another2 = new Range(26, 100);
        negative = new Range(-100, -5);
        fullRange = new Range(Long.MIN_VALUE, Long.MAX_VALUE);
        fromMinToZero = new Range(Long.MIN_VALUE, 0);
        fromZeroToMax = new Range(0, Long.MAX_VALUE);
    }

    @Test
    public void initWrongContractTest() throws Exception {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Range(50, 5).isAfter(negative));

        assertEquals(exception.getMessage(), "Start value should be lower than endInclusive");
    }

    @Test
    public void isBeforeNullArgsTest() throws Exception {
        Throwable exception = assertThrows(NullPointerException.class, () -> one.isBefore(null));

        assertEquals(exception.getMessage(), null);

    }

    @Test
    public void isBeforeReturnsTrueTest() throws Exception {
        assertThat(one.isBefore(another2), is(true));
    }

    @Test
    public void isBeforeReturnsFalseTest() throws Exception {
        assertThat(another.isBefore(one), is(false));
        assertThat(one.isBefore(equalsToOne), is(false));
    }

    @Test
    public void isAfterNullArgsTest() throws Exception {
        Throwable exception = assertThrows(NullPointerException.class, () -> one.isAfter(null));

        assertEquals(exception.getMessage(), null);

    }

    @Test
    public void isAfterReturnsTrueTest() throws Exception {
        assertThat(one.isAfter(negative), is(true));
    }

    @Test
    public void isAfterReturnsFalseTest() throws Exception {
        assertThat(fromMinToZero.isAfter(fullRange), is(false));
    }

    @Test
    public void isConcurrentNullArgsTest() throws Exception {
        Throwable exception = assertThrows(NullPointerException.class, () -> another.isConcurrent(null));

        assertEquals(exception.getMessage(), null);

    }

    @Test
    public void isConcurrentReturnsTrueTest() throws Exception {
        assertThat(one.isConcurrent(another), is(true));
        assertThat(another.isConcurrent(one), is(true));
    }

    @Test
    public void isConcurrentReturnsFalseTest() throws Exception {
        assertThat(one.isConcurrent(negative), is(false));
        assertThat(negative.isConcurrent(one), is(false));
    }


    @Test
    public void testThatLowerBoundIsCorrect() throws Exception {
        assertThat(negative.getLowerBound(), is(-100L));
    }

    @Test
    public void testThatLowerBoundIsNotCorrect() throws Exception {
        assertThat(negative.getLowerBound(), is(not(50L)));
    }

    @Test
    public void testThatUpperBoundIsCorrect() throws Exception {
        assertThat(another.getUpperBound(), is(50L));
    }

    @Test
    public void testThatUpperBoundIsNotCorrect() throws Exception {
        assertThat(fullRange.getUpperBound(), is(not(Long.MIN_VALUE)));
    }

    @Test
    public void containsReturnsTrueTest() throws Exception {
        assertThat(negative.contains(-5L), is(true));
        assertThat(negative.contains(-50L), is(true));
        assertThat(negative.contains(-100L), is(true));
    }

    @Test
    public void containsReturnsFalseTest() throws Exception {
        assertThat(one.contains(0L), is(false));
        assertThat(one.contains(26L), is(false));
    }


    @Test
    public void testThatWeCanMakeRangeAsList() throws Exception {
        List<Long> rangeList = one.asList();
        List<Long> newList = new ArrayList<>();

        for (int i = (int) one.getStartInclusive(); i <= (int) one.getEndInclusive(); i++) {
            newList.add((long) i);
        }

        assertArrayEquals(rangeList.toArray(), newList.toArray());
    }

    @Test
    public void asIteratorLogicTest() throws Exception {
        Iterator<Long> iterator = one.asIterator();
        Long l2 = one.getStartInclusive();
        while (iterator.hasNext()) {
            Long l = iterator.next();
            assertThat(l, is(l2));
            l2++;
        }
    }

    @Test
    public void asIteratorHasNextTest() throws Exception {
        Iterator<Long> iterator = another2.asIterator();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(true));
    }

    @Test
    public void asIteratorIOOBExceptionTest() throws Exception {
        Range newRange = new Range(0, 1);
        Iterator<Long> iterator = newRange.asIterator();
        iterator.next();
        iterator.next();

        Throwable exception = assertThrows(IndexOutOfBoundsException.class, iterator::next);

        assertEquals(exception.getMessage(), null);


    }
}