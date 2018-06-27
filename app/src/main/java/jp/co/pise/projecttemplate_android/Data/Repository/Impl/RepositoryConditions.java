package jp.co.pise.projecttemplate_android.Data.Repository.Impl;

public class RepositoryConditions {

    public String key;
    public String value;
    public String operator;
    public String queryType;

    public RepositoryConditions(String key, String value, String operator, String queryType)
    {
        this.key = key;
        this.value = value;
        this.operator = operator;
        this.queryType = queryType;
    }
}
