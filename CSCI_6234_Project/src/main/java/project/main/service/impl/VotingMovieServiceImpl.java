package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.VotingMovieDao;
import project.main.service.VotingMovieService;

@Service("votingMovieService")
public class VotingMovieServiceImpl implements VotingMovieService {
    @Autowired
    VotingMovieDao votingMovieDao;

    @Override
    public boolean votingMovieExist(int votingEventId, int movieId) {
        return votingMovieDao.votingMovieExist(votingEventId, movieId);
    }

    @Override
    public int addVotingMovie(int votingEventId, int movieId) {
        return votingMovieDao.addVotingMovie(votingEventId, movieId);
    }
}
