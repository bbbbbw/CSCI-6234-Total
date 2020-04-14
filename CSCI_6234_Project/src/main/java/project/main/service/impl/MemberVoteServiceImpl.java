package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.MemberVoteDao;
import project.main.model.VotesCount;
import project.main.service.MemberVoteService;

import java.util.List;

@Service("memberVoteService")
public class MemberVoteServiceImpl implements MemberVoteService {
    @Autowired
    MemberVoteDao memberVoteDao;

    @Override
    public boolean voteExist(int votingEventId, int voterId, int movieId) {
        return memberVoteDao.voteExist(votingEventId, voterId, movieId);
    }

    @Override
    public int updateVote(int votingEventId, int voterId, int movieId, int forOrAgainst) {
        return memberVoteDao.updateVote(votingEventId, voterId, movieId, forOrAgainst);
    }

    @Override
    public int createVote(int votingEventId, int voterId, int movieId, int forOrAgainst) {
        return memberVoteDao.createVote(votingEventId, voterId, movieId, forOrAgainst);
    }

    @Override
    public List<VotesCount> getVotes(int votingEventId) {
        return memberVoteDao.getVotes(votingEventId);
    }
}
