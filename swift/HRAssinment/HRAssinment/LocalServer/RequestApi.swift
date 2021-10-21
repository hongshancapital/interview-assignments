//
//  RequestApi.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/21.
//

import Foundation

enum RequestApi {
    case search(_ key: String?)
    case other

    var url: URL {
        switch self {
        case let .search(key):
            var components = URLComponents(string: "http://localhost:10086/search")!
            var querys = [URLQueryItem]()
            if let keyword = key {
                querys.append(.init(name: "keyword", value: keyword))
            }
            components.queryItems = querys
            return components.url!
        case .other:
            return URL(string: "http://localhost:10086/search")!
        }
        
    }
}
