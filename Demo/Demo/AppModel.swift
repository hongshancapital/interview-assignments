//
//  Landmark.swift
//  Demo
//
//  Created by mac on 2022/7/19.
//

import Foundation

struct AppModel: Hashable, Codable{

    var trackId:Int
    var trackName:String
    var description:String
    var artworkUrl100:String
    var collect:Bool?
}

