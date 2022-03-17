//
//  AppAPI.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/16.
//

import UIKit
import Moya

let AppProvider = MoyaProvider<AppAPI>()

// https://itunes.apple.com/search?entity=software&limit=50&term=chat
enum AppAPI {
    case search(entity: String, limit: Int, term: String)
}

extension AppAPI: TargetType {
    var baseURL: URL {
        return URL(string: "https://itunes.apple.com")!
    }
    
    var path: String {
        switch self {
        case .search:
            return "/search"
        }
    }
    
    var method: Moya.Method {
        return .get
    }
    
    var task: Task {
        switch self {
        case .search(let entity, let limit, let term):
            return .requestParameters(parameters: ["entity": entity, "limit": limit, "term": term], encoding: URLEncoding.default)
        }
    }
    
    var headers: [String : String]? {
        return [:]
    }
    
    
}
