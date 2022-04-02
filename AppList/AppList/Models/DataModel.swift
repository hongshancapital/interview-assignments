//
//  DataModel.swift
//  AppList
//
//  Created by 王宁 on 2022/4/1.
//
import SwiftUI

struct DataModel :Decodable, Identifiable{
    
    let id = UUID()
    let trackId: CLong
    let artworkUrl60: String
    let trackCensoredName: String
    let description: String 
    var like = false
    
    enum CodingKeys: String, CodingKey  {
        case trackId
        case artworkUrl60
        case trackCensoredName
        case description
    }
    
}
