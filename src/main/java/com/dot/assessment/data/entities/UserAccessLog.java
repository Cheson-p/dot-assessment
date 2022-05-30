package com.dot.assessment.data.entities;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER_ACCESS_LOG")
@Data
@ToString
public class UserAccessLog implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", insertable=true, updatable = true, unique=true, nullable = false)
    private String id;
    private LocalDateTime date;
    private String ip;
    private String request;
    private String status;
    private String userAgent;
}
