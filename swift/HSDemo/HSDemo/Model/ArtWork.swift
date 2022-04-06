//
//  ArtWork.swift
//  SwitUI实战
//
//

import Foundation
import SwiftUI

struct ArtWorlList: Codable {
    var resultCount: Int
    var results: [ArtWork]
}

struct ArtWork: Codable, Identifiable {
    var id = UUID().uuidString
    var artistViewUrl: String
    var trackName: String
    var description: String

    init(id: String, artistViewUrl: String, trackName: String, description: String) {
        self.id = id
        self.artistViewUrl = artistViewUrl
        self.trackName = trackName
        self.description = description
    }

    static func example1() -> ArtWork {
        return ArtWork(id: UUID().uuidString, artistViewUrl: "https://cdn2.thecatapi.com/images/unX21IBVB.jpg", trackName: "Jack Ma", description: "It's a greate artwork!")
    }

    static func example2() -> ArtWork {
        return ArtWork(id: UUID().uuidString, artistViewUrl: "https://cdn2.thecatapi.com/images/unX21IBVB.jpg", trackName: "Andy Lu", description: "It's a bad artwork!")
    }

    enum CodingKeys: String, CodingKey {
        case artistViewUrl = "artworkUrl100"
        case trackName
        case description
    }
}
