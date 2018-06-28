package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Orm.Orma;

import jp.co.pise.projecttemplate_android.Data.Definitions.Operator;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;

public class OrmaRepositoryConditions {

    static public String CreateConditionString(RepositoryConditions condition)
    {
        String result = "";
        switch(condition.operator)
        {
            case Operator.EQUAL:
                result = eq(condition.key, condition.value);
                break;
            case Operator.OVER:
                result = over(condition.key, condition.value);
                break;
            case Operator.BELOW:
                result = below(condition.key, condition.value);
                break;
            case Operator.LIKE:
                result = build(condition.key,"'" + condition.value + "'", "LIKE");
        }
        return result;
    }

    static private String build(String key, String value, String operator)
    {
        return key + " " + operator + " " + value;
    }

    static public String eq(String key, String value)
    {
        return build(key, value, Operator.EQUAL);
    }

    static public String eq(String key, int value)
    {
        return eq(key, String.valueOf(value));
    }

    static public String over(String key, String value)
    {
        return build(key, value, Operator.OVER);
    }

    static public String below(String key, String value)
    {
        return build(key, value, Operator.BELOW);
    }

    static public String between(String key, int from, int to)
    {
        return key + " BETWEEN " + from + " AND " + to;
    }
}
