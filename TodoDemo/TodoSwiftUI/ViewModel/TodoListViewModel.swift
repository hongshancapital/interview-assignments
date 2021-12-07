//
//  TodoListViewModel.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import Foundation


class TodoListViewModel: ObservableObject {
    @Published var groups : [TodoListGroup] = []
    @Published var editingItem: TodoItem = TodoItem(content: "", compeleted: false)
    
    init() {
        getGoups()
    }
    
    func getGoups() {
        let newItems = getExampleDatas()
        groups.append(contentsOf: newItems)
    }
    
    func addTodoItem(content: String, to group: TodoListGroup) {
        guard let index = groups.firstIndex(where: { $0.id == group.id } ) else {
            return
        }
        let newItem = TodoItem(content: content, compeleted: false)
        groups[index].todos.insert(newItem, at: 0)
        sortGoups(at: index)
    }
    
    func updateItemCompletion(item: TodoItem, in group: TodoListGroup) {
        guard let indexG = groups.firstIndex(where: { $0.id == group.id } ) else {
            return
        }
        guard let indexI = groups[indexG].todos.firstIndex(where: { $0.id == item.id }) else {
            return
        }
        groups[indexG].todos[indexI] = item.updateCompletion()
        sortGoups(at: indexG)

    }
    
    func updateItemContent(item: TodoItem, content: String,in group: TodoListGroup) {
        guard let indexG = groups.firstIndex(where: { $0.id == group.id } ) else {
            return
        }
        guard let indexI = groups[indexG].todos.firstIndex(where: { $0.id == item.id }) else {
            return
        }
        groups[indexG].todos[indexI] = item.updateContent(contentT: content)
    }
    
    func remoteItem(id: String, in group: TodoListGroup) {
        guard let indexG = groups.firstIndex(where: { $0.id == group.id } ) else {
            return
        }
        groups[indexG].todos.removeAll(where: { $0.id == id })
    }
    
    func editing(item: TodoItem) {
        self.editingItem = item
    }
    
    func sortGoups(at index: Int) {
        groups[index] = groups[index].sortTodos()
    }
    
}

extension TodoListViewModel {
    func getExampleDatas() -> [TodoListGroup] {
        var datas = [TodoListGroup]()
    
        let group1 = TodoListGroup(todos: [
            TodoItem(content: "Building Lists and Navigation", compeleted: false),
            TodoItem(content: "Creating and Combining Views", compeleted: true),
            TodoItem(content: "Handing user Input", compeleted: true)
        ], groupName: "SwiftUI Essentials")
        datas.append(group1)
    
        let group2 = TodoListGroup(todos: [
            TodoItem(content: "Animating views and Transitions", compeleted: false),
            TodoItem(content: "Drawing Paths and Shapes", compeleted: true),
        ], groupName: "Drawing and Animation")
        datas.append(group2)
    
        let group3 = TodoListGroup(todos: [
            TodoItem(content: "Composing Complex Interface", compeleted: false),
            TodoItem(content: "Working with UI Controls", compeleted: false),
        ], groupName: "App Design and Layout")
        datas.append(group3)
    
        let group4 = TodoListGroup(todos: [
            TodoItem(content: "Interfacing with UIKit", compeleted: false),
            TodoItem(content: "Creating a watchOS App", compeleted: false),
            TodoItem(content: "Creating a macOS App", compeleted: false),
        ], groupName: "Framework Integration")
        datas.append(group4)
        return datas
    }

}
