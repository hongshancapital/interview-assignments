//
//  RequestParametersHelper.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
protocol RequestParametersHelperProtocol {
    static func commonParamters() -> [URLQueryItem]?
}

struct RequestParametersHelper: RequestParametersHelperProtocol {
    static func commonParamters() -> [URLQueryItem]? {
        return [URLQueryItem(name: "token", value: "123")]
    }
}
