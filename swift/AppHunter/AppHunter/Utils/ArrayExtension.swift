//
//  ArrayExtension.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
extension Array {
    subscript(safe range: Range<Index>) -> ArraySlice<Element>? {
        if range.startIndex < 0 || range.endIndex < 0 {
            return nil
        }
        if range.startIndex > range.endIndex {
            return nil
        }
        if range.endIndex > endIndex {
            if range.startIndex >= endIndex {
                return nil
            } else {
                return self[range.startIndex ..< endIndex]
            }
        } else {
            return self[range]
        }
    }
}
