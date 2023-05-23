//
//  ApiState.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/19.
//

import Foundation

public enum ApiCode: Int {
    case Success = 200
    case DataNotFound = 400
    case ParseFailed = 10001
    case NoMoreData = 210
}

public func apiError(_ code: ApiCode) -> NSError {
    var domain = "";
    if (code == ApiCode.Success) {
        domain = "Success";
    } else if (code == ApiCode.DataNotFound) {
        domain = "data not found";
    } else if (code == ApiCode.ParseFailed) {
        domain = "data parse failed";
    } else if (code == ApiCode.NoMoreData) {
        domain = "no more data";
    }
    return NSError(domain: domain, code: code.rawValue, userInfo: nil);
}
