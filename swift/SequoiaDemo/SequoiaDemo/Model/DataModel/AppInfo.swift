//
//  AppInfo.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/3.
//

import Foundation

///Json数据对应DataModel
struct AppList : Codable {
    var resultCount: Int
    var results: [AppInfo]
}

struct AppInfo: Codable {
    var trackId: Int
    var trackName: String
    var description: String
    var artworkUrl512: String
}
