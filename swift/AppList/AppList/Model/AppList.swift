//
//  AppList.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/9.
//

import Foundation

struct AppList: Codable, Hashable {
    var resultCount: Int
    var results: [AppInfo]
}
