package lf.com.oniondemo.Utils;

import android.content.res.TypedArray;

import java.util.List;
import java.util.Random;

import lf.com.oniondemo.Domains.UseCases.Filter.ConditionPair;
import lf.com.oniondemo.R;
import lf.com.oniondemo.OnionDemoApp;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;

/**
 * Created by TranVo on 3/16/2017.
 */

public class RealmHelper {

    public static String generateUUID(){
        return java.util.UUID.randomUUID().toString();
    }

    public static RealmQuery generateQuery(RealmQuery query, List<ConditionPair> list){
        for (ConditionPair condition :
                list) {
            switch (condition.getOperator()){
                case "==":
                    query = query.equalTo(condition.getKey(), (condition.getValue()));
                    break;
//                case "<=":
//                    query = query.lessThan(condition.getKey(), ((condition.getaClass())condition.getValue());
//                    break;
            }
        }
        return query;
    }
    static TypedArray imgs;
    public static int getIconResource(int i){
        if (imgs == null){
            imgs = OnionDemoApp.getContext().getResources().obtainTypedArray(R.array.random_imgs);
        }
        return imgs.getResourceId(i, 0);
    }
}
