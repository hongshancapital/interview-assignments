//
//  AppItem.swift
//  AppList
//
//  Created by haojiajia on 2022/7/7.
//

import Foundation

struct AppContent: Codable {
    var resultCount: Int
    var results: [AppItem]
}

struct AppItem: Codable, Identifiable, Equatable {
    var id: Int
    let image: String
    let name: String
    let description: String
    var isLike: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case image = "artworkUrl60"
        case name = "trackName"
        case description
    }
    
}
