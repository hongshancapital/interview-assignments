//
//  Apis.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import Foundation


enum Apis {
    case search(_ key: String?)
    case other

    var url: URL {
        switch self {
        case let .search(key):
            var components = URLComponents(string: "http://localhost:1024/search")!
            var querys = [URLQueryItem]()
            if let keyword = key {
                querys.append(.init(name: "keyword", value: keyword))
            }
            components.queryItems = querys
            return components.url!
        case .other:
            return URL(string: "http://localhost:1024/search")!
        }
        
    }
}
