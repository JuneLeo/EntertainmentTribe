package com.entertainment.project.common.log;

/**
 * Created by lc on 16/4/1.
 * 性能日志
 */
public class PerformanceLogEvent extends LogEvent {

    private String action;

    private long ms;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) {
        this.ms = ms;
    }

    @Override
    public String toString() {
        return getTime() + "    " + getUser_id() + "    " + getOs()
                + "    " + getVersion() + "    "
                + getAction() + "    " + getMs() + "\n";
    }
}
