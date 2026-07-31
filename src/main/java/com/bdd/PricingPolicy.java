package com.bdd;

public class PricingPolicy {

  private final float pricePerMovie;

  public PricingPolicy(final float pricePerMovie) {
    this.pricePerMovie = pricePerMovie;
  }

  public float priceFor(final Movie... movies) {
    return movies.length * pricePerMovie;
  }

  public boolean isValid(final float price, final Movie... movies) {
    return price == priceFor(movies);
  }
}
