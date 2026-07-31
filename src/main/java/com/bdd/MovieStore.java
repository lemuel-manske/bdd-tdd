package com.bdd;

import java.util.List;

public class MovieStore {

  private static final float PRICE_PER_MOVIE = 8.0f;

  private static final String MSG_RENTAL_SUCCESS = "Aluguel do(s) filme(s) realizado";
  private static final String MSG_RETURN_SUCCESS = "Devolução realizada";
  private static final String MSG_MOVIE_UNAVAILABLE = "Filme indisponível para locação";
  private static final String MSG_RESERVATION_ERROR = "Erro no processamento da reserva";

  private final Inventory inventory = Inventory.withDefaultCatalog();
  private final PricingPolicy pricingPolicy = new PricingPolicy(PRICE_PER_MOVIE);
  private final Logger logger = Logger.getInstance();

  public int stockOf(final Movie movie) {
    return inventory.quantityOf(movie);
  }

  public Rent rent(final Customer customer, final Movie... movies) {
    return rent(customer, pricingPolicy.priceFor(movies), movies);
  }

  public Rent rent(final Customer customer, final float price, final Movie... movies) {
    try {
      ensurePriceIsNotTampered(price, movies);
      ensureAvailability(movies);

      inventory.reserve(movies);

      final Rent rent = new Rent(customer, List.of(movies), price);
      logger.log(MSG_RENTAL_SUCCESS);
      return rent;
    } catch (final SecurityException e) {
      logger.log(MSG_RESERVATION_ERROR);
      throw e;
    } catch (final IllegalStateException e) {
      logger.log(MSG_MOVIE_UNAVAILABLE);
      throw e;
    }
  }

  public void returnRent(final Rent rent) {
    rent.markAsReturned();
    inventory.release(rent.getMovies());
    logger.log(MSG_RETURN_SUCCESS);
  }

  private void ensurePriceIsNotTampered(final float price, final Movie... movies) {
    if (!pricingPolicy.isValid(price, movies)) {
      throw new SecurityException(MSG_RESERVATION_ERROR);
    }
  }

  private void ensureAvailability(final Movie... movies) {
    if (!inventory.hasAvailable(movies)) {
      throw new IllegalStateException(MSG_MOVIE_UNAVAILABLE);
    }
  }
}