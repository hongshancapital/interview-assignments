//
//  Array+Extension.swift
//  Demo
//
//  Created by 方林威 on 2023/1/30.
//

import Foundation

extension Array {
    
    /// 数组分组
    ///
    /// - Parameters:
    ///   - step: 每组个数
    ///   - range: 分组范围
    /// - Returns: 分组数组
    public func grouped(by step: Int) -> [[Element]] {
        guard step > 0, !isEmpty else {
            return [self]
        }
        return stride(from: 0, to: endIndex, by: step).map {
            Array(self[$0 ... ($0 + (Swift.min(endIndex - $0, step) - 1))])
        }
    }
}
