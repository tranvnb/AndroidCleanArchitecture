package lf.com.oniondemo.UI;

import java.util.List;

import lf.com.oniondemo.Domains.Models.Brand;

/**
 * Created by tranvonb on 3/14/2017.
 */
public interface ProductView extends IView {

    void updateBrandList(List<Brand> brands);
}
