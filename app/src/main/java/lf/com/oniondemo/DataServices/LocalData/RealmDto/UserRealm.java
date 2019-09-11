package lf.com.oniondemo.DataServices.LocalData.RealmDto;

import java.util.Date;

import lf.com.oniondemo.Domains.Models.User;
import lf.com.oniondemo.Domains.Models.UserType;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by TranVo on 3/16/2017.
 */
@RealmClass
public class UserRealm implements RealmModel {
    @PrimaryKey
    private String Id;
    private String name;
    private String email;
    private Date dob;
    private String userType;

    private RealmList<ReviewRealm> reviews;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public UserType getUserType() {
        return UserType.valueOf(userType);
    }

    public void setUserType(UserType userType) {
        this.userType = userType.toString();
    }

    public RealmList<ReviewRealm> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<ReviewRealm> reviews) {
        this.reviews = reviews;
    }
}
