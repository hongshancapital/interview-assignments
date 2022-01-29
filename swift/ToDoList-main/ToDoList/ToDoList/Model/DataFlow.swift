//
//  DataFlow.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/26.
//

import Foundation

func addCategory(s: AppState, categoryName: String) -> AppState {
    var categorys = s.todoData
    let newCategory = TodoCategory(categoryName: categoryName)
    categorys[newCategory.categoryID] = newCategory
    return AppState(todoData: categorys,
                    addCategoryID: s.addCategoryID)
}

func addToDoItem(s: AppState, content: String, categoryID: String) -> AppState {
    var categorys = s.todoData
    if var newCategory = s.todoData[categoryID] {
        /// make item
        let item = TodoItem(categoryID: categoryID, todoContent: content)
        newCategory.itemMap[item.ID] = item
        /// set item to category
        categorys[newCategory.categoryID] = newCategory
    }
    return AppState(todoData: categorys,
                    addCategoryID: s.addCategoryID)
}

func updateToDoItem(s: AppState,
                    itemID: String,
                    content: String,
                    categoryID: String) -> AppState {
    var categorys = s.todoData
    if var newCategory = s.todoData[categoryID],
       let oldItem = newCategory.itemMap[itemID] {
        /// make item
        let item = TodoItem(ID: oldItem.ID,
                            categoryID: oldItem.categoryID,
                            todoContent: content,
                            done: oldItem.done)
        newCategory.itemMap[item.ID] = item
        /// set item to category
        categorys[newCategory.categoryID] = newCategory
    }
    return AppState(todoData: categorys,
                    addCategoryID: s.addCategoryID)
}

func dropToDoItem(s: AppState,
                  itemID: String,
                  categoryID: String) -> AppState {
    var categorys = s.todoData
    if var newCategory = s.todoData[categoryID] {
        /// make item
        newCategory.itemMap.removeValue(forKey: itemID)
        /// set item to category
        categorys[newCategory.categoryID] = newCategory
    }
    return AppState(todoData: categorys,
                    addCategoryID: s.addCategoryID)
}

func checkToDoItem(s: AppState,
                  itemID: String,
                  categoryID: String) -> AppState {
    var categorys = s.todoData
    if var newCategory = s.todoData[categoryID],
       var oldItem = newCategory.itemMap[itemID] {
        /// make item
        oldItem.done.toggle()
        newCategory.itemMap[oldItem.ID] = oldItem
        /// set item to category
        categorys[newCategory.categoryID] = newCategory
    }
    return AppState(todoData: categorys,
                    addCategoryID: s.addCategoryID)
}

let todoStateReducer: Reducer<AppState> = { old, action in
    if let action = action as? ToDoAction {
        switch action {
        case .addCategory(let categoryName):
            return addCategory(s: old, categoryName: categoryName)
        case .addTodoItem(let categoryID, let itemContent):
            return addToDoItem(s: old, content: itemContent, categoryID: categoryID)
        case .switchCategory(let categoryID):
            return AppState(todoData: old.todoData,
                            addCategoryID: categoryID)
        case .updateTodoItem(let categoryID, let itemID, let content):
            return updateToDoItem(s: old,
                                  itemID: itemID,
                                  content: content,
                                  categoryID: categoryID)
        case .dropTodoItem(let categoryID, let itemID):
            return dropToDoItem(s: old, itemID: itemID, categoryID: categoryID)
        case .checkTodoItem(let categoryID, let itemID):
            return checkToDoItem(s: old, itemID: itemID, categoryID: categoryID)
        case .updateAppState(let state):
            return state
        default:
            break
        }
    }
    return old
}
