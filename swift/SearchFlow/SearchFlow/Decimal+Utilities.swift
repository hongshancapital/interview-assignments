//
//  Decimal+Utilities.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import Foundation

extension Decimal {
    func currencyFormatted() -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.currencySymbol = "$"
        formatter.currencyDecimalSeparator = "."
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return formatter.string(from: self as NSNumber) ?? ""
    }
}
