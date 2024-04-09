//
//  Item.swift
//  ListProject
//
//  Created by shencong on 2022/6/9.
//

import SwiftUI

struct ItemModel: Decodable,Identifiable {
    var id: Int

    // 图片url：40*40
    let artworkUrl60: String
    // 标题：1行14号字体
    let trackName: String
    // 子标题：两行 12号字体
    let releaseNotes: String?
    // 是否收藏：
    var isCollect: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId" // id 对应 trackId
        case artworkUrl60
        case trackName
        case releaseNotes
    }
}


struct ItemRespondModel: Codable {
    let resultCount: Int
    let results:[Results]?
    enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
}

struct Results: Codable {
    let artworkUrl60: String
    let trackName: String
    let releaseNotes: String?
    enum CodingKeys: String, CodingKey {
        case artworkUrl60
        case trackName
        case releaseNotes
    }
}
