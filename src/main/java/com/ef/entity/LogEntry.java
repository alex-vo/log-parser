package com.ef.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "log_entry")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date", columnDefinition = "TIMESTAMP")
    private ZonedDateTime entryDate;

    @Column(name = "ip")
    private String ip;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private Integer status;

    @Column(name = "message")
    private String message;

    public LogEntry(ZonedDateTime entryDate, String ip, String method, Integer status, String message) {
        this.entryDate = entryDate;
        this.ip = ip;
        this.method = method;
        this.status = status;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
