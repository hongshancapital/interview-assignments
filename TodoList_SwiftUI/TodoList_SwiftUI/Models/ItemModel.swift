//
//  ItemModel.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/11/23.
//

import Foundation

class ItemModel: Identifiable, Codable {
    let id: String
    let title: String
    var isCompleted: Bool
    var isEditing: Bool
    var prority: Int
    
    init(id: String = UUID().uuidString, title: String, isCompleted: Bool, isEditing: Bool = false) {
        self.id = id
        self.title = title
        self.isCompleted = isCompleted
        self.isEditing = isEditing
        self.prority = isCompleted ? 0 : 1
    }
    
//    func updateCompletion() -> ItemModel {
//        return ItemModel(id: id, title: title, isCompleted: !isCompleted)
//    }
    
}
