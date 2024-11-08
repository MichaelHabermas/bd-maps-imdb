package com.amazon.ata.maps;

import java.time.LocalDate;

/**
 * Represents a movie in IMDB. Each movie's name must be unique.
 */
public class Movie {
    private final String name;
    private final String director;
    private final LocalDate yearReleased;

    /**
     * Constructs a new movie with the given parameters.
     *
     * @param name Movie's name. Must be unique
     * @param director Movie's director. May be null
     * @param yearReleased Movie's release year. May be null
     */
    public Movie(String name, String director, LocalDate yearReleased) {
        this.name = name;
        this.director = director;
        this.yearReleased = yearReleased;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" + "Director: " + director + "\n" + "Release date: " + yearReleased;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(Movie.class)) {
            return false;
        }
        Movie movie = (Movie) obj;
        return movie.getName().equals(getName());
    }
}
