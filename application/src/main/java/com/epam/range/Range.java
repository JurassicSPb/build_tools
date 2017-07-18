package com.epam.range;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Getter
@Setter
public class Range implements RangeInterface {
    private long startInclusive;
    private long endInclusive;

    public Range(long startInclusive, long endInclusive) {
        if (startInclusive >= endInclusive) {
            throw new IllegalArgumentException("Start value should be lower than endInclusive");
        }
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    @Override
    public boolean isBefore(Range otherRange) {
        return this.endInclusive < otherRange.startInclusive;
    }

    @Override
    public boolean isAfter(Range otherRange) {
        return this.startInclusive > otherRange.endInclusive;
    }

    @Override
    public boolean isConcurrent(Range otherRange) {
        return !this.isBefore(otherRange) && !this.isAfter(otherRange);
    }

    @Override
    public long getLowerBound() {
        return startInclusive;
    }

    @Override
    public long getUpperBound() {
        return endInclusive;
    }

    @Override
    public boolean contains(long value) {
        return value >= startInclusive && value <= endInclusive;
    }

    @Override
    public List<Long> asList() {
        List <Long> buffer = new ArrayList<>();
        asIterator().forEachRemaining(buffer::add);
        return buffer;
    }

    @Override
    public Iterator<Long> asIterator() {
        return new Iterator<Long>() {
            long currentIndex = startInclusive;

            @Override
            public boolean hasNext() {
                return currentIndex <= endInclusive;
            }

            @Override
            public Long next() {
                if (currentIndex > endInclusive){
                    throw new IndexOutOfBoundsException();
                }
                return currentIndex++;
            }
        };
    }
}