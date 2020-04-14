package project.main.dao;

import project.main.model.VotesCount;

import java.util.List;

public interface MemberVoteDao {
    boolean voteExist(int votingEventId, int voterId, int movieId);
    int updateVote(int votingEventId, int voterId, int movieId, int forOrAgainst);
    int createVote(int votingEventId, int voterId, int movieId, int forOrAgainst);
    List<VotesCount> getVotes(int votingEventId);
}
