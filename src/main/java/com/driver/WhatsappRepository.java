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

    public WhatsappRepository(){
        this.Newuser = new HashMap<>();
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
        this.msgContent=new HashMap<>();

    }

    public String createUser(String name,String number) throws Exception{
        if(userMobile.contains(number)){
         throw new Exception("User already exists");
        }
        userMobile.add(number);
        Newuser.put(name,number);
        return "SUCCESS";
    }

  public Group  createGroups(List<User> users){
        User adminname= users.get(0);

            if(users.size()==2){
                User u=users.get(users.size()-1);
                String gn=u.getName();
                Group g=new Group(gn,users.size());
                groupUserMap.put(g,users);
                adminMap.put(g,adminname);
                return g;
            }
        customGroupCount++;
        Group g=new Group("Group "+customGroupCount,users.size());
        groupUserMap.put(g,users);
        adminMap.put(g,adminname);
        return g;
  }

  public int createMsg(String content){
      messageId++;
      msgContent.put(messageId,content); // putting msg id with content;
      return messageId;
  }

  public int Sendmsg(Message message, User sender, Group group) throws Exception{
        if(!groupUserMap.containsKey(group.getName())){
            throw new Exception("Group does not exist");
        }
        String curname=sender.getName();
        List groupname=groupUserMap.get(group.getName());
        if(!groupname.contains(curname)){
            throw new Exception("You are not allowed to send message");
        }
        return messageId;
  }
    public  String  changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }

     List<User> cu=groupUserMap.get(group);
        if(!cu.contains(user)){
            throw  new Exception("User is not a participant");
        }
        String curadmin =adminMap.get(group).getName();
         if (curadmin != approver.getName()){
             throw new Exception("Approver does not have rights");
         }
             adminMap.put(group,user);
             return "SUCCESS";

  }
}
