package benefito.com.chatui.GetterSetter;

import java.io.Serializable;

/**
 * Created by Benefito on 28-04-2018.
 */

public class ContactVO implements Serializable {
    private int id;
    private String ContactImage;
    private String ContactName;
    private String ContactLastMessage;
    private String LastMessageTime;
    private Boolean is_online;
    private String count;


    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }

    public String getContactImage() {
        return ContactImage;
    }


    public void setContactImage(String contactImage) {
        this.ContactImage = contactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public void setContactLastMessage(String s) {
        ContactLastMessage = s;
    }
    public String getContactLastMessage() {
        return ContactLastMessage;
    }

    public void setLastMessageTime(String time) {
        LastMessageTime = time;
    }
    public String getLastMessageTime() {
        return LastMessageTime;
    }

    public void setIs_online(Boolean is_online)
    {
        this.is_online = is_online;
    }
    public Boolean getIs_online()
    {
        return is_online;
    }

    public void setUnread_message_count(String count)
    {
        this.count = count;
    }
    public String getUnread_message_count()
    {
        return count;
    }
}
