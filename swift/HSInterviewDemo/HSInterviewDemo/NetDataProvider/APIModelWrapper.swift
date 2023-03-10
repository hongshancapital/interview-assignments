//
//  APIModelWrapper.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import Foundation
import APIService

/// 网络请求结果最外层Model
public protocol APIModelWrapper {
    associatedtype DataType: APIDefaultJSONParsable

    var code: Int { get }

    var msg: String { get }

    var data: DataType? { get }
}
