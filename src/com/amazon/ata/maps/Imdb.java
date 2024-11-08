package com.amazon.ata.maps;

import java.util.*;

/**
 * Stores the relationships between movies and actors, allowing releasing a new movie
 * with all actors in the cast, adding a single actor to an existing (or new) movie,
 * un-releasing a movie completely, and querying actors by movie and vice versa.
 */
public class Imdb {

    private final Map<Movie, Set<Actor>> movies = new HashMap<>();
    private final Map<Actor, Set<Movie>> actors = new HashMap<>();

    /**
     * Adds the new movie to the set of movies that an actor has appeared in.
     * If the movie already exists in the database, this will overwrite actors
     * associated with the movie with the new values provided.
     *
     * @param movie the movie being released
     * @param actors a set of actors that appear in the movie
     */
    public void releaseMovie(Movie movie, Set<Actor> actors) {
        movies.put(movie, actors);

        for (Actor actor : actors) {
            Set<Movie> movs = this.actors.get(actor);
            if (movs == null) {
                movs = new HashSet<>();
            }

            movs.add(movie);
            this.actors.put(actor, movs);
        }
    }

    /**
     * Removes the given movie from the database, including any actors
     * credited in the movie.
     *
     * @param movie the movie to remove
     * @return true if the movie was removed, false if it wasn't in Imdb
     *         to begin with
     */
    public boolean removeMovie(Movie movie) {
        if (!movies.containsKey(movie)) {
            return false;
        }

        movies.remove(movie);
        for (Map.Entry<Actor, Set<Movie>> ent : actors.entrySet()) {
            ent.getValue().remove(movie);
        }
        return true;
    }

    /**
     * Adds a new movie to the set of movies that an actor has appeared in.
     * If the movie already exists in the database, will add the actor if they haven't been added already.
     * If the movie doesn't yet exist in the database, this will add the movie with the actor as the only credit.
     *
     * @param movie the movie to add to the actors set of movies
     * @param actor the actor that appears in this movie
     */
    public void tagActorInMovie(Movie movie, Actor actor) {
        Set<Actor> acts = movies.containsKey(movie) ? movies.get(movie) : new HashSet<>();
        acts.add(actor);
        movies.put(movie, acts);

        Set<Movie> movs = actors.containsKey(actor) ? actors.get(actor) : new HashSet<>();
        movs.add(movie);
        actors.put(actor, movs);

//        movies.computeIfAbsent(movie, k -> new HashSet<>()).add(actor);
//        actors.computeIfAbsent(actor, k -> new HashSet<>()).add(movie);
    }

    /**
     * Returns a set of actors who are credited in the given movie. If a movie is not
     * released on IMDB throw an IllegalArgumentException.
     *
     * @param movie the movie to get actors for
     * @return the set of actors who are credited in the passed in movie
     */
    public Set<Actor> getActorsInMovie(Movie movie) {
        Set<Actor> actors = movies.get(movie);
        if (actors == null) {
            throw new IllegalArgumentException("Ain't no sunshine when she's gone.");
        }
        return actors;
    }

    /**
     * Returns a set of movies that the specified actor has appeared in. If the
     * actor has not appeared in any movies, return an empty Set.
     *
     * @param actor the actor to get movies for
     * @return the set of movies that the passed in actor has appeared in
     */
    public Set<Movie> getMoviesForActor(Actor actor) {
        Set<Movie> movs = actors.get(actor);
        return movs == null ? Collections.emptySet() : movs;
    }

    /**
     * Returns all actors that IMDB has in its records as having appeared in a movie.
     *
     * @return a set of actors that IMDB has as appeared in movies
     */
    public Set<Actor> getAllActorsInIMDB() {
        return actors.keySet();
    }

    /**
     * Returns the total number of individual movie-actor pairs in the database.
     *
     * So if there are 2 movies, the first movie has 1 actor and the second one
     * has 6 actors, this method will return 7.
     *
     * @return The total number of movie-actor pairings: the number of times
     *         any actor has appeared in any movie
     */
    public int getTotalNumCredits() {
        int count = 0;
        for (Set<Actor> actors : movies.values()) {
            count += actors.size();
        }
        return count;
    }
}
