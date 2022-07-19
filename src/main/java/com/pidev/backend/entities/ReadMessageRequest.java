package com.pidev.backend.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ReadMessageRequest {
    private String channel;
    private String username;
}
