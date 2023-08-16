//
//  HomePageItem.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/7.
//

import Foundation

/// 首页数据实体类
struct HomePageItem: Codable, Identifiable {
    let id: Int
    // cell 图标名称
    var artworkUrl100: String
    // cell 标题
    var trackCensoredName: String
    // cell 内容描述
    var description: String
    // cell 是否收藏
    var isCollected = false
}
