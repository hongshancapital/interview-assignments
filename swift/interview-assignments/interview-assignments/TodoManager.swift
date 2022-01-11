//
//  TodoManager.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import Combine
import SwiftUI
/// todo tool manager class
///
/// todo manager
///
///
class TodoManager:NSObject, ObservableObject {
    
    private let cacheFileName: String = "data"
    @Published var todoList: [Todo] = .init()
    @Published var showTodoList: [Todo] = .init()
    @Published var groupDic: [String: [Todo]] = .init()
    @Published var groupNameList: [String] = .init()
    @Published var curGroupName: String = ""
    @Published var searchText: String = ""
    private var cancellables = Set<AnyCancellable>()
    
    
    static let shared = TodoManager()
    
    // Make sure the class has only one instance
    // Should not init or copy outside
    private override init() {
        super.init()
        self.initData()
    }
    
    override func copy() -> Any {
        return self // SingletonClass.shared
    }
    
    override func mutableCopy() -> Any {
        return self // SingletonClass.shared
    }
    
    // Optional
    func reset() {
        // Reset all properties to default value
    }


    private func initData() {
        self.todoList = self.readFromFile()
        self.$todoList.sink { newTodoList in
            self.freshList(newTodoList: newTodoList)
        }.store(in: &self.cancellables)
        self.$searchText.sink { newText in
            self.freshList(newText: newText)
        }.store(in: &self.cancellables)
    }
    
    private func freshList(newTodoList: [Todo]) {
        if self.searchText.count > 0 {
            self.showTodoList = newTodoList.filter { todo -> Bool in
                todo.title.lowercased().contains(self.searchText.lowercased())
            }
        } else {
            self.showTodoList = newTodoList
        }
        self.calcGroup()
    }

    private func freshList(newText: String) {
        if newText.count > 0 {
            self.showTodoList = self.todoList.filter { todo -> Bool in
                todo.title.lowercased().contains(newText.lowercased())
            }
        } else {
            self.showTodoList = self.todoList
        }
        self.calcGroup()
    }
    
    private func calcGroup() {
        self.groupDic = Dictionary(
            grouping: self.showTodoList,
            by: { $0.groupName }
        )
        self.groupNameList = self.groupDic.keys.sorted()
        if self.groupNameList.count > 0, self.curGroupName == "" {
            self.curGroupName = self.groupNameList[0]
        } else if self.groupNameList.count == 0 {
            self.curGroupName = ""
        }
    }
    
    public func addTodo(todo: Todo) {
        self.todoList.append(todo)
        self.save()
    }
    
    public func removeTodo(index: Int) {
        self.todoList.remove(at: index)
        self.save()
    }
    
    public func updateTodo(index: Int, todo: Todo) {
        self.todoList[index] = todo
        self.save()
    }

    public func indexOfTodo(todo: Todo) -> Int {
        var oneIndex = 0
        for oneTodo in self.todoList {
            if oneTodo.id == todo.id {
                break
            }
            oneIndex = oneIndex + 1
        }
        return oneIndex
    }
    
    private func save() {
        do {
            let jsonData = try JSONEncoder().encode(self.todoList)
            let jsonString = String(data: jsonData, encoding: .utf8)!
            UserDefaults.standard.set(jsonString, forKey: self.cacheFileName)
            UserDefaults.standard.synchronize()
        } catch {
            print(error)
        }
    }

    private func readFromFile() -> [Todo] {
        do {
            if let jsonStr = UserDefaults.standard.string(forKey: self.cacheFileName) {
                if let jsonData: Data = jsonStr.data(using: .utf8) {
                    if let decodedSentences = try JSONDecoder().decode([Todo]?.self, from: jsonData) {
                        let tempTodoList: [Todo] = decodedSentences
                        return tempTodoList
                    }
                }
            }
        } catch {
            print(error)
        }
        let tempTodoList = [Todo(title: "Building Lists and Navigation", groupName: "SwiftUI Essentials"), Todo(title: "Creating and Combining Views", groupName: "SwiftUI Essentials"), Todo(title: "Hanline User Input", groupName: "SwiftUI Essentials"), Todo(title: "Animating Views and Transitions", groupName: "Drawing and Animation"), Todo(title: "Drawing Paths and Shapes", groupName: "Drawing and Animation")]
        return tempTodoList
    }
}

class Todo: Identifiable, ObservableObject, Codable {
    var id: UUID = .init()
    var title: String = ""
    var groupName: String = ""
    var checked: Bool = false
    var disable: Bool = true
    private init() {}

    public init(title: String, groupName: String = "") {
        self.title = title
        self.groupName = groupName
    }
}
