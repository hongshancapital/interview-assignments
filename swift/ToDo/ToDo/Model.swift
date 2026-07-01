//
//  Model.swift
//  ToDo
//
//  Created by 童雨 on 2021/12/8.
//

import Foundation

class GroupModel: ObservableObject{
    
    var id = UUID()
    @Published var todoModelArr = [ToDoModel]()
    var groupName:String
    
    init(modelArr:Array<ToDoModel>,name:String){
        todoModelArr = modelArr
        groupName = name
    }
}

class ToDoModel: ObservableObject, Hashable{
    
    static func == (lhs: ToDoModel, rhs: ToDoModel) -> Bool {
        return lhs.todoValue == rhs.todoValue && lhs.todoState == rhs.todoState
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(todoValue)
        hasher.combine(todoState)
    }
    
    var id = UUID()
    @Published  var todoValue:String
    @Published  var todoState:Bool
    init(todoValueS:String,todoStateB:Bool){
        todoValue = todoValueS
        todoState = todoStateB
    }
}


