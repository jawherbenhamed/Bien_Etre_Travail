package com.pidev.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String channel;
    private String sender;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Temporal(TemporalType.TIMESTAMP)
    private Date readDate;
}