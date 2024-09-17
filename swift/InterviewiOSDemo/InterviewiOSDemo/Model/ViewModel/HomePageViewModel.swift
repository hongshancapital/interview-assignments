//
//  HomePageViewModel.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/7.
//

import SwiftUI

struct HomePageViewModel: Codable, Identifiable {
    
    let homePageItem: HomePageItem
    
    init(homePageItem: HomePageItem) {
        self.homePageItem = homePageItem
    }
    
    var id: Int { homePageItem.id }
    // cell 图标名称
    var iconName: String { homePageItem.artworkUrl100 }
    // cell 标题
    var rowTitle: String { homePageItem.trackCensoredName }
    // cell 内容描述
    var rowContent: String { homePageItem.description }
    // cell 是否收藏
    var rowIsCollected: Bool { homePageItem.isCollected }
}
