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
    
    init(content: String, compeleted: Bool, id: String = UUID().uuidString) {
        self.content = content
        self.id = id
        self.compeleted = compeleted
    }
    
    func updateCompletion() -> TodoItem {
        return TodoItem(content: content, compeleted: !compeleted, id: id)
    }
    
    func updateContent(contentT: String) -> TodoItem {
        return TodoItem(content: contentT, compeleted: compeleted, id: id)
    }
}

extension TodoItem: Comparable {
    static func < (lhs: TodoItem, rhs: TodoItem) -> Bool {
        return lhs.compeleted.intValue < rhs.compeleted.intValue
    }
}


extension Bool {
    var intValue: Int {
        return self ? 1 : 0
    }
}
