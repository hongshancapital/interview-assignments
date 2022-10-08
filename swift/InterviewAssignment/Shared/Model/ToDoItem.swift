//
//  ToDoItem.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/10.
//

import Foundation
import SwiftUI

struct ToDoItem : Identifiable, Hashable, Codable, Comparable {
    static func < (lhs: ToDoItem, rhs: ToDoItem) -> Bool {
        if (lhs.category == rhs.category) {
            if (!lhs.finished && rhs.finished) {
                return true
            } else if (lhs.finished && !rhs.finished) {
                return false
            } else {
                return false
            }
        }
        
        return lhs.category < rhs.category
    }
    
    var id: Int = 0
    var name: String  = ""
    var category: String = ""
    var finished: Bool = false
}
