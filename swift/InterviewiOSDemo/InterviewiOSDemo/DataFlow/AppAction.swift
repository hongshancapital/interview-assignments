//
//  AppAction.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/7.
//

import Foundation

enum AppAction {
    
    // 加载首页数据网络请求事件
    case loadHomePage
    // 首页数据加载结束事件
    case loadHomePageComplate(result: Result<[HomePageViewModel], AppError>)
}
