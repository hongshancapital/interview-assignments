//
//  Response.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation

/// 请求基本响应结构体
struct Response<T: Codable>: Codable {
    /// 状态码
    var code: Int?
    /// 消息
    var message: String?
    /// 请求数据
    var data: T?

}
