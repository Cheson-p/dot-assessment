package com.dot.assessment.data.entities;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BLOCKED_IP_TABLE")
@Data
@ToString
public class BlockedIpTable implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", insertable=true, updatable = true, unique=true, nullable = false)
    private String id;
    private String ip;
    private String requestNumber;
    private String comment;
}
