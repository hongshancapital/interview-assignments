//
//  OTAppData.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import Foundation

struct AppData: Codable {
    let results: [AppModel]
}

struct AppModel: Codable, Identifiable, Hashable {
    let trackId: Int
    let artworkUrl60: String
    let trackName: String
    let description: String
    var id: Int {trackId}
    var isFavorite: Bool = false
    
    private enum CodingKeys: String, CodingKey {
        case trackId
        case artworkUrl60
        case trackName
        case description
    }
    
}
