package com.bdd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Inventory {

  private final Map<Movie, Integer> quantities;

  private Inventory(final Map<Movie, Integer> quantities) {
    this.quantities = quantities;
  }

  static Inventory withDefaultCatalog() {
    final Map<Movie, Integer> initialStock = new HashMap<>();
    initialStock.put(Movie.LORD_OF_THE_RINGS_1, 5);
    initialStock.put(Movie.LORD_OF_THE_RINGS_2, 5);
    initialStock.put(Movie.LORD_OF_THE_RINGS_3, 5);
    initialStock.put(Movie.HOBBIT_1, 0);
    initialStock.put(Movie.HOBBIT_2, 1);
    initialStock.put(Movie.PROJECT_HAIL_MARY, 3);
    return new Inventory(initialStock);
  }

  int quantityOf(final Movie movie) {
    return quantities.getOrDefault(movie, 0);
  }

  boolean hasAvailable(final Movie... movies) {
    for (final Movie movie : movies) {
      if (quantityOf(movie) <= 0) {
        return false;
      }
    }
    return true;
  }

  void reserve(final Movie... movies) {
    for (final Movie movie : movies) {
      quantities.put(movie, quantityOf(movie) - 1);
    }
  }

  void release(final List<Movie> movies) {
    for (final Movie movie : movies) {
      quantities.put(movie, quantityOf(movie) + 1);
    }
  }
}