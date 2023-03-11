//
//  DataModel.swift
//  Homework
//
//  Created by forever on 2023/2/26.
//

import Foundation

struct Response: Codable, Hashable {
    let resultCount: Int
    let results: [Artwork]
}

struct Artwork: Codable, Hashable {
    let artworkUrl60: String
    let trackCensoredName: String
    let artistId: Int
    let artistName: String
}

extension Artwork: Identifiable {
    var id: Int {
        artistId
    }
}
