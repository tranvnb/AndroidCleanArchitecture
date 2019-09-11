package lf.com.oniondemo.DataServices.LocalData.RealmDto;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Product;
import lf.com.oniondemo.Domains.Models.ProductStatus;
import lf.com.oniondemo.Domains.Models.Review;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by TranVo on 3/16/2017.
 */
@RealmClass
public class ProductRealm implements RealmModel {

    @PrimaryKey
    private String Id;
    private String brandId;
    private String brandName;
    private String name;
    private String description;
    private int price;
    private String colour;
    private String availableStatus;
    private double averageRating;


    private int productIconIndex;

    private RealmList<ReviewRealm> reviews;

    public String getId() {
        return Id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public ProductStatus getAvailableStatus() {
        return ProductStatus.valueOf(availableStatus);
    }

    public void setAvailableStatus(ProductStatus availableStatus) {
        this.availableStatus = availableStatus.toString();
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public RealmList<ReviewRealm> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<ReviewRealm> reviews) {
        this.reviews = reviews;
    }

    public int getProductIconIndex() {
        return productIconIndex;
    }

    public void setProductIconIndex(int productIconIndex) {
        this.productIconIndex = productIconIndex;
    }
}
