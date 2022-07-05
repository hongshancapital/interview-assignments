//
//  InfoModel.swift
//  ListDemo
//
//  Created by renhe on 2022/6/24.
//

import Foundation

struct ResultModel : Codable{
    var results : [InfoModel]?
}
class InfoModel : ObservableObject,Codable{
    var trackId: Int = 0
    var trackName: String = ""
    var description: String = ""
    var artworkUrl100: String = ""
    @Published var isLiked: Bool = false

    enum CodingKeys: String, CodingKey {
        case trackId,trackName,description,artworkUrl100
    }

    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        trackId = try container.decode(Int.self, forKey: .trackId)
        trackName = try container.decode(String.self, forKey: .trackName)
        description = try container.decode(String.self, forKey: .description)
        artworkUrl100 = try container.decode(String.self, forKey: .artworkUrl100)
    }
    
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(trackId, forKey: .trackId)
        try container.encode(trackName, forKey: .trackName)
        try container.encode(description, forKey: .description)
        try container.encode(artworkUrl100, forKey: .artworkUrl100)
    }
}
