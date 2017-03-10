package com.entertainment.project.common.log;


/**
 * Created by lc on 16/4/1.
 */
public class LogEvent {
    private String time;

    private Long user_id;

    private String os;

    private String version;

    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getTime() + "    " + getUser_id() + "    " + getOs()
                + "    " + getVersion() + "    " + getContent()  + "\n";
    }
}
