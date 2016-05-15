package niad.kr.example50.data;

import java.io.Serializable;

/**
 * Created by niad on 3/23/16.
 */
public class Friend implements Serializable {

    private static final long serialVersionUID = -7382525640692690481L;

    private Long id;
    private String name;
    private String phoneNo;
    private String profileImgUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("id:").append(this.id).append(", name:").append(this.name).append("}");
        return sb.toString();
    }
}
