//
//  AppInfo.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/23.
//

import Foundation

struct AppInfo: Decodable {
    let name: String
    let description: String
    let imageUrl: String
    
    var isFavorited: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case name = "trackName"
        case description = "description"
        case imageUrl = "artworkUrl60"
    }
}

extension AppInfo {
    
    static var preview: Self {
        do {
            let list = try MockData.list()
            return list.randomElement() ?? .default
        } catch {
            return .default
        }
    }
    
    static var `default`: Self {
        .init(name: "name", description: "description", imageUrl: "")
    }
}



struct AppListResponse: Decodable {
    let resultCount: Int
    let results: [AppInfo]
}
