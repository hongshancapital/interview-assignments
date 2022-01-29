//
//  TodoModel.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/26.
//

import Foundation

struct TodoItem: Codable {
    /// uuid
    let ID: String
    /// create timestamp inteval, unit ms
    let createTS: Double
    /// category ID, uuid
    let categoryID: String
    /// content
    let todoContent: String
    /// done
    var done: Bool
    /// use by list diff, update the cell which really changed
    var listID: String {
        return "\(ID)_\(createTS)_\(categoryID)_\(todoContent)_\(done)"
    }
    
    init(ID: String = UUID().uuidString,
         createTS: Double = Date().timeIntervalSince1970,
         categoryID: String,
         todoContent: String,
         done: Bool = false) {
        self.ID = ID
        self.createTS = createTS
        self.categoryID = categoryID
        self.todoContent = todoContent
        self.done = done
    }
}

extension TodoItem: CustomDebugStringConvertible {
    var debugDescription: String {
        return "ID: \(ID), createTS: \(createTS), categoryID: \(categoryID), todoContent: \(todoContent), done: \(done)"
    }
}

struct TodoCategory: Codable {
    /// uuid
    let categoryID: String
    /// create timestamp inteval, unit ms
    let createTS: Double
    /// content
    let categoryName: String
    /// todo items, key: TodoItem.ID, value: TodoItem
    var itemMap: [String: TodoItem]
    
    init(categoryName: String) {
        categoryID = UUID().uuidString
        createTS = Date().timeIntervalSince1970
        self.categoryName = categoryName
        itemMap = [:]
    }
    
    func displayTodos() -> [TodoItem] {
        Array(itemMap.values).sorted { prev, next in
            if (prev.done && !next.done) || (!prev.done && next.done) {
                return prev.done ? false : true
            }
            return prev.createTS < next.createTS
        }
    }
    
    func filter(searchText: String) -> TodoCategory? {
        guard !searchText.isEmpty else {
            return self
        }
        let searchItemMap = itemMap.filter { item in
            return item.value.todoContent.localizedCaseInsensitiveContains(searchText)
        }
        if !searchItemMap.isEmpty {
            var cp = self
            cp.itemMap = searchItemMap
            return cp
        }
        
        if categoryName.localizedCaseInsensitiveContains(searchText) {
            var cp = self
            cp.itemMap = [:]
            return cp
        }
        return nil
    }
    
    var isEmpty: Bool {
        return itemMap.isEmpty
    }
}
