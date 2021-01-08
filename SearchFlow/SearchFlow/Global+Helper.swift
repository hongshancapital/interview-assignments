//
//  Global+Helper.swift
//  SearchFlow
//
//  Created by evan on 2021/1/7.
//

import Foundation

typealias ActionClosure = () -> Void

@discardableResult
func given<T, U>(_ x: T?, _ f: (T) throws -> U?) rethrows -> U? {
    guard let x = x else { return nil }
    return try f(x)
}
