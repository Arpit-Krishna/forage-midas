package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Assertions;

import com.jpmc.midascore.foundation.Balance;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class TaskTwoTests {
    static final Logger logger = LoggerFactory.getLogger(TaskTwoTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private BalanceQuerier balanceQuerier;

    @Test
    void task_two_verifier() throws InterruptedException {
        String[] transactionLines = fileLoader.loadStrings("/test_data/poiuytrewq.uiop");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(2000);
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to watch for incoming transactions");
        logger.info("kill this test once you find the answer");
        // Query balance
        Balance waldorfBalance = balanceQuerier.query(Long.valueOf(3));
        Assertions.assertEquals(0, waldorfBalance.getAmount(), 0.01f);

        logger.info("✅ Task 2 test completed with correct post-condition validation.");

        }

}
