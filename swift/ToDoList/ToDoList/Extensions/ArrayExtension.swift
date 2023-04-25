//
//  ArrayExtension.swift
//  ToDoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import Foundation
import CloudKit

extension Array where Element : Equatable {
    mutating func removeObject(_ obj:Element) {
        if let index = firstIndex(of: obj){
            remove(at: index)
        }
    }
}
