//
//  ToDoList.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/24.
//

import SwiftUI

struct ToDoList: View {
    
    @StateObject private var todoViewModel = ToDoViewModel()
    @StateObject private var createViewModel = CreateViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
                List(0..<todoViewModel.groupToDos.count, id: \.self) { index in
                    let tagName = todoViewModel
                        .todoTagNameByTodos(todos: todoViewModel.groupToDos[index])
                    let header = Text(tagName)
                        .fontWeight(.semibold)
                    Section(header: header) {
                        VStack {
                            ForEach(todoViewModel.groupToDos[index]) { todoModel in
                                ToDoContentCell(todoModel: todoModel)
                                    .onTapGesture {
                                        endModifyOrCreateTodo()
                                        withAnimation(.easeInOut) {
                                            todoViewModel.checkTodoAction(todo: todoModel)
                                        }
                                    }
                                    .onLongPressGesture {
                                        foucsTodo(todoModel: todoModel)
                                    }
                            }
                        }
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(ColorPattern.mainBackground.color)
                }
                .navigationBarTitle(Text("ToDo List"))
                .listStyle(.plain)
                
                CreateTodoView(viewModel: createViewModel)
                    .frame(height: 50, alignment: .bottom)
                    .padding(EdgeInsets(top: 0, leading: 0, bottom: 5, trailing: 0))
                    .onTapGesture {
                        if createViewModel.todoField == nil {
                            createViewModel.todoField = .create
                        }
                    }
                    .onSubmit {
                        if let todoField = createViewModel.todoField {
                            switch todoField {
                            case .modify:
                                if let todo = createViewModel.modifyTodoModel {
                                    todoViewModel.modifyTodo(originTodo: todo,
                                                             createViewModel.newTodoContent,
                                                             createViewModel.newTodoTagId)
                                } else {
                                    // something wrong. need check. display error msg.
                                }
                            case .create:
                                todoViewModel.createTodo(content: createViewModel.newTodoContent,
                                                         tagId: createViewModel.newTodoTagId)
                            }
                        }
                        // 还原现场
                        endModifyOrCreateTodo()
                    }
            }
            .onAppear(perform: {
                todoViewModel.fetchTodos()
            })
            .background(ColorPattern.mainBackground.color)
        }
    }
    
    func endModifyOrCreateTodo() {
        createViewModel.newTodoContent = ""
        createViewModel.modifyTodoModel = nil
        createViewModel.todoField = nil
        createViewModel.newTodoTagId = 1
    }
    
    func foucsTodo(todoModel: ToDoModel) {
        createViewModel.todoField = .modify
        createViewModel.modifyTodoModel = todoModel
        if let todoContent = todoModel.content {
            createViewModel.newTodoContent = todoContent
        }
        if let tagId = todoModel.tagId {
            createViewModel.newTodoTagId = tagId
        }
    }
}

struct ToDoList_Previews: PreviewProvider {
    static var previews: some View {
        ToDoList()
    }
}
