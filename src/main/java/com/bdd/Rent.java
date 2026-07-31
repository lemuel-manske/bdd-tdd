package com.bdd;

import java.time.LocalDate;
import java.util.List;

public class Rent {

  private static final int DELIVER_DAYS = 7;

  private final Customer customer;
  private final List<Movie> movies;
  private final float price;
  private final LocalDate deliverDate;

  private LocalDate returnDate;

  Rent(final Customer customer,
       final List<Movie> movies,
       final float price) {
    this.customer = customer;
    this.movies = movies;
    this.price = price;

    this.deliverDate = LocalDate.now().plusDays(DELIVER_DAYS);
  }

  public boolean hasFine() {
    final LocalDate effectiveReturnMoment = returnDate != null
      ? returnDate
      : LocalDate.now();

    return effectiveReturnMoment.isAfter(deliverDate);
  }

  public LocalDate getDeliverDate() {
    return deliverDate;
  }

  public List<Movie> getMovies() {
    return movies;
  }

  public Customer getCustomer() {
    return customer;
  }

  public float getPrice() {
    return price;
  }

  void markAsReturned() {
    this.returnDate = LocalDate.now();
  }
}