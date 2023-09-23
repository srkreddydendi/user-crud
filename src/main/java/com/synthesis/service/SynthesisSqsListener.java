package com.synthesis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

@Slf4j
@Service
public class SynthesisSqsListener {

    @SqsListener("synthesis")
    public void loadMessageFromSQS(String message)  {
        log.info("message from SQS Queue {}",message);
    }
}


