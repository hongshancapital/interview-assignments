//
//  AppModel.swift
//  App
//
//  Created by lizhao on 2022/9/20.
//

import Foundation


typealias ModelSQLID = UInt64


enum LoadMoreViewState: CustomStringConvertible {
   case hidden, loading, noMoreData
 
   var description: String {
       switch self {
       case .loading:
           return "  Loading..."
       default:
           return "no more data"
       }
   }
}

struct AppModel: Codable {
    var artworkUrl60: URL
    var description: String
    var trackId: Int
    var trackName: String
}

class AppModelWrapper {
    var app: AppModel
    var isFavorite: Bool
    
    init(_ app: AppModel) {
        self.app = app
        self.isFavorite = Bool.random() // 随机产生
    }
}

struct AppFavoriteResult: Codable {
    var isFavorited: Bool
    var id: Int
}
