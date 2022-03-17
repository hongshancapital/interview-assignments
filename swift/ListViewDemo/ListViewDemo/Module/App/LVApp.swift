//
//  App.swift
//  ListViewDemo
//
//  Created by HL on 2022/3/15.
//

import Foundation
import SwiftUI
import ObjectMapper

class LVApp: Identifiable, Mappable, ObservableObject {
    
    required init?(map: Map) {

    }
    
    func mapping(map: Map) {
        artworkUrl512 <- map["artworkUrl512"]
        trackCensoredName <- map["trackCensoredName"]
        releaseNotes <- map["releaseNotes"]
    }
    
    init() {

    }

    var id = UUID()
    var artworkUrl512: String?
    var trackCensoredName: String?
    var releaseNotes: String?
    @Published var selected: Bool = false
}
