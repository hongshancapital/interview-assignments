//
//  Double+Util.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

extension Double {
    /// 格式化money 保留两位小数
    func toMoneyStr() -> String {
        return String(format: "$%.2f", self)
    }
}
