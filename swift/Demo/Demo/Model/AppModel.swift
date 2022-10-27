//
//  AppModel.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

struct AppModel: Hashable, Codable, Identifiable {
    var id: Int {
        get {
            return trackId
        }
    }
    var isFavorite: Bool = false
    var trackId: Int
    var artworkUrl60: String
    var trackName: String
    var description: String
    
    enum CodingKeys: CodingKey {
        case trackId
        case artworkUrl60
        case trackName
        case description
    }
}

struct AppResultData: Codable {
    var resultCount: Int
    var results: [AppModel]
}
