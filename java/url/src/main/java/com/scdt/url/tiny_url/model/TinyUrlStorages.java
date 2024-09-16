package com.scdt.url.tiny_url.model;

import com.scdt.url.common.configuration.AppConfiguration;
import com.scdt.url.common.util.MapStorages;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TinyUrlStorages extends MapStorages<String,TinyUrl,String, Function<TinyUrl,String>> {

    public  TinyUrlStorages(AppConfiguration appConfiguration){
        super(appConfiguration.getTinyUrlStorageCapacity());
    }
    @Override
    protected Function<TinyUrl,String> getSupplementalKeyFunction(){
        return TinyUrl::getOriginalUrl;
    }
}
