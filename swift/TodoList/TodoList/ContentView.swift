//
//  ContentView.swift
//  TodoList
//
//  Created by 边边 on 2021/12/11.
//

import SwiftUI

let todoItem1 = TodoItem(id: 0, itemText: "todoListItemtest1", completed: false)
let todoItem2 = TodoItem(id: 0, itemText: "todoListItemtest2", completed: true)
var todoItems: [TodoItem] = [todoItem1, todoItem2]
var todoSectionTitles: Dictionary<String, [TodoItem]> = ["SwiftUI Essential":todoItems,
                                                         "Drawing and Animation":todoItems,
                                                         "App Design and Layout":todoItems]

struct ContentView: View {
    var body: some View {
        TodoListView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
