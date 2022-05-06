//
//  ListModel.swift
//  Demo
//
//  Created by 盼 on 2022/4/14.
//

import Foundation

struct ListModel: Hashable, Codable, Identifiable {
    var id: Int {
        trackId
    }
    
    var trackId: Int
    var trackCensoredName: String
    var description: String
    var artworkUrl60: String
    var isGameCenterEnabled: Bool = false
}
