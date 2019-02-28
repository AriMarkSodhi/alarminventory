package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Alarm;
import com.ari.msexp1.mongoDAL.model.AlarmStatusChange;
import com.ari.msexp1.mongoDAL.model.OperatorStateChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AlarmDALImpl implements AlarmDAL {
    private final MongoTemplate mongoTemplate;
    @Autowired
    public AlarmDALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public Alarm save(Alarm Alarm) {
        mongoTemplate.save(Alarm);
        return Alarm;
    }
    @Override
    public List<Alarm> save(List<Alarm> Alarms) {
        mongoTemplate.insertAll(Alarms);
        return Alarms;
    }
    @Override
    public List<Alarm> findAll() {
        return mongoTemplate.findAll(Alarm.class);
    }
    @Override
    public List<Alarm> findAll(int pageNumber, int pageSize) {
        Query query = new Query();
        query.skip(pageNumber * pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, Alarm.class);
    }
    @Override
    public List<Alarm> getAllAlarmByDefnPaginated(String alarmTypeId, String alarmTypeQualifier,
                                                  int pageNumber, int pageSize)
    {
        MatchOperation matchStage = Aggregation.match(new Criteria("alarmTypeId").is(alarmTypeId).andOperator(new Criteria("alarmTypeQualifier").is(alarmTypeQualifier)));
        ProjectionOperation projectStage = Aggregation.project("foo", "bar.baz");

        Aggregation aggregation
                = Aggregation.newAggregation(matchStage, projectStage);

        AggregationResults<Alarm> output
                = mongoTemplate.aggregate(aggregation, "foobar", Alarm.class);

        Query query = new Query();
        query.skip(pageNumber * pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, Alarm.class);
    }

    @Override
    public List<Alarm> getDefaultSortedAlarmsPaginated(int pageNumber, int pageSize) {
        Query query = new Query();
        query.skip(pageNumber * pageSize);
        query.limit(pageSize);
        query.addCriteria(Criteria.where("isCleared").ne(Boolean.TRUE));
        query.with(new Sort(Direction.DESC, "perceivedSeverity")).with(new Sort(Direction.DESC, "timeCreated")).with(new Sort(Direction.ASC,"resource"));

        return mongoTemplate.find(query, Alarm.class);
    }

    @Override
    public List<Alarm> getDefaultSortedAlarmsPaginatedByResource(int pageNumber, int pageSize, String resource) {
        Query query = new Query();
        query.skip(pageNumber * pageSize);
        query.limit(pageSize);
        query.addCriteria(Criteria.where("resource").is(resource));
        query.addCriteria(Criteria.where("isCleared").ne(Boolean.TRUE));
        query.with(new Sort(Direction.DESC, "perceivedSeverity")).with(new Sort(Direction.DESC, "timeCreated")).with(new Sort(Direction.ASC,"resource"));

        return mongoTemplate.find(query, Alarm.class);
    }

    @Override
    public Alarm findOneById(String alarmId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(alarmId));
        return mongoTemplate.findOne(query, Alarm.class);
    }
    @Override
    public Alarm findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Alarm.class);
    }
    @Override
    public List<Alarm> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Alarm.class);
    }
    @Override
    public List<Alarm> findByTime(Date date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("timeCreated").gt(date));
        return mongoTemplate.find(query, Alarm.class);
    }
    @Override
    public List<Alarm> findByTimeRange(int lowerBound, int upperBound) {
        Query query = new Query();
        query.addCriteria(Criteria.where("timeCreated").gt(lowerBound)
                .andOperator(Criteria.where("timeCreated").lt(upperBound)));
        return mongoTemplate.find(query, Alarm.class);
    }
    @Override
    public List<Alarm> findByResource(String resource) {
        Query query = new Query();
        query.addCriteria(Criteria.where("resource").in(resource));
        return mongoTemplate.find(query, Alarm.class);
    }
    @Override
    public Alarm updateOneAlarm(Alarm Alarm) {
        mongoTemplate.save(Alarm);
        return Alarm;
    }
    @Override
    public Alarm clearAlarm(String alarmId) {

        Alarm alarm = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(alarmId)), Alarm.class);
        if (alarm != null) {
            alarm.setCleared(Boolean.TRUE);
            alarm.setLastChange(new Date());
            mongoTemplate.save(alarm);
        }
        return alarm;
    }
    @Override
    public Alarm updateAlarmStatus(String alarmId, AlarmStatusChange statusChange) {

        Alarm alarm = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(alarmId)), Alarm.class);
        if (alarm != null) {
            alarm.updateAlarmStatusChanges(statusChange);
            alarm.setLastChange(new Date());
            mongoTemplate.save(alarm);
        }
        return alarm;
    }

    @Override
    public Alarm updateOperatorState(String alarmId, OperatorStateChange statusChange) {

        Alarm alarm = mongoTemplate.findOne(
                Query.query(Criteria.where("id").is(alarmId)), Alarm.class);
        if (alarm != null) {
            alarm.updateOperatorStateChanges(statusChange);
            alarm.setLastChange(new Date());
            mongoTemplate.save(alarm);
        }
        return alarm;
    }


    @Override
    public void deleteAlarm(Alarm Alarm) {
        mongoTemplate.remove(Alarm);
    }
}
