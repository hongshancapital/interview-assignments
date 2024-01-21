//
//  AppAction.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/3.
//

import Foundation

enum AppAction {
    ///加载首页数据
    case refreshData
    ///数据加载完毕且后续还有数据
    case refreshDataDone(result: Result<[AppInfoViewModel], Error> )
    
    ///加载下一页数据
    case loadNextPage
    ///加载下一页数据完毕
    case loadNextPageDone(result: Result<[AppInfoViewModel], Error>)
    
    ///没有后续数据
    case endOfData
    
    ///点击喜欢按钮
    case tapLike(appId: String)
}
