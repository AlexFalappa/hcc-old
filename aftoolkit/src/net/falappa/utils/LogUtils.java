/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.falappa.utils;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class of static utility methods for logging purposes.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class LogUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private LogUtils() {
    }

    /**
     * Completely disable java.util.logging API loggers.
     */
    public void silenceJUL() {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
    }
}
