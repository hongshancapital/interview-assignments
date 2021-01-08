//
//  Decimal+Helper.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import Foundation

extension Decimal {
    func currencyFormatted(showDecimals: Bool) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.currencySymbol = "$"
        formatter.currencyDecimalSeparator = "."
        formatter.currencyGroupingSeparator = ","
        let fractionDigits = showDecimals ? 2 : 0
        formatter.minimumFractionDigits = fractionDigits
        formatter.maximumFractionDigits = fractionDigits
        return formatter.string(from: self as NSNumber)!
    }
}
