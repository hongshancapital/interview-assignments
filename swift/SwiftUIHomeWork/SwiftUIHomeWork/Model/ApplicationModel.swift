//
//  ApplicationModel.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import Foundation
struct ApplicationModel:Codable,Hashable{
    let resultCount: Int
    var results: [ApplicationInfo]
}
struct ApplicationInfo:Codable,Hashable{
    var trackId: Int
    var trackCensoredName: String
    var description: String
    var artworkUrl60: String
}
