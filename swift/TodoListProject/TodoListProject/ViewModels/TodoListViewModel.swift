//
//  TodoListViewModel.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/13.
//

import Foundation

class TodoListViewModel: ObservableObject {
    
    @Published var todoList: [TodoListGroup] = []
    
    init() {
        self.creatModel();
    }
    
    func creatModel() -> Void {
        let jsonPath = Bundle.main.path(forResource: "TodoListOriginData", ofType: "json")!;
        let jsonData : NSData = try! NSData.init(contentsOf: URL.init(fileURLWithPath: jsonPath));
        let models = try! JSONDecoder().decode([TodoListGroup].self, from: jsonData as Data);
        
        self.todoList = models;
    }
    
    func finishTask(todoListItem: TodoListItem) {
        todoListItem.finishTime = Date().timeStamp;
        self.todoList = todoList;
        
        self.sortedTask()
    }
    
    func unfinishTask(todoListItem: TodoListItem) {
        todoListItem.finishTime = 0;
        self.todoList = todoList;
        
        self.sortedTask()
    }
    
    func addTodoListItem(content:String,groupIndex:NSInteger) {
        if !content.isEmpty {
            let todoListGroup:TodoListGroup = self.todoList[groupIndex];
            
            let todoListItem :TodoListItem = TodoListItem()
            todoListItem.content = content;
            todoListItem.creatTime = Date().timeStamp;
            todoListItem.finishTime = 0;
            
            var tempTodoListItems: [TodoListItem] = []
            tempTodoListItems.append(todoListItem)
            tempTodoListItems.append(contentsOf: todoListGroup.todoListItems)
            todoListGroup.todoListItems = tempTodoListItems
            
            self.todoList = todoList;
            self.sortedTask()
        }
    }
    
    func editTodoListItem(todoListItem: TodoListItem) {
        
        for todolistGroup in todoList {
            var tempTodoListItems: [TodoListItem] = []
            for todoListItem in todolistGroup.todoListItems {
                if (!todoListItem.content.isEmpty) {
                    tempTodoListItems.append(todoListItem)
                }
            }
            todolistGroup.todoListItems = tempTodoListItems
        }
        
        self.todoList = todoList;
        self.sortedTask()
    }
    
    func addGroup(groupTitle:String) {
        let tempGroupItem: TodoListGroup = TodoListGroup()
        tempGroupItem.title = groupTitle
        
        todoList.append(tempGroupItem)
        self.todoList = todoList;
    }
    
    func searchTodoList(searchContent:String) -> [TodoListGroup] {
        var tempTodoListGroups: [TodoListGroup] = []
        for todolistGroup in todoList {
            let tempTodolistGroup:TodoListGroup = TodoListGroup()
            for todoListItem in todolistGroup.todoListItems {
                if todoListItem.content.contains(find: searchContent) {
                    tempTodolistGroup.todoListItems.append(todoListItem);
                }
            }
            if tempTodolistGroup.todoListItems.count > 0 {
                tempTodolistGroup.id = todolistGroup.id
                tempTodolistGroup.title = todolistGroup.title
                tempTodoListGroups.append(tempTodolistGroup)
            }
        }
        return tempTodoListGroups
    }
    
    func updateTodoList() {
        self.todoList = todoList;
        
        self.sortedTask()
    }
    
    func sortedTask() {
        for todoListGroup in todoList {
            let sortedItems = todoListGroup.todoListItems.sorted { todoListItem1, todoListItem2 in
                return todoListItem1.finishTime < todoListItem2.finishTime;
            }
            
            todoListGroup.todoListItems = sortedItems;
        }
    }
}

extension Date {
    var timeStamp: TimeInterval {
        let timeInterval:TimeInterval = self.timeIntervalSince1970;
        return timeInterval;
    }
}

extension String {
    func contains(find: String) -> Bool{
        return self.range(of: find) != nil
    }
}
