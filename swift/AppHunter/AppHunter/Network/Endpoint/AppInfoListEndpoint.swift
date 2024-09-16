//
//  AppInfoListEndpoint.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation

enum AppInfoListEndpoint: Endpoint {
    case appInfoList(page: Int)
    var path: String {
        return "abc"
    }

    var parameters: [URLQueryItem]? {
        // 组装参数
        if let items = RequestParametersHelper.commonParamters() {
            switch self {
            case let .appInfoList(page):
                var mitems = Array(items)
                mitems.append(URLQueryItem(name: "page", value: String(page)))
                return mitems
            }
        }
        return nil
    }
}
