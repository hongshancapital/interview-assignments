//
//  DataItem.swift
//  mahefei-swift-answer
//
//  Created by Mars on 2023/2/2.
//

import SwiftUI


class DataItem : Decodable ,ObservableObject {
    
    var trackId:Int = 0000
    
    var trackName:String = ""
    
    var description:String = ""
    
    var artworkUrl512:String = ""
    
    @Published var isLove:Bool = false
    
    enum CodingKeys: String, CodingKey {
        case trackId
        case trackName
        case description
        case artworkUrl512
    }
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(trackId, forKey: .trackId)
        try container.encode(trackName, forKey: .trackName)
        try container.encode(description, forKey: .description)
    }
        
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        trackId = try container.decode(Int.self, forKey: .trackId)
        trackName = try container.decode(String.self, forKey: .trackName)
        description = try container.decode(String.self, forKey: .description)
        artworkUrl512 = try container.decode(String.self, forKey: .artworkUrl512)
    }
    
    
}


