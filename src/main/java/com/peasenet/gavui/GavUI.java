package com.peasenet.gavui;

import com.peasenet.gavui.util.GavUISettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GavUI {
    /**
     * The logger of the library.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger("gavui");

    public static void initialize() {
        GavUISettings.initialize();
        LOGGER.info("GavUI has been initialized.");
    }
}
