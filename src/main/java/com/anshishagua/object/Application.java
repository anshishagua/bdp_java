package com.anshishagua.object;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Application.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

public class Application {
    private long id;
    private String name;
    private int version;
    private String path;
    private LocalDateTime createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
