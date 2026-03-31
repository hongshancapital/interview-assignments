//
//  ListItem.swift
//  ToDoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import Foundation
import SwiftUI

class ListItem : ObservableObject, Codable , Hashable ,Identifiable {
    static func == (lhs: ListItem, rhs: ListItem) -> Bool {
        return lhs.title == rhs.title
        && lhs.hasDone == rhs.hasDone
        && lhs.editMode == rhs.editMode
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id.uuidString)
    }
    
    var title: String = "" {
        didSet {
            print("value changed ")
        }
    }
    var hasDone:Bool = false
    var editMode:Bool = false
    let id = UUID()
}
