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
        Section(header: Text(todoList.groupName)
                    .textCase(nil)
                    .foregroundColor(.black)
                    .font(.system(size: 14, weight: .bold))
        ) {
            ForEach(todoList.todos) { item in
                    NavigationLink(
                        destination: EditItemView(group: todoList),
                        isActive: $jumpleAble) {
                            VStack (spacing: 1) {
                                TodoListItemView(todoItem: item)
                            }
                    }
                    .padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: -20))
                    .frame(height: 50)
                    .background(.gray.opacity(0.1))
                    .cornerRadius(10)
                    .listRowSeparator(.hidden)
                    .onTapGesture {
                        withAnimation(.linear) {
                            listViewModel.updateItemCompletion(item: item, in: todoList)
                        }
                    }
                    .onLongPressGesture {
                        listViewModel.editing(item: item)
                        jumpleAble = true
                    }
            }
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
