//
//  Item.swift
//  InterviewDemo
//
//  Created by Peng Shu on 2023/2/24.
//

import Foundation

struct ItemWrapper: Codable {
    var resultCount: Int
    var results: [Item]
}

struct Item: Codable, Identifiable, Equatable {
    var artworkUrl100: URL
    var trackName: String
    var description: String
    
    var uuid: UUID
    var isLiked: Bool
    
    var id: UUID {
        uuid
    }

    enum CodingKeys: CodingKey {
        case artworkUrl100, trackName, description
    }
    
    public init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)

        artworkUrl100 = try container.decode(URL.self, forKey: .artworkUrl100)
        trackName = try container.decode(String.self, forKey: .trackName)
        description = try container.decode(String.self, forKey: .description)
        
        uuid = UUID()
        isLiked = false
    }
}


