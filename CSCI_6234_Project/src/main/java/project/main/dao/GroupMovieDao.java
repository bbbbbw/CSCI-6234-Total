package project.main.dao;

public interface GroupMovieDao {
    boolean existGroupMovie(int groupId, int movieId);
    int populateMovie(int groupId, int movieId);
    int removeMovie(int groupId, int movieId);
}
