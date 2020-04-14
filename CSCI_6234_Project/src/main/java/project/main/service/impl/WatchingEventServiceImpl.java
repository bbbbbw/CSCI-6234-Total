package project.main.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.main.dao.WatchingEventDao;
import project.main.model.WatchingEvent;
import project.main.service.WatchingEventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service("watchingEventService")
public class WatchingEventServiceImpl implements WatchingEventService {
    @Autowired
    WatchingEventDao watchingEventDao;

    @Override
    public Pair<String, String> createWatchingEvent(int groupId, String watchDate, String startTime, String endTime, String message) throws ParseException {
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newStartTime = watchDate + " " + startTime + ":00";
        String newEndTime = watchDate + " " + endTime + ":00";

        String status = "";
        String msg = "";
        if (sft.parse(newStartTime).after(sft.parse(newEndTime))) {
            status = "fail";
            msg = "You cannot make the start time later than the end time!";
            return new Pair<String, String>(status, msg);
        }

        watchingEventDao.createWatchingEvent(groupId, newStartTime, newEndTime, message);
        status = "success";
        msg = "You have successfully created a watching event!";
        return new Pair<String, String>(status, msg);
    }

    @Override
    public List<WatchingEvent> getWatchingEvent(int groupId) {
        return watchingEventDao.getWatchingEvent(groupId);
    }

    @Override
    public WatchingEvent getWatchingEventById(int watchingEventId) {
        return watchingEventDao.getWatchingEventById(watchingEventId);
    }
}
