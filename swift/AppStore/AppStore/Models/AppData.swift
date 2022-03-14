//
//  AppData.swift
//  AppStore
//
//  Created by Ma on 2022/3/13.
//

import Foundation

struct AppData : Decodable{
    var resultCount:Int
    var results:[AppItem]
    init(resultCount:Int,results:[AppItem]) {
        self.resultCount = resultCount
        self.results = results
    }
}

struct AppItem : Identifiable,Decodable {
    var id: Int
    
    var title: String
    var description: String
    var artworkUr: String
    var isFavorated: Bool
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case title = "trackName"
        case description = "description"
        case artworkUr = "artworkUrl100"
        case isFavorated
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        id = try container.decode(Int.self, forKey: .id)
        title = try container.decode(String.self, forKey: .title)
        description = try container.decode(String.self, forKey: .description)
        artworkUr = try container.decode(String.self, forKey: .artworkUr)
        isFavorated = AppDataCache.checkFavorated(id)
    }
}
