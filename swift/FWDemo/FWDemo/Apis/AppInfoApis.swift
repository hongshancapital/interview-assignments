//
//  AppInfoApis.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation
import SwiftUI


/// app 相关的接口
enum AppInfoApis {
    
    /// 查询app列表数据，param1-页码，param2-单页数据量
    case getAppInfolist(Int, Int)
    
    /// 查询app详情数据，param 数据id
    case getAppDetailInfo(Int)
}

extension AppInfoApis : RequestType {
    
    var baseURL: URL {
        return URL(string: "https://itunes.apple.com/")!
    }
    
    var path: String? {
        switch self {
            case .getAppInfolist(_, _):
                return "search"
            default:
                return nil
        }
    }
    
    var method: Method {
        return .get
    }
    
    var param: [String : String]? {
        switch self {
            case .getAppInfolist(let pageIndex, let pageSize):
                return [
                    "entity" : "software",
                    "term" : "chat",
                    "limit" : "50",  /// 没发现分页入参，就直接取50条数据模拟一下分页
//                    "limit" : "\(pageSize)",
                    "pageIndex" : "\(pageIndex)"
                ]
            default:
                return nil
        }
    }
}


struct AppApiResponse : Decodable {
    let resultCount : Int
    let results : [AppInfo]
}
