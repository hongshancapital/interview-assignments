//
//  TodoList.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import Foundation

struct TodoItem: Identifiable, Codable {
    let content: String
    let compeleted: Bool
    let id: String
    let createTime: TimeInterval
    
    init(content: String, compeleted: Bool, id: String = UUID().uuidString, createTime: TimeInterval = Date.now.timeIntervalSince1970) {
        self.content = content
        self.id = id
        self.compeleted = compeleted
        self.createTime = createTime
    }
    
    func updateCompletion() -> TodoItem {
        return TodoItem(content: content, compeleted: !compeleted, id: id, createTime: createTime)
    }
    
    func updateContent(contentT: String) -> TodoItem {
        return TodoItem(content: contentT, compeleted: compeleted, id: id)
    }
}

extension TodoItem: Comparable {
    static func < (lhs: TodoItem, rhs: TodoItem) -> Bool {
        if lhs.compeleted == rhs.compeleted {
            //new created at top
            return lhs.createTime > rhs.createTime
        }
        return lhs.compeleted.intValue < rhs.compeleted.intValue
    }
}


extension Bool {
    var intValue: Int {
        return self ? 1 : 0
    }
}
