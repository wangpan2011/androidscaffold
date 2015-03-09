/*
 * Copyright (c) 2013 Noveo Group
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * Except as contained in this notice, the name(s) of the above copyright holders shall not be used in advertising or
 * otherwise to promote the sale, use or other dealings in this Software without prior written authorization.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pppark.framework.logging;

import android.util.Log;

/**
 * Helper for sending log messages using the standard {@link Log}.
 */
public abstract class Logger {

    /**
     * Case insensitive String constant used to retrieve the name of the root logger.
     */
    public static final String DEFAULT_LOGGER_NAME = "Logger";

    /**
     * Enumeration of priorities of log messages.
     */
    public static enum Level {

        /**
         * Corresponding constant for {@link Log#VERBOSE}.
         * 
         * @see Logger#v(Throwable, String, Object...)
         * @see Logger#v(String, Object...)
         * @see Logger#v(Throwable)
         */
        VERBOSE(Log.VERBOSE),
        /**
         * Corresponding constant for {@link Log#DEBUG}.
         * 
         * @see Logger#d(Throwable, String, Object...)
         * @see Logger#d(String, Object...)
         * @see Logger#d(Throwable)
         */
        DEBUG(Log.DEBUG),
        /**
         * Corresponding constant for {@link Log#INFO}.
         * 
         * @see Logger#i(Throwable, String, Object...)
         * @see Logger#i(String, Object...)
         * @see Logger#i(Throwable)
         */
        INFO(Log.INFO),
        /**
         * Corresponding constant for {@link Log#WARN}.
         * 
         * @see Logger#w(Throwable, String, Object...)
         * @see Logger#w(String, Object...)
         * @see Logger#w(Throwable)
         */
        WARN(Log.WARN),
        /**
         * Corresponding constant for {@link Log#ERROR}.
         * 
         * @see Logger#e(Throwable, String, Object...)
         * @see Logger#e(String, Object...)
         * @see Logger#e(Throwable)
         */
        ERROR(Log.ERROR),
        /**
         * Corresponding constant for {@link Log#ASSERT}.
         * 
         * @see Logger#a(Throwable, String, Object...)
         * @see Logger#a(String, Object...)
         * @see Logger#a(Throwable)
         */
        ASSERT(Log.ASSERT);

        private final int intValue;

        private Level(int intValue) {
            this.intValue = intValue;
        }

        /**
         * Returns int value of the priority as it declared in {@link Log}.
         * 
         * @return The integer value of corresponding constant from {@link Log} class.
         */
        public int intValue() {
            return intValue;
        }

        /**
         * Returns whether this log level includes the specified one or not.
         * 
         * @param level
         *            the level.
         * @return {@code true} if the specified level is included into this one.
         */
        public boolean includes(Level level) {
            return level != null && this.intValue() <= level.intValue();
        }

    }

    /**
     * Returns the name of this logger.
     * 
     * @return the logger name.
     */
    public abstract String getName();

    /**
     * Checks if the specified log level is enabled or not for this logger.
     * 
     * @param level
     *            the level.
     * @return Are messages with this level allowed to be logged or not.
     */
    public abstract boolean isEnabled(Level level);

    /**
     * Low-level logging call.
     * 
     * @param level
     *            a level of this log message
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void print(Level level, String message, Throwable throwable);

    /**
     * Low-level logging call.
     * 
     * @param level
     *            a level of this log message
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void print(Level level, Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#VERBOSE} log message.
     * 
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void v(String message, Throwable throwable);

    /**
     * Send a {@link Level#DEBUG} log message.
     * 
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void d(String message, Throwable throwable);

    /**
     * Send a {@link Level#INFO} log message.
     * 
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void i(String message, Throwable throwable);

    /**
     * Send a {@link Level#WARN} log message.
     * 
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void w(String message, Throwable throwable);

    /**
     * Send a {@link Level#ERROR} log message.
     * 
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void e(String message, Throwable throwable);

    /**
     * Send a {@link Level#ASSERT} log message.
     * 
     * @param message
     *            a message you would like logged.
     * @param throwable
     *            an additional throwable object.
     */
    public abstract void a(String message, Throwable throwable);

    /**
     * Send a {@link Level#VERBOSE} log message.
     * 
     * @param throwable
     *            a throwable object to send.
     */
    public abstract void v(Throwable throwable);

    /**
     * Send a {@link Level#DEBUG} log message.
     * 
     * @param throwable
     *            a throwable object to send.
     */
    public abstract void d(Throwable throwable);

    /**
     * Send a {@link Level#INFO} log message.
     * 
     * @param throwable
     *            a throwable object to send.
     */
    public abstract void i(Throwable throwable);

    /**
     * Send a {@link Level#WARN} log message.
     * 
     * @param throwable
     *            a throwable object to send.
     */
    public abstract void w(Throwable throwable);

    /**
     * Send a {@link Level#ERROR} log message.
     * 
     * @param throwable
     *            a throwable object to send.
     */
    public abstract void e(Throwable throwable);

    /**
     * Send a {@link Level#ASSERT} log message.
     * 
     * @param throwable
     *            a throwable object to send.
     */
    public abstract void a(Throwable throwable);

    /**
     * Send a {@link Level#VERBOSE} log message.
     * 
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void v(Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#DEBUG} log message.
     * 
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void d(Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#INFO} log message.
     * 
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void i(Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#WARN} log message.
     * 
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void w(Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#ERROR} log message.
     * 
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void e(Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#ASSERT} log message.
     * 
     * @param throwable
     *            an additional throwable object.
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void a(Throwable throwable, String messageFormat, Object... args);

    /**
     * Send a {@link Level#VERBOSE} log message.
     * 
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void v(String messageFormat, Object... args);

    /**
     * Send a {@link Level#DEBUG} log message.
     * 
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void d(String messageFormat, Object... args);

    /**
     * Send a {@link Level#INFO} log message.
     * 
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void i(String messageFormat, Object... args);

    /**
     * Send a {@link Level#WARN} log message.
     * 
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void w(String messageFormat, Object... args);

    /**
     * Send a {@link Level#ERROR} log message.
     * 
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void e(String messageFormat, Object... args);

    /**
     * Send a {@link Level#ASSERT} log message.
     * 
     * @param messageFormat
     *            a message format you would like logged.
     * @param args
     *            arguments for a formatter.
     */
    public abstract void a(String messageFormat, Object... args);

    public static Logger getLogger(Class<?> aClass) {
        return LoggerManager.getLogger(aClass == null ? null : aClass.getName());
    }

    public static Logger getLogger(String name) {
        return LoggerManager.getLogger(name);
    }

    public abstract void verbose(String message);

    public abstract void warn(String message);

    public abstract void debug(String message);

    public abstract void info(String message);

    public abstract void error(String message);

    public abstract void verbose(String messageFormat, Object arg1);

    public abstract void warn(String messageFormat, Object arg1);

    public abstract void debug(String messageFormat, Object arg1);

    public abstract void info(String messageFormat, Object arg1);

    public abstract void error(String messageFormat, Object arg1);

    public abstract void verbose(String messageFormat, Object arg1, Object arg2);

    public abstract void warn(String messageFormat, Object arg1, Object arg2);

    public abstract void debug(String messageFormat, Object arg1, Object arg2);

    public abstract void info(String messageFormat, Object arg1, Object arg2);

    public abstract void error(String messageFormat, Object arg1, Object arg2);

    public abstract void verbose(String message, Throwable throwable);

    public abstract void warn(String message, Throwable throwable);

    public abstract void debug(String message, Throwable throwable);

    public abstract void info(String message, Throwable throwable);

    public abstract void error(String message, Throwable throwable);

    public abstract void verbose(Throwable throwable);

    public abstract void warn(Throwable throwable);

    public abstract void debug(Throwable throwable);

    public abstract void info(Throwable throwable);

    public abstract void error(Throwable throwable);

    public abstract void verbose(Throwable throwable, String messageFormat, Object... args);

    public abstract void warn(Throwable throwable, String messageFormat, Object... args);

    public abstract void debug(Throwable throwable, String messageFormat, Object... args);

    public abstract void info(Throwable throwable, String messageFormat, Object... args);

    public abstract void error(Throwable throwable, String messageFormat, Object... args);

    public abstract void verbose(String messageFormat, Object... args);

    public abstract void debug(String messageFormat, Object... args);

    public abstract void info(String messageFormat, Object... args);

    public abstract void warn(String messageFormat, Object... args);

    public abstract void error(String messageFormat, Object... args);

}
