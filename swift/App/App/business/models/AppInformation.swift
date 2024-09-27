//
//  AppInformation.swift
//  App
//
//  Created by august on 2022/3/21.
//

import Foundation
import SwiftUI

struct HomeListResponse: Decodable {
    let resultCount: Int
    let results: [AppInformation]
}

struct AppInformation: Decodable, DynamicProperty {
    let trackId: Int
    let trackName: String
    let description: String
    let artworkUrl60: URL
    @AppStorage var isFavorate: Bool
    
    enum CodingKeys: CodingKey {
        case trackId
        case trackName
        case description
        case artworkUrl60
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        let trackId = try container.decode(Int.self, forKey: .trackId)
        let trackName = try container.decode(String.self, forKey: .trackName)
        let description = try container.decode(String.self, forKey: .description)
        let artworkUrl60 = try container.decode(URL.self, forKey: .artworkUrl60)
        self.init(trackId: trackId, trackName: trackName, description: description, artworkUrl60: artworkUrl60)
    }
    
    init(trackId: Int, trackName: String, description: String, artworkUrl60: URL) {
        self.trackId = trackId
        self.trackName = trackName
        self.description = description
        self.artworkUrl60 = artworkUrl60
        _isFavorate = AppStorage<Bool>.init(wrappedValue: false, "\(trackName)-\(trackId)")
    }
}
