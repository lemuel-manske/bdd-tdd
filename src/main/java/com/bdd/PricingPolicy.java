package com.bdd;

final class PricingPolicy {

  private final float pricePerMovie;

  PricingPolicy(final float pricePerMovie) {
    this.pricePerMovie = pricePerMovie;
  }

  float priceFor(final Movie... movies) {
    return movies.length * pricePerMovie;
  }

  boolean isValid(final float price, final Movie... movies) {
    return price == priceFor(movies);
  }
}