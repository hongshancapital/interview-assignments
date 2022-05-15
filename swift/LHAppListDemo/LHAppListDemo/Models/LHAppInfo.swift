//
//  LHAppInfo.swift
//  LHAppListDemo
//
//  Created by lzh on 2022/3/29.
//

import Foundation

struct LHAppInfoRes : Codable {
    let resultCount : Int
    var results : [LHAppInfo]
    
    private enum CodingKeys : String,CodingKey {
        case resultCount
        case results
    }
}

struct LHAppInfo : Codable,Identifiable {
    var id : Int
    let name : String
    let content : String
    let imgUrl : String
    var isLike : Bool?
    
    private enum CodingKeys: String, CodingKey {
        case imgUrl = "artworkUrl60"
        case name = "trackCensoredName"
        case content = "description"
        case id = "trackId"
        case isLike
    }
    
    mutating func updateWith(info:LHAppInfo) {
        self.id = info.id
        self.isLike = info.isLike
    }
}

