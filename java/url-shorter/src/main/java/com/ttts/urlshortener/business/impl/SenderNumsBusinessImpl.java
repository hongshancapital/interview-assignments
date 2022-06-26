package com.ttts.urlshortener.business.impl;

import com.ttts.urlshortener.base.exception.BusinessException;
import com.ttts.urlshortener.base.model.BaseResultCodeEnums;
import com.ttts.urlshortener.business.SenderNumsBusiness;
import com.ttts.urlshortener.domain.SenderNums;
import com.ttts.urlshortener.repository.SenderNumsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderNumsBusinessImpl implements SenderNumsBusiness {

    private SenderNumsRepository repository;

    @Autowired
    public SenderNumsBusinessImpl(SenderNumsRepository repository) {
        this.repository = repository;
    }

    @Override
    public SenderNums add() {
        try {
            return repository.add();
        } catch (RuntimeException e) {
            throw new BusinessException(BaseResultCodeEnums.ADD_SENDER_NUMS_FAIL, e);
        }
    }
}
