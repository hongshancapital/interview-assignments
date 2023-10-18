//
//  AppItems.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Foundation

struct AppItems: Codable {
    let resultCount: Int
    let results: [AppItem]
}
