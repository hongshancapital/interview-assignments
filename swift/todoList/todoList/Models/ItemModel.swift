//
//  ItemModel.swift
//  todoList
//
//  Created by deng on 2021/12/6.
//

import Foundation
class ItemModel: Identifiable, Codable {
    let id: String
    let title: String
    var isCompeleted: Bool
    var isEditing: Bool
    var prority: Int

    init(title: String, isCompleted: Bool, isEditing: Bool = false) {
        self.id = UUID().uuidString
        self.title = title
        self.isCompeleted = isCompleted
        self.isEditing = isEditing
        self.prority = isCompleted ? 0 : 1
    }
}
