//
//  AppInfo.swift
//  DemoApp
//
//  Created by 王俊 on 2022/8/30.
//

import Foundation

struct AppInfo: Hashable, Codable {
    var trackId: Int
    var trackName: String
    var artworkUrl60: String
    var description: String
}

struct ResponseInfo: Hashable, Codable {
    var resultCount: Int
    var results: [AppInfo]
}
