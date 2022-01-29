//
//  AppState.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/26.
//

import Foundation

struct AppState: Codable, ReduxState {
    let todoData: [String: TodoCategory]
    let addCategoryID: String?
    init(todoData: [String: TodoCategory] = [:],
         addCategoryID: String? = nil) {
        self.todoData = todoData
        self.addCategoryID = addCategoryID
    }
    
    func category(id: String) -> TodoCategory? {
        return todoData[id]
    }
    /// 选中的category
    func categoryToAdd() -> TodoCategory? {
        guard let categoryID = addCategoryID else {
            return nil
        }
        return category(id: categoryID)
    }
    
    func sortedCategorys() -> [TodoCategory] {
        let sortDatas = Array(todoData.values)
        return sortDatas
    }
}

enum ToDoAction: Action {
    case `init`
    case addCategory(categoryName: String)
    case dropCategory(categoryID: String)
    case addTodoItem(categoryID: String,
                     itemContent: String)
    case dropTodoItem(categoryID: String,
                      itemID: String)
    case checkTodoItem(categoryID: String,
                      itemID: String)
    case updateTodoItem(categoryID: String,
                        itemID: String,
                        itemContent: String)
    case changeSearchKey(keyword: String?, originState: AppState)
    case switchCategory(categoryID: String?)
    case updateAppState(state: AppState)
    case loadCacheState
}
