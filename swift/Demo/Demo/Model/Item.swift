//
//  Item.swift
//  Demo
//
//  Created by 杨立鹏 on 2022/3/29.
//

import SwiftUI

struct Item: Codable, Hashable {
    
    /// app icon
    let artworkUrl60: String
    
    /// app 名称
    let trackCensoredName: String
    
    /// app 描述
    let description: String
    
    /// app id
    let trackId: Int
}
