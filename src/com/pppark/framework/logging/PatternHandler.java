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

public class PatternHandler implements Handler {

    private final Logger.Level level;
    private final String tagPattern;
    private final String messagePattern;
    private final Pattern compiledTagPattern;
    private final Pattern compiledMessagePattern;

    /**
     * Creates new {@link PatternHandler}.
     * 
     * @param level
     *            the level.
     * @param tagPattern
     *            the tag pattern.
     * @param messagePattern
     *            the message pattern.
     */
    public PatternHandler(Logger.Level level, String tagPattern, String messagePattern) {
        this.level = level;
        this.tagPattern = tagPattern;
        this.compiledTagPattern = Pattern.compile(tagPattern);
        this.messagePattern = messagePattern;
        this.compiledMessagePattern = Pattern.compile(messagePattern);
    }

    /**
     * Returns the level.
     * 
     * @return the level.
     */
    public Logger.Level getLevel() {
        return level;
    }

    /**
     * Returns the tag messagePattern.
     * 
     * @return the tag messagePattern.
     */
    public String getTagPattern() {
        return tagPattern;
    }

    /**
     * Returns the message messagePattern.
     * 
     * @return the message messagePattern.
     */
    public String getMessagePattern() {
        return messagePattern;
    }

    @Override
    public boolean isEnabled(Logger.Level level) {
        return this.level != null && level != null && this.level.includes(level);
    }

    @Override
    public void print(String loggerName, Logger.Level level,
            Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException {
        if (isEnabled(level)) {
            String message;

            if (messageFormat == null) {
                if (args != null && args.length > 0) {
                    throw new IllegalArgumentException("message format is not set but arguments are presented");
                }

                if (throwable == null) {
                    message = "";
                } else {
                    message = Log.getStackTraceString(throwable);
                }
            } else {
                if (throwable == null) {
                    if (args.length != 0)
                        message = String.format(messageFormat, args);
                    else
                        message = messageFormat;
                } else {
                    if (args.length != 0)
                        message = String.format(messageFormat, args) + '\n' + Log.getStackTraceString(throwable);
                    else
                        message = messageFormat + '\n' + Log.getStackTraceString(throwable);
                }
            }

            StackTraceElement caller = null;
            if ((compiledTagPattern != null && compiledTagPattern.isCallerNeeded())
                    || (compiledMessagePattern != null && compiledMessagePattern.isCallerNeeded())) {
                caller = Utils.getCaller();
            }

            String tag = compiledTagPattern == null ? loggerName : compiledTagPattern.apply(caller, loggerName, level);
            String messageHead = compiledMessagePattern == null ? "" : compiledMessagePattern.apply(caller, loggerName,
                    level);

            if (messageHead.length() > 0 && !Character.isWhitespace(messageHead.charAt(0))) {
                messageHead = messageHead + " ";
            }
            Log.println(level.intValue(), tag, messageHead + message);
        }
    }
}
