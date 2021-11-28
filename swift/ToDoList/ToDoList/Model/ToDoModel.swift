//
//  ToDoModel.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/24.
//

import Foundation
import Combine

class ToDoModel: ObservableObject, Identifiable, Hashable {
    
    static func == (lhs: ToDoModel, rhs: ToDoModel) -> Bool {
        return lhs.tagId == rhs.tagId && lhs.tagId != nil
    }
    
    // just for demo。 这里没有给 model 一个唯一标识符，假定为内容吧。
    var hashValue: Int {
        return self.content?.hashValue ?? -1
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(self.content ?? "")
    }
    
    @Published var content: String? = nil
    @Published var checked: Bool = false
    @Published var tagId: Int64? = nil
    
    init(content: String, checked: Bool, tagId: Int64) {
        self.content = content
        self.checked = checked
        self.tagId = tagId
    }
    
    
}

// TestCode
func createTestToDoDatas() -> [[ToDoModel]] {
    return [
        [
            ToDoModel(content: "Building Lists and Navigation",
                      checked: false, tagId: 1),
            ToDoModel(content: "Creating and Combining Views",
                      checked: false, tagId: 1),
            ToDoModel(content: "Handling User Input",
                      checked: true, tagId: 1)],
        
        [
            ToDoModel(content: "Animating Views and Transitions",
                      checked: false, tagId: 2),
            ToDoModel(content: "Drawing Paths and Shapes",
                      checked: true, tagId: 2)],
        
        [
            ToDoModel(content: "Composing Comples iInterfaces",
                      checked: false, tagId: 3),
            ToDoModel(content: "Working with UI Controls",
                      checked: false, tagId: 3)],
        
        [
            ToDoModel(content: "Interfacing With UIKit",
                      checked: false, tagId: 4),
            ToDoModel(content: "Creating a watchOS App",
                      checked: true, tagId: 4),
            ToDoModel(content: "Creating a macOS App",
                      checked: true, tagId: 4)]
    ]
}
