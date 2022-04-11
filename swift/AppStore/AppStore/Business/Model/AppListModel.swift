//
//  AppListModel.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import Foundation

struct AppListModel: Codable {
    let resultCount: Int
    let results: [AppModel]
}

struct AppModel: Codable, Hashable, Identifiable {
    var id: Int
    let artworkUrl60: URL
    let trackName: String
    let description: String
    var isFavorite: Bool = false
    
    private enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case artworkUrl60
        case trackName
        case description
    }
}
