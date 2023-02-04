//

//
//  AppData.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/1/31.
//  
//
    

import Foundation

struct AppData: Codable, Hashable, Identifiable {
    // appid
    var id: Int = 0
    // 名称
    var name: String
    // 图标
    var icon: String
    // 描述信息
    var desc: String
    // 是否喜欢
    var isLike: Bool = false
    
    enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case name = "trackName"
        case icon = "artworkUrl512"
        case desc = "description"
    }
    
    init(id: Int, name: String, icon: String, desc: String, isLike: Bool) {
        self.id = id
        self.name = name
        self.icon = icon
        self.desc = desc
        self.isLike = isLike
    }
    
    init(from decoder: Decoder) throws {
        // 自定义解码
        let container = try decoder.container(keyedBy: CodingKeys.self)
        id = try container.decode(Int.self, forKey: .id)
        name = try container.decode(String.self, forKey: .name)
        icon = try container.decode(String.self, forKey: .icon)
        desc = try container.decode(String.self, forKey: .desc)
        // 处理是否喜欢
        isLike = AppDataAPI.likeList.contains(id)
    }
}
