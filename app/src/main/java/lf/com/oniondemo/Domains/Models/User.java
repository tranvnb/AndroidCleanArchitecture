package lf.com.oniondemo.Domains.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by TranVoNB on 3/9/2017.
 */

public class User extends BaseModel {

    private int Id;
    private String name;
    private String email;
    private Date dob;
    private UserType userType;

    private List<Review> reviews;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public lf.com.oniondemo.Domains.Models.UserType getUserType() {
        return userType;
    }

    public void setUserType(lf.com.oniondemo.Domains.Models.UserType userType) {
        this.userType = userType;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
