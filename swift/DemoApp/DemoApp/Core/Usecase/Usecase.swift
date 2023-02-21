//
//  Usecase.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import Foundation

/// 用例基类，通过实例化子类代表不同的操作
/// P - Param: 所需的参数
/// T - Success Result 执行后的结果
class Usecase<P: Any, T: Any> {
    func call(params: P) async -> Result<T, Failure> { return .failure(Failure()) }
}
