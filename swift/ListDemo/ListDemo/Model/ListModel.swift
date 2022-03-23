//
//  ListModel.swift
//  ListDemo
//
//  Created by mac on 2022/3/18.
//

import SwiftUI

struct ListModel: Hashable, Codable {
    
    //ID
    var trackId: Int
    //描述
    var description: String
    //图
    var artworkUrl100: String
    //标题
    var sellerName: String

    init(trackId: Int, description: String, artworkUrl100: String, sellerName: String){
        self.trackId = trackId
        self.description = description
        self.artworkUrl100 = artworkUrl100
        self.sellerName = sellerName
    }
}
