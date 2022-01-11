package ks.sequoia.bobj;

import com.github.pagehelper.PageHelper;
import ks.sequoia.eobj.DomainEObj;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DomainBObj extends AbstractBObj<DomainEObj> {
    public List<DomainEObj> queryLatest10000Times(){
        PageHelper.startPage(1, 10000);
        return  this.getSqlSessionTemplate().selectList("KS_DOMAIN.queryLatest10000Times");
    }

    public DomainEObj queryEObjByLongDomain(String longDomain){
      return this.getSqlSessionTemplate().selectOne("KS_DOMAIN.queryEObjByLongDomain",longDomain);
    }
    public DomainEObj queryEObjByShortDomain(String longDomain){
       return this.getSqlSessionTemplate().selectOne("KS_DOMAIN.queryEObjByShortDomain",longDomain);
    }

    public boolean addEObj(DomainEObj input){
        if(input.getDomainId() == null){
            input.setDomainId(this.getIdFactory().nextId());
        }
        this.getSqlSessionTemplate().insert("KS_DOMAIN.addEObj",input);
        return true;
    }

}
