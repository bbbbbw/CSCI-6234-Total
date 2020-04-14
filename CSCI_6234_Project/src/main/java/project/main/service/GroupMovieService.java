package project.main.service;

public interface GroupMovieService {
    boolean existGroupMovie(int groupId, int movieId);
    int populateMovie(int groupId, int movieId);
    int removeMovie(int groupId, int movieId);
}
