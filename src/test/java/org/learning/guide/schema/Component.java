package org.learning.guide.schema;

import lombok.Getter;
import org.springframework.boot.actuate.health.Status;

import java.util.Map;

@Getter
public class Component {
    private Status status;
    private Map<String, Object> details;
}
