package com.youyuzuo.shortdn.storage;

import com.youyuzuo.shortdn.dto.DomainNameDto;
import com.youyuzuo.shortdn.entry.DomainRecordEntry;
import com.youyuzuo.shortdn.util.IdUtil;
import com.youyuzuo.shortdn.util.NumberUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryStorageImpl implements Storage{
    private Map<String, Map<String, Map<String,DomainRecordEntry>>> longDnIndex = new HashMap<>();
    private Map<String, DomainRecordEntry> shortDnIndex = new HashMap<>();

    public synchronized DomainNameDto save(DomainNameDto dto){
        assert dto != null;
        Map<String, Map<String,DomainRecordEntry>> sha1Index = longDnIndex.get(dto.getLongDnSha1());
        if(sha1Index ==null){
            sha1Index = new HashMap<>();
            longDnIndex.put(dto.getLongDnSha1(), sha1Index);
        }
        Map<String,DomainRecordEntry> md5Index = sha1Index.get(dto.getLongDnMd5());
        if(md5Index ==null){
            md5Index = new HashMap<>();
            sha1Index.put(dto.getLongDnMd5(), md5Index);
        }
        DomainRecordEntry entry = md5Index.get(dto.getLongDN());
        if(entry!=null){
            //返回已有的shortDn
            return DomainNameDto.fromDomainRecordEntry(entry);
        }

        //生成shortDn
        dto.setShortDN(NumberUtil.toDigitsString(IdUtil.nexId()));
        entry = dto.toDomainRecordEntry();
        md5Index.put(dto.getLongDN(),entry);
        shortDnIndex.put(dto.getShortDN(),entry);
        return DomainNameDto.fromDomainRecordEntry(entry);
    }

    public DomainNameDto search(String shortDn){
        DomainRecordEntry entry = shortDnIndex.get(shortDn);
        if(entry==null){
            return null;
        }
        return DomainNameDto.fromDomainRecordEntry(entry);
    }

}
