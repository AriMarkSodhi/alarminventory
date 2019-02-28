package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.AlarmDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlarmDefinitionDALImpl implements AlarmDefinitionDAL {
    private final MongoTemplate mongoTemplate;
    @Autowired
    public AlarmDefinitionDALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public AlarmDefinition save(AlarmDefinition AlarmDefinition) {
        mongoTemplate.save(AlarmDefinition);
        return AlarmDefinition;
    }
    @Override
    public List<AlarmDefinition> findAll() {
        return mongoTemplate.findAll(AlarmDefinition.class);
    }

    @Override
    public List<AlarmDefinition> findAll(int pageNumber, int pageSize) {
        Query query = new Query();
        query.skip(pageNumber * pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, AlarmDefinition.class);
    }
    @Override
    public AlarmDefinition findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, AlarmDefinition.class);
    }

    @Override
    public AlarmDefinition findOneById(String alarmDefinitionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("AlarmId").is(alarmDefinitionId));
        return mongoTemplate.findOne(query, AlarmDefinition.class);
    }

    @Override
    public List<AlarmDefinition> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, AlarmDefinition.class);
    }
    @Override
    public List<AlarmDefinition> findByResource(String resource) {
        Query query = new Query();
        query.addCriteria(Criteria.where("resources").in(resource));
        return mongoTemplate.find(query, AlarmDefinition.class);
    }
    @Override
    public AlarmDefinition updateOneAlarm(AlarmDefinition Alarm) {
        mongoTemplate.save(Alarm);
        return Alarm;
    }
    @Override
    public void deleteAlarm(AlarmDefinition Alarm) {
        mongoTemplate.remove(Alarm);
    }
}
