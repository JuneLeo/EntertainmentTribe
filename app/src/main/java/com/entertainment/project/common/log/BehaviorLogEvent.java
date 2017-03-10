package com.entertainment.project.common.log;

/**
 * Created by lc on 16/4/1.
 * 行为日志
 */
public class BehaviorLogEvent extends LogEvent {

    private String behavior;

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    @Override
    public String toString() {
        return getTime() + "    " + getUser_id() + "    " + getOs()
                + "    " + getVersion() + "    " + getBehavior() + "\n";
    }
}
