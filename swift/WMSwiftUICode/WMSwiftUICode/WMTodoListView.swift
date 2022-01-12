//
//  WMTodoListView.swift
//  WMSwiftUICode
//
//  Created by 王明民 on 2022/1/5.
//

import Foundation
import SwiftUI

struct WMTodoListView: View {
    
        
    var body: some View {
        ZStack (alignment: .bottom) {
            listView()
            WMAddButton()
        }
        
    }
    
    
}

struct listView: View {
    
    @EnvironmentObject var todoLists : WMModelData
    
    var body: some View {
        NavigationView {
            List(0..<todoLists.todoKeys.count) { index in
                
                let key = todoLists.todoKeys[index]
                
                let rows = todoLists.todos[key]
                
                Section (
                    header: Text(key)
                        .font(Font.system(size: 15,weight:.bold))
                        .foregroundColor(Color.black)
                        .listRowInsets(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 0)),
                    footer: Rectangle()
                        .fill(.clear)
                        .frame(height: (index ==  todoLists.todoKeys.count-1) ? 60 : 0)
                        .listRowInsets(EdgeInsets())
                        
                ) {
                    ForEach(rows ?? [], id: \.self) { todo in
                        
                        VStack (spacing: 0) {
                            WMTodoRow(todo: todo, message: todo.message)
                                .listRowSeparator(.hidden)
                                .environmentObject(WMStateModel(select: todo.check))
                            
                            Rectangle()
                                .fill(Color.clear)
                                .frame(height: 15)
                            
                        }
                        
                    }
                    
                }
                .padding(.leading, 15)
                .padding(.trailing, 15)
                .listRowInsets(EdgeInsets())
                .listRowBackground(Color.clear)
                .listRowSeparator(.hidden)
                
            }
            .environment(\.defaultMinListHeaderHeight, 1)
            .environment(\.defaultMinListRowHeight, 60)
            .listStyle(GroupedListStyle())
            .navigationTitle("List")
            
        }
    }
}
