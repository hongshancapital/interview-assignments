//
//  AppInfo.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/9.
//

import Foundation

struct AppInfo: Decodable, Identifiable {
    var id: Int
    var name: String
    var iconUrl: String
    var description: String
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case name = "trackName"
        case iconUrl = "artworkUrl60"
        case description = "description"
    }
}
