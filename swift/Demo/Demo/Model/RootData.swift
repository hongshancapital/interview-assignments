//
//  RootData.swift
//  Demo
//
//  Created by 杨立鹏 on 2022/3/29.
//

import SwiftUI

struct RootData: Codable {
    
    /// 数量
    var resultCount = 0
    
    /// 返回的数组
    var results: [Item] = []
}
