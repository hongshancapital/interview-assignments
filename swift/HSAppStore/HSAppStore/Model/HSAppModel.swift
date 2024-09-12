//
//  HSAppModel.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/24.
//

import Foundation

struct HSAppModel: Codable, Identifiable, Hashable {
    var id: Int
    let artworkUrl60: String
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
