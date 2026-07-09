//
//  Application.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import Foundation

struct HomePageListResult: Codable {
    let resultCount: Int
    let results: [Application]
}

struct Application: Codable, Identifiable, Hashable {
    var id: Int {
        hashValue
    }
    let trackName: String
    let artworkUrl60: String
    let description: String    
}
