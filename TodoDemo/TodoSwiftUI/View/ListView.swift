//
//  ListView.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import SwiftUI

struct ListView: View {
    
    @EnvironmentObject var listViewModel: TodoListViewModel
    
    var body: some View {
        List {
            ForEach(listViewModel.groups) {
                 TodoGroupView(todoList: $0)
            }
        }
        .listRowBackground(Color.gray)
        .navigationTitle("List")
        .navigationBarItems(
            trailing: NavigationLink("add", destination: AddItemView())
        )
        .environmentObject(listViewModel)
    }
}

struct ListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ListView()
        }
    }
}
