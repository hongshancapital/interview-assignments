//
//  HMApplication.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import Foundation
import UIKit

struct HMApplication:Identifiable, Codable {
    var id: Int {
        trackId ?? 0
    }
    var description: String
    var artworkUrl512: String
    var trackName: String
    var trackId: Int?
    
    // -------- Local property --------
    var collectImageName: String
    var isCollected: Bool
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        if let description = try container.decodeIfPresent(String.self, forKey: .description) {
            self.description = description
        } else {
            self.description = ""
        }

        if let artworkUrl512 = try container.decodeIfPresent(String.self, forKey: .artworkUrl512) {
            self.artworkUrl512 = artworkUrl512
        } else {
            self.artworkUrl512 = ""
        }

        if let trackName = try container.decodeIfPresent(String.self, forKey: .trackName) {
            self.trackName = trackName
        } else {
            self.trackName = ""
        }

        if let trackId = try container.decodeIfPresent(Int.self, forKey: .trackId) {
            self.trackId = trackId
        }
        
        self.collectImageName = "icon001"
        self.isCollected = false
    }
    
    private enum CodingKeys: String, CodingKey {
        case description = "description"
        case artworkUrl512 = "artworkUrl512"
        case trackName = "trackName"
        case trackId = "trackId"
    }
    
}


struct HMApplicationRequestResult: Codable {
    var resultCount: Int
    var results: [HMApplication]
}
