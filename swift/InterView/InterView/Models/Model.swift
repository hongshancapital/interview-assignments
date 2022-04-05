//
//  Landmark.swift
//  InterViewDemo
//
//  Created by 黑旭鹏 on 2022/4/2.
//

import SwiftUI

struct Models : Hashable, Codable {
    var resultCount: Int
    var results: [Model]
}

struct Model: Hashable, Codable, Identifiable {
    var trackId: Int
    var id: Int {
        trackId
    }

    var artworkUrl512: String
    var trackCensoredName: String
    var description: String
    
    
//    trackId
    
}
