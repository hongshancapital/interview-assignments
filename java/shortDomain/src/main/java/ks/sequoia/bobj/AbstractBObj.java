package ks.sequoia.bobj;

import ks.sequoia.eobj.EObj;
import ks.sequoia.utils.IdFactory;
import lombok.Data;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

@Data
public abstract class AbstractBObj<T extends EObj> implements BObj<T> {
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private IdFactory idFactory;

    @Override
    public T queryEObjById(Object id) {

        return null;
    }

    @Override
    public List<T> queryEObjByCondition(T input) {
        return null;
    }

    @Override
    public boolean addEObj(T input) {
        return false;
    }

    @Override
    public boolean deleteEObyById(Object id) {
        return false;
    }
}
