package project.main.service;

import project.main.model.VotesCount;

import java.util.List;

public interface MemberVoteService {
    boolean voteExist(int votingEventId, int voterId, int movieId);
    int updateVote(int votingEventId, int voterId, int movieId, int forOrAgainst);
    int createVote(int votingEventId, int voterId, int movieId, int forOrAgainst);
    List<VotesCount> getVotes(int votingEventId);
}
