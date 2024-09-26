//
//  ListView.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import SwiftUI

struct ListView: View {
    @State private var showAddNew = false

    @EnvironmentObject var listViewModel: TodoListViewModel
    
    var body: some View {
        VStack {
            List {
                ForEach(listViewModel.groups) {
                     TodoGroupView(todoList: $0)
                }
            }
            .listStyle(.plain)
            .navigationTitle("List")
            .environmentObject(listViewModel)
            .onTapGesture {
                //cancel editing.
                //为什么不存在事件拦截。。。
                showAddNew = false
            }
            
            AddTodoItemBottomView(showAddNew: $showAddNew)
                .id(showAddNew ? "addnew1" : "addnew2")
        }

    }
}

struct AddTodoItemBottomView: View {
    @EnvironmentObject var listViewModel: TodoListViewModel
    
    @Binding var showAddNew: Bool
    @State private var todoContent = ""
    @State private var selectedIndex = 0
    
    var body: some View {
        ZStack {
            if !showAddNew {
                Button(action: {
                    showAddNew = true
                }) {
                    Text("Add new...")
                        .frame(
                            minWidth: 100,
                            maxWidth: .infinity,
                            minHeight: 50,
                            alignment: .leading
                        )
                        .font(Font.subheadline.weight(.bold))
                        .cornerRadius(12)
                        .foregroundColor(.gray.opacity(0.66))
                }
                .frame(height: 50)
                .padding(.horizontal, 20)
                .overlay(
                    RoundedRectangle(cornerRadius: 10)
                        .strokeBorder(
                            style: StrokeStyle(
                                lineWidth: 1,
                                dash: [1]
                            )
                        )
                        .foregroundColor(.gray)
                )
                .transition(AnyTransition.opacity.animation(.easeIn))

            } else {
                HStack (spacing: 20) {
                    TextField("Add new", text: $todoContent)
                        .frame(maxWidth: .infinity, maxHeight: 40)
                        .padding(.horizontal,16)
                        .background(.gray.opacity(0.1))
                        .cornerRadius(10)
                        .onSubmit {
                            guard todoContent.count > 0 else {
                                showAddNew = false
                                return
                            }
                            listViewModel.addTodoItem(content: todoContent, to: listViewModel.groups[selectedIndex])
                            showAddNew = false
                        }
                    
                    Picker("choose group", selection: $selectedIndex) {
                        ForEach(listViewModel.groups.indices, id: \.self) { idx in
                            Text(listViewModel.groups[idx].groupName)
                        }
                    }
                    .font(.system(size: 10))
                    .pickerStyle(.automatic)
                    .frame(width: 100, height: 40)
                    .background(.gray.opacity(0.1))
                    .cornerRadius(10)
                }
                .padding()
                .transition(AnyTransition.opacity.animation(.easeIn))
            }
        }
        .padding(.horizontal, showAddNew ? 0 : 40)

    }
    
}

struct ListView_Previews: PreviewProvider {
    @State static var showAddNew1 = false

    static var previews: some View {
        NavigationView {
            AddTodoItemBottomView(showAddNew: $showAddNew1)
        }
        .environmentObject(TodoListViewModel())
    }
}
