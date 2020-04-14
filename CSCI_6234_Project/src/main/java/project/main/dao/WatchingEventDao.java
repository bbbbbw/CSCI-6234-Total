package project.main.dao;

import project.main.model.WatchingEvent;

import java.util.List;

public interface WatchingEventDao {
    int createWatchingEvent(int groupId, String newStartTime, String newEndTime, String message);
    List<WatchingEvent> getWatchingEvent(int groupId);
    WatchingEvent getWatchingEventById(int watchingEventId);
}
