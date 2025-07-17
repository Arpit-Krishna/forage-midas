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
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private BalanceQuerier balanceQuerier;

    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        Thread.sleep(2000);


        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what waldorf's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");

        Thread.sleep(3000);
        Long waldorfUserId = 1234L; 

        Balance waldorfBalance = balanceQuerier.query(waldorfUserId);
        logger.info("Waldorf's balance: {}", waldorfBalance.getAmount());

        Assertions.assertEquals(0.0, waldorfBalance.getAmount(), 0.01, "Waldorf's balance mismatch after processing transactions.");
        // while (true) {
        //     Thread.sleep(20000);
        //     logger.info("...");
        // }
    }
}
