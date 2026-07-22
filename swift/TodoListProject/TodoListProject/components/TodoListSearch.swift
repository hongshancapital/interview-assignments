//
//  TodoListSearch.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/17.
//

import SwiftUI

struct TodoListSearch: View {

    @ObservedObject var todoListViewModel : TodoListViewModel
    @State private var content = ""
    @State private var searchTodoList: [TodoListGroup] = []
    
    var body: some View {
            VStack {
                Spacer()
                    .frame(height: 20)
                
                HStack {
                    Spacer()
                        .frame(width: 20)
                    
                    TextField("input todo title", text: $content)
                        .frame(height: 50)
                        .padding([.leading,.trailing],10)
                        .background(Color.white)
                        .cornerRadius(10)
                        .onSubmit {
                            if (content.count > 0) {
                                searchTodoList = todoListViewModel.searchTodoList(searchContent: content)
                            }
                        }
                    
                    Spacer()
                        .frame(width: 20)
                }
                
                ZStack {
                    List {
                        ForEach(searchTodoList, id:\.self) { groupItem in
                            Section(header: Text(groupItem.title)
                                        .textCase(nil)
                                        .foregroundColor(Color.black)
                            ) {
                                ForEach(groupItem.todoListItems, id:\.self) { listItem in
                                    TodoListCellView(listItem: listItem)
                                        .listRowBackground(Color.clear)
                                }
                            }
                        }
                        TodoListBlackCellView()
                            .frame(height: 50)
                            .listRowBackground(Color.clear)
                    }
                    .listStyle(.plain)
                    .environmentObject(todoListViewModel)
                    
                    if content.count == 0 && searchTodoList.count == 0 {
                        
                        Image(systemName: "tray.full")
                            .font(Font.system(size: 40))
                            .foregroundColor(Color.gray)
                    }
                }
            }
            .navigationTitle("search")
            .background(Color.gray.opacity(0.1))
    }
}
