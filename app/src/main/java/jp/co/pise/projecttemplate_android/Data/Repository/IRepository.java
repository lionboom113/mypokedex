package jp.co.pise.projecttemplate_android.Data.Repository;

import java.util.List;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;

/**
 * Repositryの基底クラス
 * 標準的に使用しそうなものを定義している
 * @param <TEntity>
 */
public interface IRepository<TEntity> {

    /**
     * 全件取得
     * @return
     */
    List<TEntity> FindAll();

    /**
     * 指定された条件に基づいて検索を行った結果の最初の1件を返す
     * @param conditions
     * @return 条件に合致した場合は、検索結果の1件目　合致しない場合は、null
     */
    io.reactivex.Observable<PokemonEntity> FirstOrDefault(List<RepositoryConditions> conditions);

    /**
     * 指定された条件に基づいて検索を行った結果を返す
     * @param conditions
     * @return 検索結果
     */
    List<TEntity> Where(List<RepositoryConditions> conditions);

    /**
     * 単一条件指定でデータを削除
     * @param entity
     */
    void Delete(TEntity entity);

    /**
     * 複数条件指定でデータを削除
     * @param entities
     */
    void DeleteRange(List<TEntity> entities);

    /**
     *
     * @param entity
     */
    void Update(TEntity entity);

    /**
     * 指定されたEntityを挿入
     * @param entity
     */
    void Insert(TEntity entity);

    void InsertOrUpdate(TEntity entity);

    int ExecSqlCommand(String sql, Object[] parameters);
}
