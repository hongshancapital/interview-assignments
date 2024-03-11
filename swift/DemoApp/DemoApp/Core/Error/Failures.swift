//
//  Failures.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import Foundation

/// 错误类型枚举
public enum FailureType {
    /// 服务端错误
    case server
    /// 缓存错误
    case cache
    /// 其他错误
    case other
}

/// 自定义错误
public struct Failure: Error {
    /// 错误类型
    public var type: FailureType?
    /// 错误信息
    public var message: String
    
    public init(_ message: String, type: FailureType? = nil) {
        self.message = message
        self.type = type
    }
}
