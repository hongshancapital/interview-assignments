//
//  ResultModel.swift
//  AppListDemo
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//
//

import Foundation

/// 接口返回结果通用模型
enum ResultModel<T: Decodable> {
    
    // 接口失败（提示文案）
    case failure(msg:String)
    
    // 接口成功（响应数据，提示文案）
    case success(T, msg: String?)
}
