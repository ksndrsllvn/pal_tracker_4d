package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    private Map<String, String> env;

    public EnvController(
            @Value("${PORT:NOT SET}") String port,
            @Value("${MEMORY_LIMIT:NOT SET}") String memLmt,
            @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfIndx,
            @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfAddr) {

        env = new HashMap<>();
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memLmt);
        env.put("CF_INSTANCE_INDEX", cfIndx);
        env.put("CF_INSTANCE_ADDR", cfAddr);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return env;
    }
}
