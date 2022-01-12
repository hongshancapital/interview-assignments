//
//  WMModel.swift
//  WMSwiftUICode
//
//  Created by 王明民 on 2022/1/5.
//

import Foundation
import Combine

final class WMModelData: ObservableObject {
    @Published var todos: [String:[WMModel]] = load()
    @Published var todoKeys:[String] = [
        "SwiftUI Essentials",
        "Drawing And Animation",
        "App Design and Layout",
        "Framework Integration",
    ]
    
    var firstKey : String {
        todoKeys[0]
    }

    func addTodo(todo: String , _ key: String?) {
        let theGourp = key ?? firstKey
        
        let choosedkey = WMModel.TodoType(rawValue: theGourp)
        
        if todoKeys.contains(theGourp) {
            var temp = todos[theGourp]
            let todoModel = WMModel(id: temp?.count ?? 0, message: todo, check: false, line: false, type: choosedkey!)
            temp?.insert(todoModel, at: 0)
            todos[theGourp] = temp
        }else {
            todoKeys.insert(theGourp, at: 0)
            let todoModel = WMModel(id: 0, message: todo, check: false, line: false, type: choosedkey!)
            todos[theGourp] = [todoModel]
        }
    }
    
    func deleteTodo(todo: WMModel) {
        if todoKeys.contains(todo.type.rawValue) {
            var temp = todos[todo.type.rawValue]
            temp = temp?.filter({ model in
                model.id != todo.id
            })
            todos[todo.type.rawValue] = temp
            
        }
    }
    
    func changeTheMessage(todo: WMModel, _ message: String?) {
        var newTodo = todo
        if message != nil {
            newTodo.message = message!
        }
        
        if todoKeys.contains(newTodo.type.rawValue) {
            
            var temp = todos[newTodo.type.rawValue]
            if temp!.count == 1 {return}
            
            for i in 0..<temp!.count {
                let model:WMModel = temp![i]
                if model.id == newTodo.id {
                    temp?[i] = newTodo
                    break
                }
            }
            todos[todo.type.rawValue] = temp
            
        }
        
    }
    
    func changeTodoCheck(todo: WMModel) {
        if todoKeys.contains(todo.type.rawValue) {
            
            var temp = todos[todo.type.rawValue]
            if temp!.count == 1 {return}
            
            for i in 0..<temp!.count {
                let model:WMModel = temp![i]
                if model.id == todo.id {
                    temp!.remove(at: i)
                    break
                }
            }
            
            var tempModel = todo;
            tempModel.check.toggle()
            
            if todo.check == true {
                temp!.insert(tempModel, at: 0)
            } else {
                for i in 0..<temp!.count {
                    let model:WMModel = temp![i]
                    if model.check == true {
                        temp!.insert(tempModel, at: i)
                        break
                    }
                    if i == temp!.count-1 {
                        temp!.append(tempModel)
                        break
                    }
                }
            }
            
            todos[todo.type.rawValue] = temp
            
        }
    }
    
}



func load() -> [String:[WMModel]] {
    var models: [String:[WMModel]] = [:]
    for index in 0...12 {
        var todo = WMModel(id: index, message: "I want to do Something ,\(index)line", check: false, line: false)
        if (0..<3).contains(index) {todo.type = .oneType}
        if (3..<5).contains(index) {
            todo.type = .twoType
            todo.check = true
        }
        if (5..<8).contains(index) {
            todo.type = .threeType
            todo.check = true
        }
        if (8...12).contains(index) {
            todo.type = .fourType
            todo.check = true
        }

        var arr:[WMModel] = models[todo.type.rawValue] ?? []
        arr.append(todo)
        models[todo.type.rawValue] = arr

    }
    return models
}

struct WMModel: Hashable, Identifiable {
    var id: Int
    var message: String
    var check: Bool  //是否点击
    var line: Bool   //划线
    var type: TodoType = .oneType
    
    enum TodoType: String {
        case oneType = "SwiftUI Essentials"
        case twoType = "Drawing And Animation"
        case threeType = "App Design and Layout"
        case fourType = "Framework Integration"
    }

}


class WMStateModel: ObservableObject {
    @Published var select: Bool
    init(select: Bool) {
        self.select = select
    }
}
