//
//  SocialAppModel.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/25.
//

import Foundation

struct SocialAppModel: Decodable, Identifiable {
    let id = UUID()
    let trackName: String
    let description: String
    let artworkUrl60: URL
    var isFavorite = false

    enum CodingKeys: String, CodingKey {
        case trackName
        case description
        case artworkUrl60
    }
}
