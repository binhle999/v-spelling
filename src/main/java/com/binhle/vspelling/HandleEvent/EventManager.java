package com.binhle.vspelling.HandleEvent;

import com.binhle.vspelling.Common.Constants;
import com.binhle.vspelling.HandleEvent.Implementation.AlphaBetDetailEventHandler;
import com.binhle.vspelling.HandleEvent.Implementation.AlphaBetEventHandler;
import com.binhle.vspelling.HandleEvent.Implementation.SpellingListEventHandler;

/**
 * Created by BinhLe on 4/16/2017.
 */
public class EventManager {
    private static EventManager instance;

    private EventManager() {}
    public static synchronized EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }
    public static CustomizeEventHandler getEventHandler(String screenName) {
        CustomizeEventHandler customizeEventHandler = null;
        switch (screenName) {
            case Constants.SCREEN_ALPHA_BETA:
                customizeEventHandler = AlphaBetEventHandler.getInstance();
                break;
            case Constants.SCREEN_SPELLING_LIST:
                customizeEventHandler = SpellingListEventHandler.getInstance();
                break;
            case Constants.SCREEN_ALPHA_BETA_DETAIL:
                customizeEventHandler = AlphaBetDetailEventHandler.getInstance();
                break;
        }
        return customizeEventHandler;
    }
}
