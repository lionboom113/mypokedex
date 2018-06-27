package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Orm.Orma;

import com.github.gfx.android.orma.Selector;

import java.util.List;

import jp.co.pise.projecttemplate_android.Data.Definitions.QueryType;
import jp.co.pise.projecttemplate_android.Data.Entities.OrmaDatabase;
import jp.co.pise.projecttemplate_android.Data.Infrastracture.Orm.OrmaProvider;
import jp.co.pise.projecttemplate_android.Data.Repository.IRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;
import jp.co.pise.projecttemplate_android.Data.ValueObject.TemplateApplicationContext;

public abstract class OrmaRepository<TEntity> implements IRepository<TEntity> {

    protected static OrmaDatabase orma;

    public OrmaRepository(){
        orma = OrmaProvider.ProvideDatabase(TemplateApplicationContext.GetInstance());
    }

    public List<TEntity> Where(Selector selector, List<RepositoryConditions> conditions)
    {
        for(RepositoryConditions condition : conditions)
        {
            switch (condition.queryType)
            {
                case QueryType.AND:
                    selector.and();
                    break;
                case QueryType.OR:
                    selector.or();
                    break;
            }
            selector.where(OrmaRepositoryConditions.CreateConditionString(condition));
        }
        return selector.toList();
    }
}
