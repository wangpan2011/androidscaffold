package com.pppark.framework.logging;

/**
 * 此处写类描述
 * @Package com.kankan.logging
 * @ClassName: DefaultlHandler
 * @author wangfaxi
 * @mail wangfaxi@xunlei.com
 * @date 2013-10-23 下午1:47:12
 */
public class NullHandler implements Handler {

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
    public NullHandler() {

    }

    @Override
    public boolean isEnabled(Logger.Level level) {
        return false;
    }

    @Override
    public void print(String loggerName, Logger.Level level,
            Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException {
    }

}
