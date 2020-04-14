package project.main.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.VotingEventDao;
import project.main.model.VotingEvent;
import project.main.model.WatchingEvent;
import project.main.service.VotingEventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("votingEventService")
public class VotingEventServiceImpl implements VotingEventService {
    @Autowired
    VotingEventDao votingEventDao;

    @Override
    public int createVotingEvent(int watchingEventId, String startDate, String startTime, String endDate, String endTime) {
        String startAt = startDate + " " + startTime + ":00";
        String endAt = endDate + " " + endTime + ":00";
        return votingEventDao.createVotingEvent(watchingEventId, startAt, endAt);
    }

    @Override
    public int createVotingEvent(WatchingEvent watchingEvent) throws ParseException {
        int watchingEventId = watchingEvent.getId();
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date current = new Date();
        // Start the voting 1 hour later
        Date oneHourLater = new Date(current.getTime() + 60 * 60 * 1000);
        String startAt = sft.format(oneHourLater);
        Date watchingEventStart = sft.parse(watchingEvent.getStartTime());
        // End the voting 6 hours before watching event starts
        Date endDate = new Date(watchingEventStart.getTime() - 6 * 60 * 60 * 1000);
        String endAt = sft.format(endDate);
        return votingEventDao.createVotingEvent(watchingEventId, startAt, endAt);
    }

    @Override
    public VotingEvent getVotingEvent(int watchingEventId) {
        return votingEventDao.getVotingEvent(watchingEventId);
    }

    @Override
    public Pair<String, String> updateVotingEvent(WatchingEvent we, String startDate, String startTime, String endDate, String endTime) throws ParseException {
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startAt = startDate + " " + startTime + ":00";
        String endAt = endDate + " " + endTime + ":00";

        String status = "";
        String msg = "";
        if (sft.parse(startAt).after(sft.parse(endAt))) {
            status = "fail";
            msg = "You cannot make the start time later than the end time!";
            return new Pair<String, String>(status, msg);
        }
        String last = we.getStartTime();
        if (sft.parse(last).before(sft.parse(endAt))) {
            status = "fail";
            msg = "You cannot let the voting event ends after watching event starts!";
            return new Pair<String, String>(status, msg);
        }

        votingEventDao.updateVotingEvent(we.getId(), startAt, endAt);
        status = "success";
        msg = "You have successfully updated the voting event!";
        return new Pair<String, String>(status, msg);
    }

    @Override
    public int updateWinner(int votingEventId, int movieId) {
        return votingEventDao.updateVotingEventWinner(votingEventId, movieId);
    }

}
