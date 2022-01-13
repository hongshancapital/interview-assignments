//
//  MainData.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI
import Combine

class ToDoModel: ObservableObject, Identifiable {
    let id = UUID()
    @Published var title: String
    @Published var isCompleted: Bool
    
    init (title: String, isCompleted: Bool) {
        self.title = title
        self.isCompleted = isCompleted
    }
}

protocol Copying {
    init(original: Self)
}

extension Copying {
    func copy() -> Self {
        return Self.init(original: self)
    }
}

extension Array where Element: Copying {
    func clone() -> Array {
        var copiedArray = Array<Element>()
        for element in self {
            copiedArray.append(element.copy())
        }
        return copiedArray
    }
}

class GroupModel : ObservableObject , Identifiable , Copying{
    required init(original: GroupModel) {
        self.title = original.title
        self.toDoList = original.toDoList
    }
    
    let id = UUID()
    @Published var title : String
    @Published var toDoList: [ToDoModel]
    
    init (title: String, toDoList: [ToDoModel]) {
        self.title = title
        self.toDoList = toDoList
    }

}


class MainData : ObservableObject {
    let id = UUID()
    static let shared = MainData()
    @Published var groupArray : [GroupModel]
    
    init () {
        self.groupArray = [
            GroupModel(title: "SwiftUI Essentials", toDoList: [
                ToDoModel(title: "Building Lists and Navigation", isCompleted: false),
                ToDoModel(title: "Greating and Combining Views", isCompleted: true),
                ToDoModel(title: "Handling User Input", isCompleted: true),
            ]),
            GroupModel(title: "Drawing and Animation",toDoList: [
                ToDoModel(title: "Animating Views and Transitions", isCompleted: false),
                ToDoModel(title: "Drawing Paths and Shapes", isCompleted: true),
                ToDoModel(title: "Handling User Input", isCompleted: true),
            ]),
            GroupModel(title: "App Design and layout ",toDoList:  [
                ToDoModel(title: "Composing Complex Interfaces", isCompleted: false),
                ToDoModel(title: "Working with UI Controls", isCompleted: false),
            ]),
            GroupModel(title: "Framework Integration",toDoList:  [
                ToDoModel(title: "Interfacing with UIKit", isCompleted: false),
                ToDoModel(title: "Creating a watchOS App", isCompleted: false),
                ToDoModel(title: "Creating a MacOS App", isCompleted: false),
            ]),
        ]
    }
    
    //删除事项
    func removeTodo(formGroupModel: GroupModel,removeTodo: ToDoModel) -> Void {
        if let groupIndex = self.groupArray.firstIndex(where: {$0 === formGroupModel}) {
            let nowGroupArray = self.groupArray
            if let index = nowGroupArray[groupIndex].toDoList.firstIndex(where: {$0 === removeTodo}){
                nowGroupArray[groupIndex].toDoList.remove(at: index)
                withAnimation {
                    self.groupArray = nowGroupArray
                }
            }
        }
    }
    
    //点击选中或取消事项
    func selectTodo(formGroupModel: GroupModel,selectTodo: ToDoModel) -> Void {
        selectTodo.isCompleted.toggle()
        if let groupIndex = self.groupArray.firstIndex(where: {$0 === formGroupModel}) {
            let nowGroupArray = self.groupArray
            var todoArray = nowGroupArray[groupIndex].toDoList
            
            todoArray = todoArray.sorted { $0.isCompleted != true && $1.isCompleted == true  }
            nowGroupArray[groupIndex].toDoList = todoArray
            withAnimation {
                self.groupArray = nowGroupArray
            }
        }
    }
    
    //添加事项
    func addTodo(formGroupModel: GroupModel,todoModel: ToDoModel) -> Void {
        if let groupIndex = self.groupArray.firstIndex(where: {$0 === formGroupModel}) {
            let nowGroupArray = self.groupArray
            var todoArray = nowGroupArray[groupIndex].toDoList
            todoArray.append(todoModel)
            
            todoArray = todoArray.sorted { $0.isCompleted != true && $1.isCompleted == true  }
            nowGroupArray[groupIndex].toDoList = todoArray
            withAnimation {
                self.groupArray = nowGroupArray
            }
        }
    }
    
    //添加分组
    func addGroup(groupModel: GroupModel, onSuccess: @escaping (Bool) -> () = { _ in }) -> Void {
        var nowGroupArray = self.groupArray
        //判断分组是否已存在
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
    
    //按关键字搜索Todo并返回
    func searchTodo(searchText : String) -> [GroupModel] {
        print(self.groupArray)
        if searchText.count == 0 {
            return self.groupArray
        }
        let nowGroupArray = self.groupArray.clone()
        var resultArray : [GroupModel] = []
        for groupModel in nowGroupArray {
            let todoArray = groupModel.toDoList.filter{(todo) -> Bool in
                let title : String = todo.title
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
