//
//  Loadable.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import Foundation

#if canImport(Combine)
import Combine

enum Loadable<T> {
    case notRequested
    case isLoading(last: T?)
    case loaded(T)
    case failed(Error)

    var value: T? {
        switch self {
        case let .loaded(value): return value
        case let .isLoading(last): return last
        default: return nil
        }
    }
    var error: Error? {
        switch self {
        case let .failed(error): return error
        default: return nil
        }
    }
}
#endif
