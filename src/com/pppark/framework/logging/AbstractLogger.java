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

/**
 * Abstract implementation of {@link Logger} interface.
 */
public abstract class AbstractLogger extends Logger {

    private final String name;

    /**
     * Constructor of {@link AbstractLogger}.
     * 
     * @param name
     *            the name of the logger.
     */
    public AbstractLogger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void v(String message, Throwable throwable) {
        print(Logger.Level.VERBOSE, message, throwable);
    }

    @Override
    public void d(String message, Throwable throwable) {
        print(Logger.Level.DEBUG, message, throwable);
    }

    @Override
    public void i(String message, Throwable throwable) {
        print(Logger.Level.INFO, message, throwable);
    }

    @Override
    public void w(String message, Throwable throwable) {
        print(Logger.Level.WARN, message, throwable);
    }

    @Override
    public void e(String message, Throwable throwable) {
        print(Logger.Level.ERROR, message, throwable);
    }

    @Override
    public void a(String message, Throwable throwable) {
        print(Logger.Level.ASSERT, message, throwable);
    }

    @Override
    public void v(Throwable throwable) {
        print(Logger.Level.VERBOSE, null, throwable);
    }

    @Override
    public void d(Throwable throwable) {
        print(Logger.Level.DEBUG, null, throwable);
    }

    @Override
    public void i(Throwable throwable) {
        print(Logger.Level.INFO, null, throwable);
    }

    @Override
    public void w(Throwable throwable) {
        print(Logger.Level.WARN, null, throwable);
    }

    @Override
    public void e(Throwable throwable) {
        print(Logger.Level.ERROR, null, throwable);
    }

    @Override
    public void a(Throwable throwable) {
        print(Logger.Level.ASSERT, null, throwable);
    }

    @Override
    public void v(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.VERBOSE, throwable, messageFormat, args);
    }

    @Override
    public void d(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.DEBUG, throwable, messageFormat, args);
    }

    @Override
    public void i(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.INFO, throwable, messageFormat, args);
    }

    @Override
    public void w(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.WARN, throwable, messageFormat, args);
    }

    @Override
    public void e(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.ERROR, throwable, messageFormat, args);
    }

    @Override
    public void a(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.ASSERT, throwable, messageFormat, args);
    }

    @Override
    public void v(String messageFormat, Object... args) {
        print(Logger.Level.VERBOSE, null, messageFormat, args);
    }

    @Override
    public void d(String messageFormat, Object... args) {
        print(Logger.Level.DEBUG, null, messageFormat, args);
    }

    @Override
    public void i(String messageFormat, Object... args) {
        print(Logger.Level.INFO, null, messageFormat, args);
    }

    @Override
    public void w(String messageFormat, Object... args) {
        print(Logger.Level.WARN, null, messageFormat, args);
    }

    @Override
    public void e(String messageFormat, Object... args) {
        print(Logger.Level.ERROR, null, messageFormat, args);
    }

    @Override
    public void a(String messageFormat, Object... args) {
        print(Logger.Level.ASSERT, null, messageFormat, args);
    }

    @Override
    public void verbose(String message) {
        print(Logger.Level.VERBOSE, message, null);
    }

    @Override
    public void warn(String message) {
        print(Logger.Level.WARN, message, null);
    }

    @Override
    public void debug(String message) {
        print(Logger.Level.DEBUG, message, null);
    }

    @Override
    public void info(String message) {
        print(Logger.Level.INFO, message, null);
    }

    @Override
    public void error(String message) {
        print(Logger.Level.ERROR, message, null);
    }

    @Override
    public void verbose(String messageFormat, Object arg1) {
        print(Logger.Level.VERBOSE, null, messageFormat, arg1);
    }

    @Override
    public void warn(String messageFormat, Object arg1) {
        print(Logger.Level.WARN, null, messageFormat, arg1);
    }

    @Override
    public void debug(String messageFormat, Object arg1) {
        print(Logger.Level.DEBUG, null, messageFormat, arg1);
    }

    @Override
    public void info(String messageFormat, Object arg1) {
        print(Logger.Level.INFO, null, messageFormat, arg1);
    }

    @Override
    public void error(String messageFormat, Object arg1) {
        print(Logger.Level.ERROR, null, messageFormat, arg1);
    }

    @Override
    public void verbose(String messageFormat, Object arg1, Object arg2) {
        print(Logger.Level.VERBOSE, null, messageFormat, arg1, arg2);
    }

    @Override
    public void warn(String messageFormat, Object arg1, Object arg2) {
        print(Logger.Level.WARN, null, messageFormat, arg1, arg2);
    }

    @Override
    public void debug(String messageFormat, Object arg1, Object arg2) {
        print(Logger.Level.DEBUG, null, messageFormat, arg1, arg2);
    }

    @Override
    public void info(String messageFormat, Object arg1, Object arg2) {
        print(Logger.Level.INFO, null, messageFormat, arg1, arg2);
    }

    @Override
    public void error(String message, Object arg1, Object arg2) {
        print(Logger.Level.ERROR, null, message, arg1, arg2);
    }

    @Override
    public void verbose(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.VERBOSE, throwable, messageFormat, args);
    }

    @Override
    public void warn(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.WARN, throwable, messageFormat, args);
    }

    @Override
    public void debug(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.DEBUG, throwable, messageFormat, args);
    }

    @Override
    public void info(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.INFO, throwable, messageFormat, args);
    }

    @Override
    public void error(Throwable throwable, String messageFormat, Object... args) {
        print(Logger.Level.ERROR, throwable, messageFormat, args);
    }

    @Override
    public void verbose(String message, Throwable throwable) {
        print(Logger.Level.VERBOSE, message, throwable);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        print(Logger.Level.WARN, message, throwable);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        print(Logger.Level.DEBUG, message, throwable);
    }

    @Override
    public void info(String message, Throwable throwable) {
        print(Logger.Level.INFO, message, throwable);
    }

    @Override
    public void error(String message, Throwable throwable) {
        print(Logger.Level.ERROR, message, throwable);
    }

    @Override
    public void verbose(Throwable throwable) {
        print(Logger.Level.VERBOSE, null, throwable);
    }

    @Override
    public void warn(Throwable throwable) {
        print(Logger.Level.WARN, null, throwable);
    }

    @Override
    public void debug(Throwable throwable) {
        print(Logger.Level.DEBUG, null, throwable);
    }

    @Override
    public void info(Throwable throwable) {
        print(Logger.Level.INFO, null, throwable);
    }

    @Override
    public void error(Throwable throwable) {
        print(Logger.Level.ERROR, null, throwable);

    }

    @Override
    public void verbose(String messageFormat, Object... args) {
        print(Logger.Level.VERBOSE, null, messageFormat, args);

    }

    @Override
    public void debug(String messageFormat, Object... args) {
        print(Logger.Level.DEBUG, null, messageFormat, args);

    }

    @Override
    public void info(String messageFormat, Object... args) {
        print(Logger.Level.INFO, null, messageFormat, args);

    }

    @Override
    public void warn(String messageFormat, Object... args) {
        print(Logger.Level.WARN, null, messageFormat, args);

    }

    @Override
    public void error(String messageFormat, Object... args) {
        print(Logger.Level.ERROR, null, messageFormat, args);

    }
}
