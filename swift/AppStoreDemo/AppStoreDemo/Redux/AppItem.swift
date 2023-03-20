//
//  AppItem.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import Foundation

struct AppItem: Decodable, Identifiable {
    
    let id: Int64
    let title: String
    let desc: String
    let photoUrl: String
    var collected: Bool = false
    
    enum CodingKeys: CodingKey {
        case trackId
        case sellerName
        case trackCensoredName
        case description
        case artworkUrl60
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        let sellname: String = try container.decode(String.self, forKey: .sellerName)
        let trackname: String = try container.decode(String.self, forKey: .trackCensoredName)
        self.id = try container.decode(Int64.self, forKey: .trackId)
        self.title = "\(sellname) \(trackname)"
        self.desc = try container.decode(String.self, forKey: .description)
        self.photoUrl = try container.decode(String.self, forKey: .artworkUrl60)
    }
    
    init(id: Int64,
         title: String,
         desc: String,
         photoUrl: String) {
        self.id = id
        self.title = title
        self.desc = desc
        self.photoUrl = photoUrl
    }
}

struct PaginationResponse: Decodable {
    var data: [AppItem]
    var pageIndex: Int = 0
    var isEnd: Bool = false
    
    enum CodingKeys: CodingKey {
        case results
    }
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.data = try container.decode([AppItem].self, forKey: .results)
    }
}
