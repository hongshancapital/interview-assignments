//
//  AppModel.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import Foundation


struct AppModel: Hashable, Identifiable, Codable {
    var id: Int
    var iconUrl: String
    var name: String
    var description: String
    var isFavorite: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case iconUrl = "artworkUrl60"
        case name = "trackName"
        case description = "description"
    }
}

enum LoadingState: Int {
    case Loading = 1, LoadMore, NoMoreData
}
