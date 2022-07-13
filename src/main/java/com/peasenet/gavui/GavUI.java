package com.peasenet.gavui;

import com.peasenet.gavui.util.GavUISettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gt3ch1
 * @version 7/13/2022
 * The main initializer class for GavUI
 */
public class GavUI {
    /**
     * The logger of the library.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger("gavui");

    /**
     * Initializes GavUI
     */
    public static void initialize() {
        GavUISettings.initialize();
        LOGGER.info("GavUI has been initialized.");
    }
}
