package lf.com.oniondemo.DataServices.LocalData.RealmDto;

import lf.com.oniondemo.Domains.Models.Brand;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by TranVo on 3/16/2017.
 */
@RealmClass
public class BrandRealm implements RealmModel {
    @PrimaryKey
    private String Id;
    private String name;
    private String description;
    private int brandIconIndex;

    private RealmList<ProductRealm> products;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<ProductRealm> getProducts() {
        return products;
    }

    public void setProducts(RealmList<ProductRealm> products) {
        this.products = products;
    }

    public int getBrandIconIndex() {
        return brandIconIndex;
    }

    public void setBrandIconIndex(int brandIconIndex) {
        this.brandIconIndex = brandIconIndex;
    }
}
