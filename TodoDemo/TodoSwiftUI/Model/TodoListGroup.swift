//
//  TodoListGroup.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import Foundation

struct TodoListGroup: Identifiable {
    var todos: [TodoItem]
    let groupName: String
    let id: String
     
    init(todos: [TodoItem], groupName: String, id: String = UUID().uuidString) {
        self.groupName = groupName
        self.id = id
        self.todos = todos
    }
    
    func sortTodos() -> TodoListGroup {
        let todos = todos.sorted()
        return TodoListGroup(todos: todos, groupName: groupName,id: id)
    }
}


