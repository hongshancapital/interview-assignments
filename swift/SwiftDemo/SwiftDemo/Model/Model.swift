//
//  Model.swift
//  SwiftDemo
//
//  Created by xz on 2023/2/9.
//

import Foundation

struct Model: Codable {
    var id = UUID()
    
    var trackId: Int64
    
    var trackName: String
    var description: String
    
    var artworkUrl60: String
    var artworkUrl512: String
    var artworkUrl100: String
    
    var favorite: Bool = false
    
    enum CodingKeys: CodingKey {
        case trackId
        case trackName
        case description
        case artworkUrl100
        case artworkUrl60
        case artworkUrl512
    }
}

extension Model: Identifiable {
   
}

struct ModelFeeds: Codable {
    var resultCount: Int
    var results: [Model]
}
