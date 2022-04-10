package com.work.service;

import com.work.model.Result;

public interface ShortWebsiteNameService{
    Result<String> convertToShortName(String longName);

    Result<String> getLongName(String shortName);
}
