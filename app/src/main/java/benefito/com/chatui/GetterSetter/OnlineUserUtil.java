package benefito.com.chatui.GetterSetter;

public class OnlineUserUtil {

    private Boolean is_online;
    private String imageURL;

    public void setIs_online(Boolean is_online)
    {
        this.is_online = is_online;
    }

    public Boolean getIs_online()
    {
        return is_online;
    }
    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public String getImageURL()
    {
        return imageURL;
    }
}
