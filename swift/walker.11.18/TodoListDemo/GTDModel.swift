//
//  GTDModel.swift
//  TodoListDemo
//
//  Created by walker on 2021/11/17.
//

import Foundation

struct GTDTaskModel: Identifiable {
    
    let id: Int
    var content: String
    var checked: Bool
    var group: String
    
    // hide default initializer
    init(id: Int, group: String, content: String, checked: Bool = false) {
        self.id      = id
        self.group   = group
        self.content = content
        self.checked = checked
    }
    
    // auto gen id
//    init(id: Int, group: String, content: String, checked: Bool = false) {
//        self = GTDTaskModel(id: id, group: group, content: content, checked: checked)
//    }
}

