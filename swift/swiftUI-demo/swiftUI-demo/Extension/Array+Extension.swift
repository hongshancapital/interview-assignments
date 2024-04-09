//
//  Array+Extension.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/25.
//

import Foundation

extension Array {
    // 去重
    public func filterDuplicates<E: Equatable>(_ filter: (Element) -> E) -> [Element] {
        var result = [Element]()
        for value in self {
            let key = filter(value)
            if !result.map({filter($0)}).contains(key) {
                result.append(value)
            }
        }
        return result
    }
}
