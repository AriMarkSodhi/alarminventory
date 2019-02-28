package com.ari.msexp1;


import com.ari.msexp1.mongoDAL.DAL.AlarmDAL;
import com.ari.msexp1.mongoDAL.DAL.AlarmDefinitionDAL;
import com.ari.msexp1.mongoDAL.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
public class AlarmPopulationApp implements CommandLineRunner {
    private static final Logger LOG = Logger.getLogger(AlarmPopulationApp.class);
    private final AlarmDefinitionDAL alarmDefinitionDAL;
    private final AlarmDAL alarmDAL;

    @Autowired
    public AlarmPopulationApp(AlarmDefinitionDAL alarmDefinitionDAL, AlarmDAL alarmDAL) {
        this.alarmDefinitionDAL = alarmDefinitionDAL;
        this.alarmDAL = alarmDAL;
    }

    @Autowired
    org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;


    public static void main(String[] args) {
        SpringApplication.run(AlarmPopulationApp.class, args);
    }

    @Override
    public void run(String... args) {

        // cleanup any existing data
        if (mongoTemplate.getDb() != null)
            mongoTemplate.getDb().drop();

        for (int i =0; i<100; i++) {
            alarmDefinitionDAL.save(new AlarmDefinition(
                    "AlarmType_"+i, "", "Alarm_"+i, Severity.values()[new Random().nextInt(Severity.values().length)],
                    "Alarm text", Boolean.TRUE, Arrays.asList("1/1/eth1","1/1/eth2")));
        }

        LOG.info("Getting all alarmdefinition data from MongoDB: \n{}"+
                alarmDefinitionDAL.findAll());
        LOG.info("Getting paginated alarmdefinition data from MongoDB: \n{}"+
                alarmDefinitionDAL.findAll(0, 2));

        for (int i =0; i<100; i++) {
            alarmDAL.save(new Alarm("alarm_id_"+i, "1/1/eth1", "AlarmType_"+i, "", "Alarm_"+i, "",
                    "", Boolean.FALSE, new Date(), Severity.values()[new Random().nextInt(Severity.values().length)],
                    "Alarm text", Arrays.asList(new AlarmStatusChange(Severity.values()[new Random().nextInt(Severity.values().length)], "AlarmText-New"))
                    , Arrays.asList(new OperatorStateChange("Op1", OperatorState.values()[new Random().nextInt(OperatorState.values().length)], "Update"))));
        }
        LOG.info("Getting all alarm data from MongoDB: \n{}"+
                alarmDAL.findAll());
        LOG.info("Getting paginated alarm data from MongoDB: \n{}"+
                alarmDAL.findAll(0, 2));
    }
}


