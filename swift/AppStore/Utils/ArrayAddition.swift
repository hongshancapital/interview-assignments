//
//  ArrayAddition.swift
//  AppStore
//
//  Created by huyanling on 2022/5/13.
//

import Foundation

extension Array where Element: Equatable {
    mutating func remove(item: Element) {
        if let index = firstIndex(of: item) {
            remove(at: index)
        }
    }

    mutating func remove(items: [Element]) {
        for item in items {
            if let index = firstIndex(of: item) {
                remove(at: index)
            }
        }
    }

    subscript(safe index: Index) -> Element? {
        get {
            return indices.contains(index) ? self[index] : nil
        }
        set {
            guard let value = newValue, index >= 0 else {
                return
            }
            if indices.contains(index) {
                self[index] = value
            } else {
                append(value)
            }
        }
    }
}
