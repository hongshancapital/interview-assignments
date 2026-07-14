//
//  AppListDataSource.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

struct AppListDataSource: Codable {
    let resultCount: Int
    let results: [AppListModel]
}
