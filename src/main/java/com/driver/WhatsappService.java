package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatsappService {

    @Autowired
    WhatsappRepository Wp;

    public String createUser(String name,String mobile){
            return Wp.createUser(name,mobile);
    }

    public Group createGroups( List<User> users){
        return Wp. createGroups(users);
    }

    public int createMessage(String content){
        return Wp.createMsg(content);
    }

    public int sendMessage(Message message, User sender, Group group){
        return Wp.Sendmsg(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group){
       return Wp.changeAdmin(approver,user,group);
    }
}
