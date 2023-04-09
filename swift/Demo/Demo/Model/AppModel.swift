//
//  AppItem.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import Foundation

struct AppResponseModel: Codable {
    let resultCount: Int
    let results: [AppModel]
}

class AppModel: Identifiable, Codable {
    let id: Int
    let artworkUrl512: String
    let trackName: String
    let description: String
    var favorite: Bool = false
    
    private enum CodingKeys: String, CodingKey {
        case id = "artistId", artworkUrl512, trackName, description
    }
    
    func favorite(favorite: Bool) {
        self.favorite = favorite
    }
}
