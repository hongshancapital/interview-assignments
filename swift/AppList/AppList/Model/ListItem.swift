//
//  ListItem.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation

struct ListItem: Identifiable, Decodable {
    
    var id: Int
    var imageUrl: String
    var title: String
    var text: String
    var favored: Int?
    
    private enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case imageUrl = "artworkUrl100"
        case title = "trackName"
        case text = "description"
        case favored
    }
}

