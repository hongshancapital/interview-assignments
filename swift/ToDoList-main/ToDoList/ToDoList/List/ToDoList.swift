//
//  ToDoList.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/28.
//

import Foundation
import SwiftUI

struct ToDoList: View {
    @EnvironmentObject var store: DefaultStore<AppState>
    @State private var searchText = ""
    
    var filteredCategorys: [TodoCategory] {
        let categorys = store.getCurrent().sortedCategorys()
         if searchText.isEmpty {
             return categorys
         } else {
             var filterCategorys: [TodoCategory] = []
             for category in categorys {
                 if let _searchCategory = category.filter(searchText: searchText) {
                     filterCategorys.append(_searchCategory)
                 }
             }
             return filterCategorys
         }
    }

    func categoryHeader(name: String) -> some View {
        Text(name)
            .foregroundColor(.black)
            .font(Font.caption)
            .fontWeight(.bold)
            .lineLimit(1)
    }
    
    var body: some View {
        List() {
            ForEach(filteredCategorys, id: \.categoryID) { category in
                Section(header: categoryHeader(name: category.categoryName)) {
                    if category.isEmpty {
                        Text("Empty Todo, click bottom to add")
                    } else {
                        ForEach(category.displayTodos(), id: \.listID) { item in
                            ToToItemCell(initem: item)
                        }
                    }
                }
            }
        }
        .listStyle(.plain)
        .background(Color("listbg"))
        .listRowSeparator(Visibility.hidden)
        .searchable(text: $searchText,
                    placement: .navigationBarDrawer(displayMode: .always),
                    prompt: "Todo Search")
    }
}
