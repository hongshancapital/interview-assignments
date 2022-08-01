//
//  AppModel.swift
//  AppList
//
//  Created by 钟逊超 on 2022/8/1.
//

import Foundation

struct AppModel: Codable, Identifiable {
    // 暂不解析未使用属性.
    var iconUrl: String
    var name: String
    var description: String
    var id: String

    enum CodingKeys: String, CodingKey {
        case iconUrl = "artworkUrl512"
        case name = "trackName"
        case description = "description"
        case id = "bundleId"
    }
}

struct AppResultModel: Codable {
    var resultCount: Int
    var results: [AppModel]
}
