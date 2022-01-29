//
//  TodoItemCell.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/28.
//

import Foundation
import SwiftUI


struct ToToItemCell: View {
    @EnvironmentObject var store: DefaultStore<AppState>
    @State private var text: String = ""
    @State private var editmode: Bool = false
    @FocusState private var isFocused: Bool
    @State private var item: TodoItem
    
    
    
    init(initem: TodoItem) {
        self.item = initem
        self._text = State(wrappedValue: initem.todoContent)
    }
    
    func switchEidtMode() {
        editmode.toggle()
        isFocused = editmode
    }
    /// edit or delete a todo item
    func editToDo(content: String) {
        Log.d("start edit \(item) with \(content)")
        if content.isEmpty {
            /// need to delete this todo
            Log.d("start delete \(item)")
            let ac = ToDoAction.dropTodoItem(
                categoryID: item.categoryID,
                itemID: item.ID
            )
            store.dispatch(action: ac)
        } else {
            guard content != item.todoContent else {
                Log.d("content not change, not update")
                return
            }
            let ac = ToDoAction.updateTodoItem(
                categoryID: item.categoryID,
                itemID: item.ID,
                itemContent: content
            )
            store.dispatch(action: ac)
        }
    }
    
    func checkToDo() {
        Log.d("start check \(item) with")
        let ac = ToDoAction.checkTodoItem(
            categoryID: item.categoryID,
            itemID: item.ID
        )
        store.dispatch(action: ac)
    }

    var body: some View {
        HStack(alignment: .center, spacing: 0, content: {
            HStack(alignment: .center, spacing: 5) {
                TodoCheckBox(check: item.done)
                    .onTapGesture {
                        Log.d("todo item check: \(text)")
                        checkToDo()
                }
                Group {
                    ZStack {
                        TextField(item.todoContent, text: $text, onCommit: {
                            Log.d("todo item commit: \(text)")
                            switchEidtMode()
                            editToDo(content: text)
                        })
                            .font(Font.body)
                            .padding([.top, .bottom], 10)
                            .foregroundStyle(.black)
                            .focused($isFocused)
                            .opacity(item.done ? 0 : 1.0)
                        HStack {
                            Text(item.todoContent)
                                .strikethrough()
                                .font(Font.body)
                                .padding([.top, .bottom], 10)
                                .foregroundStyle(.gray)
                            Spacer()
                        }.opacity(item.done ? 1.0 : 0)

                    }
                    Spacer()
                }.overlay {
                    if !editmode {
                        /// long press gesture conflict with textfield
                        /// so put long gseture on overlay view, which will be removed after long pressed
                        Color.white.opacity(0.01)
                            .onLongPressGesture(minimumDuration: 1.0, perform: {
                                switchEidtMode()
                                Log.d("\(item.todoContent) editmode chaneg to \(editmode)")
                            })
                            .allowsHitTesting(true)
                    }
                }
            }.padding([.leading, .trailing], 10)
             .background(.white)
             .cornerRadius(8)
        })
          .padding([.leading, .trailing], 0)
          .listRowBackground(Color.clear)
          .listRowSeparator(.hidden)
    }
}

