package project.main.service;

import javafx.util.Pair;
import project.main.model.VotingEvent;
import project.main.model.WatchingEvent;

import java.text.ParseException;

public interface VotingEventService {
    int createVotingEvent(int watchingEventId, String startDate, String startTime, String endDate, String endTime);
    int createVotingEvent(WatchingEvent watchingEvent) throws ParseException;
    VotingEvent getVotingEvent(int watchingEventId);
    Pair<String, String> updateVotingEvent(WatchingEvent we, String startDate, String startTime, String endDate, String endTime) throws ParseException;
    int updateWinner(int votingEventId, int movieId);
}