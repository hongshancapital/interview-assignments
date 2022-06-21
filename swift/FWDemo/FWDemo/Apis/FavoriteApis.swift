//
//  FavoriteApis.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation
import SwiftUI

/// 收藏相关的api
enum FavoriteApis {
    
    /// 收藏app
    case doAppFavorite(Int)
    
    /// 取消收藏app
    case unDoAppFavorite(Int)
    
    /// 收藏一个用户
    case doPersionFavorite(Int)
    
    /// 取消收藏指定用户
    case unDoPersionFavorite(Int)
    
    /// 查询我的app收藏列表
    case getMyAppFavoriteList
}

extension FavoriteApis : RequestType {
    
    var baseURL: URL {
        return URL(string: "https://itunes.apple.com/")!
    }
    
    var path: String? {
        switch self {
            case .getMyAppFavoriteList:
                fallthrough
            case .doAppFavorite(_):
                fallthrough
            case .unDoAppFavorite(_):
                return "app/favorite"
            case .doPersionFavorite(_):
                fallthrough
            case .unDoPersionFavorite(_):
                return "persion/favorite"
        }
    }
    
    var method: Method {
        switch self {
            case .getMyAppFavoriteList:
                return .get
            default:
                return .post
        }
    }
    
    var param: [String : String]? {
        switch self {
            case .doAppFavorite(let id):
                return [
                    "action" : "favorite",
                    "id" : "\(id)",
                ]
            case .unDoAppFavorite(let id):
                return [
                    "action" : "unFavorite",
                    "id" : "\(id)",
                ]
            default:
                return nil
        }
    }
}
