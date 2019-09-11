package lf.com.oniondemo.Domains.Models;

import java.util.List;

/**
 * Created by TranVoNB on 3/8/2017.
 */

public class Brand extends BaseModel{

    private String Id;
    private String name;
    private String description;

    private int brandIconIndex;

    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getBrandIconIndex() {
        return brandIconIndex;
    }

    public void setBrandIconIndex(int brandIconIndex) {
        this.brandIconIndex = brandIconIndex;
    }
}
