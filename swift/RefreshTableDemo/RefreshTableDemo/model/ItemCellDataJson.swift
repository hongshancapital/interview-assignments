//
//  ItemCellDataJson.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/14.
//


import Combine
import Foundation

struct DataJsonModel: Codable {
    let resultCount: Int
    let results: [ItemCellDataJson]?
}

struct ItemCellDataJson: Codable,Identifiable {
    let artworkUrl60: String
    let trackCensoredName: String
    let description: String
    let trackId: Int
    
    var isCollection: Bool?
    
    var imageURL: URL? {
        URL(string: artworkUrl60)
    }
    
    var id: Int {
        trackId
    }
}
