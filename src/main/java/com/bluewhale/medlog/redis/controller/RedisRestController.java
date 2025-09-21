package com.bluewhale.medlog.redis.controller;

import com.bluewhale.medlog.redis.dto.RedisKeyValueDTO;
import com.bluewhale.medlog.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redis")
public class RedisRestController {

    private final RedisService redisService;

    @PostMapping("")
    public ResponseEntity<Void> saveKeyValue(@RequestBody RedisKeyValueDTO redisKeyValueDTO) {
        int status;
        if (redisKeyValueDTO.getDuration() == null) {
            status = redisService.setSingleData(redisKeyValueDTO.getKey(), redisKeyValueDTO.getValue());
        } else {
            status = redisService.setSingleData(redisKeyValueDTO.getKey(), redisKeyValueDTO.getValue(), redisKeyValueDTO.getDuration());
        }

        if (status == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<RedisKeyValueDTO> getKeyValue(@PathVariable String key) {
        String value = redisService.getSingleData(key);
        if (value != null && !value.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new RedisKeyValueDTO(key, value));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
