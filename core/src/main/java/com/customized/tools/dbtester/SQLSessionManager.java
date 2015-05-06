package com.customized.tools.dbtester;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

public final class SQLSessionManager {
	
	private final SortedMap<String,SQLSession> sessions;
    private SQLSession currentSession;
    
    private SQLSessionManager() {
    	sessions = new TreeMap<>();
    }
    
    private SQLSessionManager(SQLSession session) {
    	sessions = new TreeMap<>();
    	addSession(UUID.randomUUID().toString(), session);
    }
    
    public void addSession(String name, SQLSession session) {
        sessions.put(name, session);
    }
    
    public SQLSession removeSessionWithName(String name) {
        return sessions.remove(name);
    }
    
    public SQLSession getSessionByName(String name) {
        return sessions.get(name);
    }
    
    public String getFirstSessionName() {
        return sessions.firstKey();
    }
    
    public int getSessionCount () {
        return sessions.size();
    }
    
    public boolean hasSessions() {
        return !sessions.isEmpty();
    }
    
    public boolean sessionNameExists(String sessionName) {
        return sessions.containsKey(sessionName);
    }
    
    public void setCurrentSession(SQLSession session) {
        this.currentSession = session;
    }
    
    public SQLSession getCurrentSession() {
        return currentSession == null ? sessions.get(getFirstSessionName()) : currentSession;
    }
    
    public boolean closeCurrentSession() {
        currentSession.close();
        return removeSession(currentSession);
    }
    
    private boolean removeSession(SQLSession session) {
        boolean result = false;
        Map.Entry<?,?> entry = null;
        Iterator<Entry<String, SQLSession>> it = sessions.entrySet().iterator();
        while (it.hasNext()) {
            entry = it.next();
            if (entry.getValue() == session) {
                it.remove();
                result = true;
                break;
            }
        }
        return result;
    }
    
    public void closeAll() {
        Iterator<SQLSession> sessIter = sessions.values().iterator();
        while (sessIter.hasNext()) {
            SQLSession session = sessIter.next();
            session.close();
        }
    }
    
    public static class Factory {
    	
    	public static SQLSessionManager create() {
    		return new SQLSessionManager();
    	}
    	
    	public static SQLSessionManager create(SQLSession session) {
    		return new SQLSessionManager(session);
    	}
    }

}
