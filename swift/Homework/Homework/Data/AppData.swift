//
//  AppData.swift
//  Homework
//
//  Created by miao jiafeng on 2022/6/12.
//

import Foundation

struct AppData: Decodable,Identifiable,Equatable{
    // 用于区分不同的cell
    let id : Int
    // app的图标
    let appUrl: String
    // app的名字
    let appName: String
    // app的介绍
    let appDescription: String
    //是否喜欢app
    var isLike: Bool
}
