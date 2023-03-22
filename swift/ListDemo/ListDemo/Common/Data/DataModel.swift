//
//  DataModel.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation
struct DataModel {
    struct AppItems: Codable {
        let resultCount: Int
        let results: [AppItem]
    }
    // trackCensoredName
    struct AppItem: Codable {
        enum CodingKeys: String, CodingKey {
            case artworkUrl100 
            case description
            case trackId
            case title = "trackCensoredName"
        }
        let artworkUrl100: String
        let description: String
        let trackId: Int
        let title: String
    }
}


extension DataModel.AppItem: Identifiable {
    public typealias ID = Int
    public var id: Int {
        return trackId
    }
}
