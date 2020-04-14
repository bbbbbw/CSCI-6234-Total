package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.GroupMovieDao;
import project.main.service.GroupMovieService;

@Service("groupMovieService")
public class GroupMovieServiceImpl implements GroupMovieService {
    @Autowired
    GroupMovieDao groupMovieDao;

    @Override
    public boolean existGroupMovie(int groupId, int movieId) {
        return groupMovieDao.existGroupMovie(groupId, movieId);
    }

    @Override
    public int populateMovie(int groupId, int movieId) {
        return groupMovieDao.populateMovie(groupId, movieId);
    }

    @Override
    public int removeMovie(int groupId, int movieId) {
        return groupMovieDao.removeMovie(groupId, movieId);
    }
}
