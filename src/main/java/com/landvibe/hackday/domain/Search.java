package com.landvibe.hackday.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class Search implements Serializable {
    private static final long serialVersionUID = -8513333341906443310L;

    private long id;
    private String word;
    private boolean crawled;
    private String fileName;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", crawled=" + crawled +
                ", fileName='" + fileName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
