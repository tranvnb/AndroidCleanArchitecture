package lf.com.oniondemo.Domains.Models;

import java.util.List;

/**
 * Created by TranVoNB on 3/8/2017.
 */

public class Product extends BaseModel {
    private String Id;
    private String brandId;
    private String brandName;
    private String name;
    private String description;
    private int price;
    private String colour;
    private ProductStatus availableStatus;
    private double averageRating;

    private List<Review> reviews;

    private int productIconIndex;

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
        return availableStatus;
    }

    public void setAvailableStatus(ProductStatus availableStatus) {
        this.availableStatus = availableStatus;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getProductIconIndex() {
        return productIconIndex;
    }

    public void setProductIconIndex(int productIconIndex) {
        this.productIconIndex = productIconIndex;
    }
}
