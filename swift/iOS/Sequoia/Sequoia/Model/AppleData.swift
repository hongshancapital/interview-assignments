//
//  AppleData.swift
//  Sequoia
//
//  Created by 徐锐 on 2024/4/11.
//

import Foundation

struct AppleData: Hashable, Codable, Identifiable {
    //id
    var id: Int = 0
    var trackId: Int = 0
    //图标url
    var artworkUrl60: String = ""
    //标题: 最多展示2行
    var trackName: String = ""
    //内容:最多展示2行
    var description: String = ""
    //是否喜爱
    var isFavorite: Bool = false
}
