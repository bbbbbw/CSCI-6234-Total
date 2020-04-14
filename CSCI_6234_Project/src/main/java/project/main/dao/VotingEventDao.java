package project.main.dao;

import project.main.model.VotingEvent;

public interface VotingEventDao {
    int createVotingEvent(int watchingEventId, String startAt, String endAt);
    VotingEvent getVotingEvent(int watchingEventId);
    int updateVotingEvent(int watchingEventId, String startAt, String endAt);
    int updateVotingEventWinner(int votingEventId, int winnerMovieId);
}
