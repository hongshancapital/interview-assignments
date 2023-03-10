//
//  NetworkConstants.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import SwiftUI

enum Env {
    case dev
    case prod
}

struct NetworkConstants {
    static let baseProdURL = URL(string: "https://www.fastmock.site/mock/155307d8ec9de4f5c14c6a1a01768556/xindata")!

    // 不可用，显示使用
    static let baseDevURL = URL(string: "https://www.fastmock.site/mock/155307d8ec9de4f5c14c6a1a01768556/xindata")!
    static let baseMockURL = URL(string: "https://www.fastmock.site/mock/155307d8ec9de4f5c14c6a1a01768556/xindata")!

    static let env: Env = .prod
}

// 下拉刷新延时时间
public let refreshTime: Double = 0.4
// 加载更多延时时间
public let loadMoreTime: Double = 0.3

// 默认的列表条目内边距
public let defaultListItemPadding: EdgeInsets = EdgeInsets(top: 5, leading: 15, bottom: 5, trailing: 15)
