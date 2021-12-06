//
//  TodoGroupView.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import SwiftUI

struct TodoGroupView: View {
    
    @EnvironmentObject var listViewModel: TodoListViewModel
    @State private var jumpleAble = false
    @State private var jumpItem: TodoItem = TodoItem(content: "", compeleted: false)

    var todoList: TodoListGroup
    
    var body: some View {
        Text(todoList.groupName)
            .font(.system(size: 14, weight: .bold))
            .foregroundColor(.black)
        Group {
            ForEach(todoList.todos) { item in
                NavigationLink(
                    destination: EditItemView(item: jumpItem, group: todoList),
                    isActive: $jumpleAble,
                    label: {
                        TodoListItemView(todoItem: item)
                            .onTapGesture {
                                withAnimation(.linear) {
                                    listViewModel.updateItemCompletion(item: item, in: todoList)
                                }
                            }
                            .onLongPressGesture {
                                jumpItem = item
                                jumpleAble = true
                            }
                    })
            }
            .padding(8)
        }
        .onAppear {
            jumpleAble = false
        }
    }
}


//struct TodoGroupView_Previews: PreviewProvider {
//    static var previews: some View {
//        TodoGroupView()
//    }
//}
