package com.anshishagua.object;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * TaskLogger.java
 *
 * @author lixiao
 * @date 2021-04-01
 */

public class TaskLogger {
    private Logger logger;
    private long workflowInstanceId;
    private long taskInstanceId;

    public TaskLogger(String logName) {
        this.logger = Logger.getLogger(logName);
    }

    public static TaskLogger getLogger(String logName) {
        return new TaskLogger(logName);
    }

    public long getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void init() throws IOException {
        String logPath = String.format("/tmp/workflow-%d-task-%d.log", workflowInstanceId, taskInstanceId);

        Handler handler = new FileHandler(logPath, true);
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                StringBuilder builder = new StringBuilder();
                Instant instant = Instant.ofEpochMilli(record.getMillis());
                ZoneId zone = ZoneId.systemDefault();
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);

                builder.append(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append(" - ");
                builder.append("[").append(record.getSourceClassName()).append(".");
                builder.append(record.getSourceMethodName()).append("] - ");
                builder.append("[").append(record.getLevel()).append("] - ");
                builder.append(formatMessage(record));
                builder.append("\n");
                return builder.toString();
            }
        });
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public void setWorkflowInstanceId(long workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public long getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(long taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warning(message);
    }

    public void error(String message) {
        logger.severe(message);
    }
}
