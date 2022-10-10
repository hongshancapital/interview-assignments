//
//  ListModel.swift
//  AppList
//
//  Created by mengyun on 2022/3/12.
//

import Foundation

protocol ListCellDataProtocol {
    var trackId: Int { get }
    var artworkUrl: String { get }
    var trackName: String { get }
    var description: String { get }
}

struct ListCellModel: Codable, ListCellDataProtocol {
    var trackId: Int = 0
    var artworkUrl60: String = ""
    var artworkUrl512: String = ""
    var artworkUrl100: String = ""
    var trackName: String = ""
    var description: String = ""
    
    var artworkUrl: String {
        if !artworkUrl60.isEmpty { return artworkUrl60 }
        if !artworkUrl100.isEmpty { return artworkUrl100 }
        return artworkUrl512
    }
}

struct ListModel: Codable {
    var resultCount: Int = 0
    var results: [ListCellModel] = []
}
