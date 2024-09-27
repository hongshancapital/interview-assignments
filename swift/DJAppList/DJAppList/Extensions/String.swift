//
//  String.swift
//  AppList
//
//  Created by haojiajia on 2022/7/10.
//

import Foundation
import CryptoKit

extension String {
    var md5: String {
        let computed = Insecure.MD5.hash(data: self.data(using: .utf8)!)
        return computed.map { String(format: "%02hhx", $0) }
            .joined()
    }
}

public extension Array where Element: Equatable {
    
    /// 去除数组重复元素
    /// - Returns: 去除数组重复元素后的数组
    func removeDuplicate() -> Array {
        return self.enumerated().filter { (index,value) -> Bool in
            return self.firstIndex(of: value) == index
        }.map { (_, value) in
            value
        }
    }
}

