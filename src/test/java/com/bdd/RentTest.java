package com.bdd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RentTest {

  private final Logger logger = Logger.getInstance();

  private MovieStore movieStore;
  private Customer customer;

  @BeforeEach
  void setUp() {
    movieStore = new MovieStore();
    customer = new Customer("John Doe");
  }

  @Test
  void shouldRentAMovie() { // Cenário 1
    int stock = movieStore.stockOf(Movie.LORD_OF_THE_RINGS_1);

    Rent rent = movieStore.rent(
      customer,
      Movie.LORD_OF_THE_RINGS_1
    );

    assertAll(
      () -> assertLastLogMessage("Aluguel do(s) filme(s) realizado"),
      () -> assertDeliverDaysCountdown(rent, 7),

      () -> assertEquals(
        stock - 1,
        movieStore.stockOf(Movie.LORD_OF_THE_RINGS_1))
    );
  }

  @Test
  void shouldRentMultipleMovies() { // Cenário 2
    int lotr1Stock = movieStore.stockOf(Movie.LORD_OF_THE_RINGS_1);
    int lotr2Stock = movieStore.stockOf(Movie.LORD_OF_THE_RINGS_2);
    int lotr3Stock = movieStore.stockOf(Movie.LORD_OF_THE_RINGS_3);

    Rent rent = movieStore.rent(
      customer,
      Movie.LORD_OF_THE_RINGS_1,
      Movie.LORD_OF_THE_RINGS_2,
      Movie.LORD_OF_THE_RINGS_3
    );

    assertAll(
      () -> assertLastLogMessage("Aluguel do(s) filme(s) realizado"),
      () -> assertDeliverDaysCountdown(rent, 7),

      () -> assertEquals(
        lotr1Stock - 1,
        movieStore.stockOf(Movie.LORD_OF_THE_RINGS_1)),

      () -> assertEquals(
        lotr2Stock - 1,
        movieStore.stockOf(Movie.LORD_OF_THE_RINGS_2)),

      () -> assertEquals(
        lotr3Stock - 1,
        movieStore.stockOf(Movie.LORD_OF_THE_RINGS_3)),

      () -> assertEquals(3, rent.getMovies().size())
    );
  }

  @Test
  void shouldReturnMovieOnDueDate() { // Cenário 3
    int stock = movieStore.stockOf(Movie.PROJECT_HAIL_MARY);

    Rent rent = movieStore.rent(
      customer,
      Movie.PROJECT_HAIL_MARY
    );

    movieStore.returnRent(rent);

    assertAll(
      () -> assertLastLogMessage("Devolução realizada"),
      () -> assertFalse(rent.hasFine()),

      () -> assertEquals(
        stock,
        movieStore.stockOf(Movie.PROJECT_HAIL_MARY))
    );
  }

  @Test
  void shouldNotRentMovieWithoutAvailableCopies() { // Cenário 4
    int stock = movieStore.stockOf(Movie.HOBBIT_1);

    assertEquals(0, stock);

    assertThrows(
      IllegalStateException.class,
      () -> movieStore.rent(
        customer,
        Movie.HOBBIT_1)
    );

    assertAll(
      () -> assertLastLogMessage("Filme indisponível para locação"),

      () -> assertEquals(
        0,
        movieStore.stockOf(Movie.HOBBIT_1))
    );
  }

  @Test
  void shouldRejectTamperedRentalPrice() { // Cenário 5
    float tamperedPrice = 0.01f;

    assertThrows(
      SecurityException.class,
      () -> movieStore.rent(
        customer,
        tamperedPrice,
        Movie.HOBBIT_2
      )
    );

    assertAll(
      () -> assertLastLogMessage("Erro no processamento da reserva"),

      () -> assertEquals(
        1,
        movieStore.stockOf(Movie.HOBBIT_2))
    );
  }

  private void assertDeliverDaysCountdown(Rent rent, int expectedDays) {
    LocalDate expected = LocalDate.now().plusDays(expectedDays);

    assertEquals(expected, rent.getDeliverDate());
  }

  private void assertLastLogMessage(String expectedMessage) {
    assertEquals(expectedMessage, logger.getLastMessage());
  }
}
