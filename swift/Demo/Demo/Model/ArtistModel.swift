//
//  ArtistModel.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import Foundation

struct ArtistModel: Decodable {
    
    @DecodableDefault.Zero var trackId: Int
    @DecodableDefault.EmptyString var trackCensoredName: String
    @DecodableDefault.EmptyString var artworkUrl60: String
    @DecodableDefault.EmptyString var description: String
    
    var isLike: Bool = false
    
    enum CodingKeys: CodingKey {
        case trackId
        case trackCensoredName
        case artworkUrl60
        case description
    }
}

extension ArtistModel {
    var artworkUrl60URL: URL {
        return URL(string: artworkUrl60)!
    }
}

extension ArtistModel: Equatable {
    
    static func ==(lhs: Self, rhs: Self) -> Bool {
        return lhs.trackId == rhs.trackId
    }
    
}

extension ArtistModel: Identifiable {
    var id: Int {
        trackId
    }
    
    
}
