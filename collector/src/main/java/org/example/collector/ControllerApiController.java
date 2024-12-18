package org.example.collector;

import java.util.List;
import org.example.common.context.Span;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerApiController {
    @PostMapping("/span")
    public void insertSpan(@RequestBody List<Span> span) {
        System.out.println("collect: " + span);
    }

    @PostMapping("/metric")
    public void insertMetric(){

    }
}
