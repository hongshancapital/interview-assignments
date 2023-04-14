//
//  appModel.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import Foundation

struct AppResult: Codable {
    var resultCount: Int
    var results: [AppModel]
}

struct AppModel: Codable {
    var trackId: Int
    var trackName: String
    var artworkUrl100: String
    var description: String
    
    enum CodingKeys: String, CodingKey {
        case trackId
        case trackName
        case artworkUrl100
        case description
    }
}
