//
//  DataModel.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/11.
//

import Foundation

struct DataModel: Identifiable, Codable {
    var id: UUID = UUID()
    var trackName: String
    var description: String
    var favorite: Bool = false
    var artworkUrl60: String
    
    enum CodingKeys: String, CodingKey {
        case trackName = "trackName"
        case description = "description"
        case artworkUrl60 = "artworkUrl60"
    }
}
