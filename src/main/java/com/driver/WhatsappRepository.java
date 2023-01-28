package com.driver;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<String,String> Newuser;
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;

    private HashMap<Integer,String > msgContent;

    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public String createUser(String name,String number){
        if(userMobile.contains(number)){
             return  "User already exists";
        }
        userMobile.add(number);
        Newuser.put(name,number);
        return "SUCCESS";
    }


    public WhatsappRepository(){
        this.Newuser = new HashMap<>();
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = -1;
        this.msgContent=new HashMap<>();

    }

  public Group  createGroups(List<User> users){
        int count=-1;

        User adminname= users.get(0);


      if(!groupUserMap.isEmpty()){
            if(users.size()==2){
                User u=users.get(users.size()-1);
                String gn=u.getName();
                Group g=new Group(gn,users.size());
                groupUserMap.put(g,users);
                adminMap.put(g,adminname);
            }
        }
        count++;
        Group g=new Group("Group"+count,users.size());
        groupUserMap.put(g,users);
        adminMap.put(g,adminname);
        return g;
  }

  public int createMsg(String content){
      messageId++;
      msgContent.put(messageId,content); // putting msg id with content;
      Message msg=new Message(messageId,content,new Date());
      return messageId;
  }

  public int Sendmsg(Message message, User sender, Group group){
        if(!groupUserMap.containsKey(group.getName())){
            throw new IllegalArgumentException("Group does not exist");
        }
        String curname=sender.getName();
        List groupname=groupUserMap.get(group.getName());

        if(!groupname.contains(curname)){
            throw new IllegalArgumentException("You are not allowed to send message");
        }
        return messageId;
  }

    //Throw "Approver does not have rights"     if the approver is not the current admin of the group
    //Throw "User is not a participant" if the user is not a part of the group
    //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

    public  String  changeAdmin(User approver, User user, Group group){
        if(!groupUserMap.containsKey(group.getName())){
            throw new IllegalArgumentException("Group does not exist");
        }
        User curadmin =adminMap.get(group);
         if (curadmin.getName()!= approver.getName()){
             throw new IllegalArgumentException("User is not a participant");
         }else{
             adminMap.put(group,user);
             return "SUCCESS";
         }

  }
}
