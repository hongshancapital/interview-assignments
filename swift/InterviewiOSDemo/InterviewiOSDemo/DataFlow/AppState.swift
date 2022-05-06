//
//  AppState.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/6.
//

import Combine

/// APP各种事件的状态
struct AppState {
    // 首页数据的状态
    var homePageData = HomePageData()
}

extension AppState {
    struct HomePageData {
        // 首页cell实体模型数据
        var homePageItemEntity: HomePageItem?
        // 首页数据源
        var homePageList: [HomePageViewModel]?
        // 是否正在请求首页数据
        var loadingHomePage = false
        // 可选绑定
        var allHomePageList: [HomePageViewModel] {
            guard let data = homePageList else {
                return []
            }
            return data
        }
    }
}
