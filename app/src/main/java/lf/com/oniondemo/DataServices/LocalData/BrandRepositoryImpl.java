package lf.com.oniondemo.DataServices.LocalData;

import java.util.ArrayList;
import java.util.List;

import lf.com.oniondemo.DataServices.LocalData.RealmDto.BrandRealm;
import lf.com.oniondemo.Domains.Models.Brand;
import lf.com.oniondemo.Domains.Repositories.MyDataSource;
import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.Utils.RealmHelper;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class BrandRepositoryImpl implements MyDataSource<Brand> {

    @Override
    public Observable<List<Brand>> getAll() {

        Realm realm = Realm.getDefaultInstance();
        return Observable.create(e -> {
            List<Brand> brands = new ArrayList<>();
            realm.executeTransactionAsync(realm1 -> {
                        RealmResults realmResults = realm1.where(BrandRealm.class).findAll();
                        List<BrandRealm> brandRealms = realm1.copyFromRealm(realmResults);
                        for (BrandRealm item :
                                brandRealms) {
                            Brand brand = mapToDomain(item);
                            brands.add(brand);
                        }
                        e.onNext(brands);
                    }
            );
        });
    }

    @Override
    public Observable<Brand> getByID(String ID) {
        return null;
    }

    @Override
    public Observable<Boolean> update(Brand newItem) {
        return null;
    }

    @Override
    public Observable<String> insert(Brand newItem) {

        Realm realm = Realm.getDefaultInstance();
        return Observable.create(e -> {
            final String[] key = {""};
            realm.executeTransactionAsync(realm1 -> {
                BrandRealm brandRealm = mapToRealm(newItem);
                key[0] = RealmHelper.generateUUID();
                brandRealm.setId(key[0]);
                realm1.copyToRealmOrUpdate(brandRealm);
                e.onNext(key[0]);
            });
        });

    }

    @Override
    public Observable<Boolean> delete(String ID) {
        return null;
    }

    @Override
    public Observable<List<Brand>> getAll(List<ConditionPair> filterOptions) {
        return null;
    }

    private Brand mapToDomain(BrandRealm brandRealm) {
        Brand brand = new Brand();
        brand.setId(brandRealm.getId() + "");
        brand.setName(brandRealm.getName());
        brand.setDescription(brandRealm.getDescription());
        brand.setBrandIconIndex(brandRealm.getBrandIconIndex());
        return brand;
    }

    private BrandRealm mapToRealm(Brand brand) {
        BrandRealm brandRealm = new BrandRealm();
        if (brand.getId() != null && "".compareTo(brand.getId()) != 0) {
            brandRealm.setId((brand.getId()));
        }
        brandRealm.setName(brand.getName());
        brandRealm.setDescription(brand.getDescription());

        brandRealm.setBrandIconIndex(brand.getBrandIconIndex());

        return brandRealm;
    }

}
