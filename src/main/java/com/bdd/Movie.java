package com.bdd;

public class Movie {

  public static final Movie LORD_OF_THE_RINGS_1 = new Movie("O Senhor dos Anéis: A Sociedade do Anel");
  public static final Movie LORD_OF_THE_RINGS_2 = new Movie("O Senhor dos Anéis: As Duas Torres");
  public static final Movie LORD_OF_THE_RINGS_3 = new Movie("O Senhor dos Anéis: O Retorno do Rei");

  public static final Movie HOBBIT_1 = new Movie("O Hobbit: Uma Jornada Inesperada");
  public static final Movie HOBBIT_2 = new Movie("O Hobbit: A Desolação de Smaug");

  public static final Movie PROJECT_HAIL_MARY = new Movie("Devoradores de Estrelas");

  private final String title;

  public Movie(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
