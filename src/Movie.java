
/**
 * Movie.java
 * -----------
 * This class represents a single Movie.
 * It stores basic details about a movie like title, genre, duration and price.
 * This is a POJO (Plain Old Java Object) - simple class with fields, constructor, getters.
 */
public class Movie {

    // Unique id for the movie (e.g., 1, 2, 3)
    private int id;

    // Name of the movie (e.g., "Avengers")
    private String title;

    // Type of movie (e.g., Action, Comedy, Drama)
    private String genre;

    // How long the movie is (e.g., "2h 30m")
    private String duration;

    // Base price for one ticket for this movie
    private double basePrice;

    /**
     * Constructor - used to create a new Movie object
     * @param id        Unique id
     * @param title     Movie name
     * @param genre     Movie genre
     * @param duration  Movie duration string
     * @param basePrice Base ticket price
     */
    public Movie(int id, String title, String genre, String duration, double basePrice) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.basePrice = basePrice;
    }

    // Getter methods - to get values from outside the class (Encapsulation)

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public double getBasePrice() {
        return basePrice;
    }

    /**
     * toString() is called when we display Movie in JComboBox
     * We return title so dropdown shows movie name nicely
     */
    @Override
    public String toString() {
        return title + " (" + genre + ") - $" + basePrice;
    }
}
