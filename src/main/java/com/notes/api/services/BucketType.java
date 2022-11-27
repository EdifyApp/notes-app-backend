package com.notes.api.services;

/*
* An enum type to map bucket number to review interval size*/
public enum BucketType {
    One(1L),
    Two(2L),
    Three(4L),
    Four(5L),
    Five(6L) {
      @Override
      public BucketType next() {
          return this;
      }
    };

    private final long interval;
    BucketType(long interval) {
        this.interval = interval;
    }

    public BucketType next() {
        return values()[ordinal()+1];
    }

    public BucketType reset() {
        return values()[0];
    }

    public long getInterval() {
        return interval;
    }
}
