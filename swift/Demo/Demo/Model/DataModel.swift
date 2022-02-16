//
//  DataModel.swift
//  Demo
//
//  Created by Kai on 2022/2/16.
//

import Foundation

class KKData: Codable, Identifiable {
    let resultCount: Int
    let results: [KKModel]
}


class KKModel: ObservableObject, Codable, Identifiable {
    let trackCensoredName: String
    let description: String
    let artworkUrl60: String
    @Published var like = false
    
    enum CodingKeys: String, CodingKey {
        case trackCensoredName
        case description
        case artworkUrl60
        case like
    }
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.trackCensoredName = try container.decode(String.self, forKey: .trackCensoredName)
        self.description = try container.decode(String.self, forKey: .description)
        self.artworkUrl60 = try container.decode(String.self, forKey: .artworkUrl60)
        like = false
    }
    
    func encode(to encoder: Encoder) throws {
        var container = encoder.container(keyedBy: CodingKeys.self)
        try container.encode(like, forKey: .like)
    }
}
