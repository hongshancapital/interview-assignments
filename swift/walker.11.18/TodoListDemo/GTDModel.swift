//
//  GTDModel.swift
//  TodoListDemo
//
//  Created by walker on 2021/11/17.
//

import Foundation

struct GTDTaskModel: Identifiable {
    
    let id: String
    var content: String
    var checked: Bool
    var group: String
    
    // hide default initializer
    private init(id: String, group: String, content: String, checked: Bool) {
        self.id = UUID().uuidString
        self.group = group
        self.content = content
        self.checked = checked
    }
    
    // auto gen id
    init(group: String, content: String, checked: Bool = false) {
        self = GTDTaskModel(id: UUID().uuidString, group: group, content: content, checked: checked)
    }
}

