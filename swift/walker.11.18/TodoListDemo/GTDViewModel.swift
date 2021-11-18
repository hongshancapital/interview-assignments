//
//  GTDViewModel.swift
//  TodoListDemo
//
//  Created by walker on 2021/11/17.
//

import Foundation

class GTDViewModel: ObservableObject {
    @Published var tasks = [GTDTaskModel]()
    
    init() {
        
        // demo use
        if tasks.isEmpty {
            addTask(group: "SwiftUI Essentials", content: "Building Lists and Navigation")
            addTask(group: "SwiftUI Essentials", content: "Creating and Combining Views")
            addTask(group: "SwiftUI Essentials", content: "Handling User Input", checked: true)
            addTask(group: "Drawing and Animation", content: "Anamating Views and Transitions")
            addTask(group: "Drawing and Animation", content: "Drawing Paths and Shapes", checked: true)
            addTask(group: "App Design and Layout", content: "Composing Comples iInterfaces")
            addTask(group: "App Design and Layout", content: "Working with UI Controls")
            addTask(group: "Framework Intergration", content: "Interfacing With UIKit")
            addTask(group: "Framework Intergration", content: "Creating a watchOS App")
            addTask(group: "Framework Intergration", content: "Creating a macOS App", checked: true)
            
        }
        
        // todo: restore from userdefaults
    }

    func addTask(group: String, content: String, checked: Bool = false) {
        tasks.append(GTDTaskModel(group: group, content: content, checked: checked))
    }
    
    // 修改任务
    // struct是value type，直接inline修改没用
    // 用删除和插入简化处理
    func updateTaskContent(for id: String, _ content: String) {
        guard let index = tasks.firstIndex(where: {$0.id == id}) else {
            return
        }
        var task = tasks[index]
        tasks.remove(at: index)
        task.content = content.trimmingCharacters(in: .whitespacesAndNewlines)
        if task.content.count > 0 {
            // 内容为空则处理为删除当前任务
            tasks.insert(contentsOf: [task], at: index)
        }
    }
    
    // check状态即时生效
    func toggleTaskStatus(for id: String) {
        guard let index = tasks.firstIndex(where: {$0.id == id}) else {
            return
        }
        var task = tasks[index]
        tasks.remove(at: index)
        task.checked = !task.checked
        tasks.insert(contentsOf: [task], at: index)
    }
}
