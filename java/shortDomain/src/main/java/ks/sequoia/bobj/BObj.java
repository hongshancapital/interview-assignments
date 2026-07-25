package ks.sequoia.bobj;

import ks.sequoia.eobj.EObj;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = RuntimeException.class)
public interface BObj<T extends EObj> {
    public T queryEObjById(Object id);

    public List<T> queryEObjByCondition(T input);

    public boolean addEObj(T input);

    public boolean deleteEObyById(Object id);
}
