//
//  Decimal+String.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import Foundation

extension Decimal {
    var formattedAmount: String {
        let formatter = NumberFormatter()
        formatter.generatesDecimalNumbers = true
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        return "$\(formatter.string(from: self as NSDecimalNumber) ?? "")"
    }
}
