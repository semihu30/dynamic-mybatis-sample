package com.github.xiaoy.samples.service;


import com.github.xiaoy.samples.entity.User;
import com.github.xiaoy.samples.mapper.UserMasterMapper;
import com.github.xiaoy.samples.mapper.UserSlaveMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author songxiaoyue
 */
@Service
public class UserService {
  @Resource
  private UserMasterMapper masterMapper;
  @Resource
  private UserSlaveMapper slaveMapper;

  public int selectTestOne(){
    int i = masterMapper.selectMasterTestOne();
    int j = slaveMapper.selectTestOne();
    return i+j;
  }

  public void addUser(User user){
    addUserMasterAndSlave(user);
  }

  @Transactional
  public void addUserTx(User user){
    addUserMasterAndSlave(user);
  }

  @Transactional
  public void addUserTxExcepiton(User user) throws RuntimeException {
    addUserMasterAndSlave(user);
    throw new RuntimeException();
  }

  private void addUserMasterAndSlave(User user) {
    masterMapper.addMasterUser(getParamMap(user, "A"));
    slaveMapper.addUser(getParamMap(user, "B"));
  }

  private Map<String, Object> getParamMap(User user, String ds) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("name", ds + "_" + user.getName());
    int index = user.getAge() + (ds.equals("B") ? 1 : 0);
    paramMap.put("age", index);
    return paramMap;
  }

/*
    public void addUser(User user){
      masterMapper.addUser("A表"+user.getName(),user.getAge());
      slaveMapper.addUser("B表"+user.getName(),user.getAge()+1);
    }

    @Transactional
    public void addUserTx(User user){
      masterMapper.addUser("A表"+user.getName(),user.getAge());
      slaveMapper.addUser("B表"+user.getName(),user.getAge()+1);
    }

    @Transactional
    public void addUserTxExcepiton(User user) throws RuntimeException {
      masterMapper.addUser("A表"+user.getName(),user.getAge());
      slaveMapper.addUser("B表"+user.getName(),user.getAge()+1);
      throw new RuntimeException();
  }

 */






  public List<User> selectA(){
    List<User> master = masterMapper.selectMasterUsers();
    return master;
  }

  public List<User> selectB(){
    List<User> slave = slaveMapper.selectUsers();
    return slave;
  }



}
