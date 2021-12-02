//
//  ToDoViewModel.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/26.
//

import Foundation
import Combine
import SwiftUI

class ToDoViewModel: ObservableObject {
    
    // 简单起见创建一个实体
    @Published var groupToDos: [[ToDoModel]]
    
    let tagDatas = createTestTagDatas()
    
    init() {
        self.groupToDos = []
    }
    
    func fetchTodos() {
        groupToDos = createTestToDoDatas()
        // 这里假定数据都合法，正常来说 网络解析 codable 的时候，或者 db 拉取的时候需要验证 data。
    }
    
    func tagNameByDataIndex(index: Int64) -> String {
        return tagDatas[index]?.name ?? ""
    }
    
    func todoTagNameByTodos(todos: [ToDoModel]) -> String {
        guard let todo = todos.first,
              let tagId = todo.tagId,
              let tagName = tagDatas[tagId]?.name else  {
                  // error handing
                  return ""
        }
        return tagName
    }
    
    /**
     - returns: 第一个参数代表第几个 group，第二个代表当前 group中第几个 todo
     */
    private func findTodoIndex(todo: ToDoModel) -> (Int, Int)? {
        guard let tagId = todo.tagId,
              let groupIndex = findTodoGroup(tagId: tagId),
              let todoIndex = groupToDos[groupIndex]
                .firstIndex(where: { $0.content == todo.content && todo.content != nil })
        else {
            return nil
        }
        
        return (groupIndex, todoIndex)
    }
    
    /// 当前 todo tag 所在的 group index
    private func findTodoGroup(tagId: Int64) -> Int? {
        return groupToDos
            .firstIndex(where: { $0.first?.tagId == tagId })
    }
    
    private func firstCheckedTodoIndex(groupIndex: Int,
                                       todo: ToDoModel) -> Int? {
        if todo.checked {
            if let index = groupToDos[groupIndex]
                .firstIndex(where: { $0.checked }) {
                return index
            } else {
                return nil
            }
        } else {
            let uncheckedTodo = groupToDos[groupIndex]
                .filter({ $0.checked == false })
            if uncheckedTodo.count <= 0 {
                return 0
            } else {
                // 10 30
                if let index = uncheckedTodo
                    .firstIndex(where: { $0.sortIndex > todo.sortIndex }) {
                    return index
                } else {
                    return uncheckedTodo.count
                }
            }
            
        }
    }
    
    /// 勾选或者反勾选 todo
    func checkTodoAction(todo: ToDoModel) {
        guard let (groupIndex, todoIndex) = findTodoIndex(todo: todo)
        else { return }
        let checkedTodo = groupToDos[groupIndex]
            .remove(at: todoIndex)
        checkedTodo.checked = !checkedTodo.checked
        let firstCheckTodoIndex = firstCheckedTodoIndex(groupIndex: groupIndex,
                                                        todo: checkedTodo)
        if let firstCheckTodoIndex = firstCheckTodoIndex {
            // 找到了直接移到对应的位置吧
            groupToDos[groupIndex]
                .insert(checkedTodo, at: firstCheckTodoIndex)
        } else {
            // 没找到直接移到最后吧
            groupToDos[groupIndex].append(checkedTodo)
        }
    }
    
    /// 简单起见，不指定 tag 就默认第一个
    func createTodo(content: String, tagId: Int64 = 1) {
        var sortIndex = 10
        let todo = ToDoModel(content: content, checked: false,
                             tagId: tagId, sortIndex: sortIndex)
        if let groupIndex = findTodoGroup(tagId: tagId) {
            // 找到第一个元素并取到 sort index
            if let firstTodo = groupToDos[groupIndex].first {
                // 每个 sort 间隔 10
                sortIndex = firstTodo.sortIndex - 10
                todo.sortIndex = sortIndex
            }
            groupToDos[groupIndex].insert(todo, at: 0)
        } else {
            groupToDos.append([todo])
        }
    }
    
    func modifyTodo(originTodo: ToDoModel,
                    _ modifyContent: String, _ modifyTagId: Int64 = 1) {
        if let (groupIndex, todoIndex) = findTodoIndex(todo: originTodo) {
            groupToDos[groupIndex].remove(at: todoIndex)
            // 也是为了简单，当组内全删的时候直接清除出组数组，业务逻辑。
            if groupToDos[groupIndex].isEmpty {
                groupToDos.remove(at: groupIndex)
            }
            if !modifyContent.isEmpty {
                originTodo.content = modifyContent
                originTodo.tagId = modifyTagId
                if let insertGroupIndex = findTodoGroup(tagId: modifyTagId) {
                    if originTodo.checked {
                        if let checkIndex =
                            firstCheckedTodoIndex(groupIndex: insertGroupIndex, todo: originTodo) {
                            groupToDos[insertGroupIndex].insert(originTodo, at: checkIndex)
                        } else {
                            groupToDos[insertGroupIndex].append(originTodo)
                        }
                    } else {
                        groupToDos[insertGroupIndex].insert(originTodo, at: 0)
                    }
                } else {
                    groupToDos.append([originTodo])
                }
            }
        } else {
            // delete failed. something wrong. need check msg.
            print("delete failed")
        }
        
    }
}
