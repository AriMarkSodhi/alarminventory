package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.AlarmDefinition;

import java.util.List;

public interface AlarmDefinitionDAL {
    AlarmDefinition save(AlarmDefinition Alarm);
    List<AlarmDefinition> findAll();
    List<AlarmDefinition> findAll(
            int pageNumber, int pageSize);
    AlarmDefinition findOneByName(String name);
    AlarmDefinition findOneById(String alarmDefinitionId);
    List<AlarmDefinition> findByName(String name);
    List<AlarmDefinition> findByResource(String resource);
    AlarmDefinition updateOneAlarm(AlarmDefinition Alarm);
    void deleteAlarm(AlarmDefinition Alarm);
}