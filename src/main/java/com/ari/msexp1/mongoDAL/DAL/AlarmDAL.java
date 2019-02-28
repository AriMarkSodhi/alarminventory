package com.ari.msexp1.mongoDAL.DAL;

import com.ari.msexp1.mongoDAL.model.Alarm;
import com.ari.msexp1.mongoDAL.model.AlarmStatusChange;
import com.ari.msexp1.mongoDAL.model.OperatorStateChange;

import java.util.Date;
import java.util.List;

public interface AlarmDAL {
    Alarm save(Alarm Alarm);
    List<Alarm> save(List<Alarm> Alarms);
    List<Alarm> findAll();

    List<Alarm> findAll(int pageNumber, int pageSize);
    List<Alarm> getAllAlarmByDefnPaginated(String alarmTypeId, String alarmTypeQualifier,
                                           int pageNumber, int pageSize);
    List<Alarm> getDefaultSortedAlarmsPaginated(int pageNumber, int pageSize);
    List<Alarm> getDefaultSortedAlarmsPaginatedByResource(int pageNumber, int pageSize, String resource);

    Alarm findOneById(String alarmId);
    Alarm findOneByName(String name);
    List<Alarm> findByName(String name);
    List<Alarm> findByTime(Date date);
    List<Alarm> findByTimeRange(int lowerBound, int upperBound);
    List<Alarm> findByResource(String resource);
    Alarm updateOneAlarm(Alarm Alarm);
    Alarm updateAlarmStatus(String alarmId, AlarmStatusChange statusChange);
    Alarm updateOperatorState(String alarmId, OperatorStateChange statusChange);
    Alarm clearAlarm(String alarmId);

    void deleteAlarm(Alarm Alarm);
}