//
//  RequestType.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation

/// 定义request标准，后续可以扩展更多功能支持，比如：request-format、请求加密等
protocol RequestType {
    
    var baseURL : URL { get }
    
    var path : String? { get }
    
    var method : Method { get }
    
    var param : [String : String]? { get }
}
