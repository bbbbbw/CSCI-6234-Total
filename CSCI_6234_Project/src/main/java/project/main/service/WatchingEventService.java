package project.main.service;

import javafx.util.Pair;
import project.main.model.WatchingEvent;

import java.text.ParseException;
import java.util.List;

public interface WatchingEventService {
    Pair<String, String> createWatchingEvent(int groupId, String watchDate, String startTime, String endTime, String message) throws ParseException;
    List<WatchingEvent> getWatchingEvent(int groupId);
    WatchingEvent getWatchingEventById(int watchingEventId);
}
