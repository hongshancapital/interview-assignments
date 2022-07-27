//
//  sectionModel.swift
//  todoList
//
//  Created by deng on 2021/12/6.
//

import Foundation
import SwiftUI
class SectionModel: Identifiable, Codable {
    let id: String
    let header: String
    var items: [ItemModel]
    var isChecked: Bool
    init(header: String, items: [ItemModel] = [], checkd: Bool = false) {
        self.id = UUID().uuidString
        self.header = header
        self.items = items
        self.isChecked = checkd
    }

    func AddItems(items: [ItemModel]) {
        self.items = items
    }

    func changCheck(checked: Bool) {
        self.isChecked = checked
    }
}
