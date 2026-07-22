//
//  TodoListItem.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/13.
//

import Foundation
import SwiftUI

class TodoListItem: ObservableObject, Codable, Hashable {
    static func == (lhs: TodoListItem, rhs: TodoListItem) -> Bool {
        return lhs.id == rhs.id
        && lhs.content == rhs.content
        && lhs.creatTime == rhs.creatTime
        && lhs.finishTime == rhs.finishTime
    }
    
    var id: String = ""
    var content: String = ""
    var creatTime: TimeInterval = 0
    var finishTime: TimeInterval = 0
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
}

class TodoListGroup: ObservableObject, Codable, Hashable {
    static func == (lhs: TodoListGroup, rhs: TodoListGroup) -> Bool {
        return lhs.id == rhs.id
        && lhs.title == rhs.title
        && lhs.todoListItems == rhs.todoListItems
    }
    
    var id: String = ""
    var title: String = ""
    var todoListItems:[TodoListItem] = []
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
}
