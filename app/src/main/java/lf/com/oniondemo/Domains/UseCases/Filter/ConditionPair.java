package lf.com.oniondemo.Domains.UseCases.Filter;

/**
 * Created by TranVo on 3/15/2017.
 */
public class ConditionPair {
    private String key;
    private String value;
    private String operator;
    private Class aClass;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
