//
//  MainData.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import Combine
import SwiftUI

class ToDoModel: ObservableObject, Identifiable {
    let id = UUID()
    @Published var title: String
    var isCompleted: Bool
    
    init(title: String, isCompleted: Bool) {
        self.title = title
        self.isCompleted = isCompleted
    }
}

protocol Copying {
    init(original: Self)
}

extension Copying {
    func copy() -> Self {
        return Self(original: self)
    }
}

extension Array where Element: Copying {
    func clone() -> Array {
        var copiedArray = [Element]()
        for element in self {
            copiedArray.append(element.copy())
        }
        return copiedArray
    }
}

class GroupModel: ObservableObject, Identifiable, Copying {
    required init(original: GroupModel) {
        self.title = original.title
        self.toDoList = original.toDoList
        self.index = original.index
    }
    
    let id = UUID()
    var title: String
    var toDoList: [ToDoModel]
    var index: Int
    
    init(title: String, toDoList: [ToDoModel], index: Int) {
        self.title = title
        self.toDoList = toDoList
        self.index = index
    }
}

class MainData: ObservableObject {
    let id = UUID()
    @Published var groupArray: [GroupModel]
    @Published var searching: Bool
    @Published var searchText: String
    
    init() {
        self.groupArray = [
            GroupModel(title: "SwiftUI Essentials", toDoList: [
                ToDoModel(title: "Building Lists and Navigation", isCompleted: false),
                ToDoModel(title: "Greating and Combining Views", isCompleted: true),
                ToDoModel(title: "Handling User Input", isCompleted: true),
            ], index: 0),
            GroupModel(title: "Drawing and Animation", toDoList: [
                ToDoModel(title: "Animating Views and Transitions", isCompleted: false),
                ToDoModel(title: "Drawing Paths and Shapes", isCompleted: true),
                ToDoModel(title: "Handling User Input", isCompleted: true),
            ], index: 1),
            GroupModel(title: "App Design and layout ", toDoList: [
                ToDoModel(title: "Composing Complex Interfaces", isCompleted: false),
                ToDoModel(title: "Working with UI Controls", isCompleted: false),
            ], index: 2),
            GroupModel(title: "Framework Integration", toDoList: [
                ToDoModel(title: "Interfacing with UIKit", isCompleted: false),
                ToDoModel(title: "Creating a watchOS App", isCompleted: false),
                ToDoModel(title: "Creating a MacOS App", isCompleted: false),
            ], index: 3),
        ]
        self.searching = false
        self.searchText = ""
    }
    
    // 删除事项
    func removeTodo(formGroupModel: GroupModel, removeTodo: ToDoModel) {
        let nowGroupArray = self.groupArray
        if let index = nowGroupArray[formGroupModel.index].toDoList.firstIndex(where: { $0 === removeTodo }) {
            nowGroupArray[formGroupModel.index].toDoList.remove(at: index)
            withAnimation {
                self.groupArray = nowGroupArray
            }
        }
    }
    
    // 点击选中或取消事项
    func selectTodo(formGroupModel: GroupModel, selectTodo: ToDoModel) {
        selectTodo.isCompleted.toggle()
        let nowGroupArray = self.groupArray
        var todoArray = nowGroupArray[formGroupModel.index].toDoList
        
        todoArray = todoArray.sorted { $0.isCompleted != true && $1.isCompleted == true }
        nowGroupArray[formGroupModel.index].toDoList = todoArray
        withAnimation {
            self.groupArray = nowGroupArray
        }
    }
    
    // 添加事项
    func addTodo(formGroupModel: GroupModel, todoModel: ToDoModel) {
        let nowGroupArray = self.groupArray
        var todoArray = nowGroupArray[formGroupModel.index].toDoList
        todoArray.append(todoModel)
        
        todoArray = todoArray.sorted { $0.isCompleted != true && $1.isCompleted == true }
        nowGroupArray[formGroupModel.index].toDoList = todoArray
        withAnimation {
            self.groupArray = nowGroupArray
        }
    }
    
    // 添加分组
    func addGroup(groupModel: GroupModel, onSuccess: @escaping (Bool) -> Void = { _ in }) {
        var nowGroupArray = self.groupArray
        // 判断分组是否已存在
        for model in nowGroupArray {
            if model.title == groupModel.title {
                onSuccess(false)
                return
            }
        }
        nowGroupArray.append(groupModel)
        withAnimation {
            self.groupArray = nowGroupArray
        }
        onSuccess(true)
    }
    
    // 按关键字搜索Todo并返回
    func searchTodo() -> [GroupModel] {
        if self.searchText.count == 0 {
            for groupModel in self.groupArray {
                let todoArray = groupModel.toDoList.filter { todo -> Bool in
                    todo.title.count != 0
                }
                groupModel.toDoList = todoArray
            }
            return self.groupArray
        }
                
        let nowGroupArray = self.groupArray.clone()
        var resultArray: [GroupModel] = []
        for groupModel in nowGroupArray {
            let todoArray = groupModel.toDoList.filter { todo -> Bool in
                let title: String = todo.title
                return title.contains(searchText)
            }
            if todoArray.count > 0 {
                groupModel.toDoList = todoArray
                resultArray.append(groupModel)
            }
        }
        return resultArray
    }
}
