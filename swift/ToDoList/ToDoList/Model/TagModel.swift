//
//  TagModel.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/24.
//

import Foundation

struct TagModel {
    var name: String = ""
    var id: Int64 = -1
    
    var vaild: Bool {
        return id > 0
    }
}


// TestCode
func createTestTagDatas() -> [Int64: TagModel] {
    return [
        1: TagModel(name: "SwiftUI Essentials", id: 1),
        2: TagModel(name: "Drawing and Animation", id: 2),
        3: TagModel(name: "App Design and Layout", id: 3),
        4: TagModel(name: "Framework Intergration", id: 4)
    ]
}

func justTestTagNames() -> [String] {
    return [
        "SwiftUI Essentials",
        "Drawing and Animation",
        "App Design and Layout",
        "Framework Intergration"
    ]
}
