//
//  ListGroup.swift
//  ToDoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import Foundation

struct ListGroup : ObservableObject , Codable , Hashable , Identifiable {
    static func == (lhs: ListGroup, rhs: ListGroup) -> Bool {
        return lhs.sectionTitle == rhs.sectionTitle && lhs.listItems == rhs.listItems;
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id.uuidString)
        hasher.combine(sectionTitle)
    }
    
    let id = UUID()
    var sectionTitle: String = ""
    var listItems: [ListItem] = []
}
