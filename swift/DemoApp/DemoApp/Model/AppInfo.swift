//
//  AppInfo.swift
//  DemoApp
//
//  Created by dev on 2023/3/10.
//

import Foundation

struct AppInfo: Hashable,Codable {
     var trackId: Int
     var trackName: String
     var artworkUrl60: String
     var description: String
}

struct ResponseInfo: Hashable, Codable {
    var resultCount: Int
    var results: [AppInfo]
}
