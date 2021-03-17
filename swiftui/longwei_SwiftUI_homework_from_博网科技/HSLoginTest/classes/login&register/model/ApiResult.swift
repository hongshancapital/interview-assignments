//
//  ApiResult.swift
//  HSLoginTest
//
//  Created by lejing_lotawei on 2021/3/8.
//

import Foundation

/// mock network Api
public enum ApiResult{
    case success(result:AnyObject?)
    case failed(Error)
}
extension ApiResult {
    public init(result:AnyObject?,error:Error?=nil){
        if let err = error {
            self = .failed(err)
            return
        }
        guard let res = result else {
            let error = NSError.init(domain: "apiresult", code: -44, userInfo: nil)
            self = .failed(error)
            return
        }
        self = .success(result: res)
    }
    
    
}
