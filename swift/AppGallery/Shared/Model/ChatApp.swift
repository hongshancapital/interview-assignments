//
//  ChatApp.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

struct ChatApp: Decodable, Identifiable, Equatable {
    
    let id: Int
    
    let name: String
    
    let iconUrl: String
    
    let description: String
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case name = "trackName"
        case iconUrl = "artworkUrl60"
        case description = "description"
    }
    
}

extension ChatApp {
    
    var isMarked: Bool {
        get {
            return LocalMarkService.shared.isMarked(for: id)
        }
        set {
            LocalMarkService.shared.update(by: id, marked: newValue)
        }
    }
}
