//
//  SectionModel.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/12/9.
//

import Foundation
import SwiftUI

class SectionModel: Identifiable, Codable {
    
    let id: String
    let header: String
    var items: [ItemModel]
    var isChecked: Bool
    
    init(id: String = UUID().uuidString, header: String, items: [ItemModel] = [], checked: Bool = false) {
        
        self.id = id
        self.header = header
        self.items = items
        self.isChecked = checked
        
    }
    
    func addItems(items: [ItemModel]) {
        self.items = items
    }
    
    func changeCheck(checked: Bool) {
        self.isChecked = checked
    }
}
