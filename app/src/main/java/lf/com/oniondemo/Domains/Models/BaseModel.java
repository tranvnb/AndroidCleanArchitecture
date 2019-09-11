package lf.com.oniondemo.Domains.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TranVoNB on 3/9/2017.
 */
public class BaseModel implements Serializable {

    protected Date createdDate;
    protected Date modifiedDate;

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
