//
//  ResponseModel.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import Foundation

/// API响应数据通用模型
struct ResponseModel<T: Decodable>: Decodable {
    
    /// 数据返回条数
    let resultCount: Int
    
    /// 数据列表
    let results: [T]
}
