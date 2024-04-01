//
//  ListViewItemModel.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/19.
//

import Foundation

/// 列表Item数据
struct ListItemModel : Identifiable , Codable {
    /// 唯一标识ID
    var id = UUID().uuidString
    
    ///
    var trackId : Int
    
    /// 标题
    let title : String
    
    /// 内容描述
    let description : String
    
    /// 左侧icon图标
    let icon : String
    
    /// 默认未点亮右侧的喜欢按钮
    var isLike : Bool = false;
    
    /// 使用自定义的key代替接口返回的Json字符串中的key
    enum CodingKeys:String,CodingKey {
        case trackId
        case title = "trackCensoredName"
        case description
        case icon = "artworkUrl60"
    }
    
    mutating func update(_ newModel : ListItemModel) {
        self.id = newModel.id
        self.isLike = newModel.isLike
    }
    
}
