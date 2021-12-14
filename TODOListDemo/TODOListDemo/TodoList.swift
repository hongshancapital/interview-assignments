//
//  TodoItem.swift
//  TODOListDemo
//
//  Created by 盼李 on 13.12.21.
//

import SwiftUI

var exampleTodos: [Todo] = [
    Todo(title: "Buy a book about Oracle"),
    Todo(title: "Leard SwiftUI"),
    Todo(title: "Go walking"),
    Todo(title: "Fix bugs"),
    Todo(title: "Try to make more bugs")
]

struct TodoList: View {
    @ObservedObject var main: Main
    var body: some View {
        NavigationView {
            ScrollView {
                ForEach(main.todos) { todo in
                    VStack {
                        if todo.i == 0{
                            HStack {
                                Spacer()
                                    .frame(width: 30)
                                Text("SwiftUI Essentlals")
                                Spacer()
                            }
                        }
                        HStack {
                            Spacer().frame(width: 20)
                            TodoItem(main: self.main, todoIndex: .constant(todo.i))
                                .cornerRadius(10.0)
                                .clipped()
                                .shadow(color: Color("todoItemShadow"), radius: 5)
                            Spacer().frame(width: 25)
                        }
                        Spacer().frame(height: 20)
                    }
                }
                Spacer()
                    .frame(height: 150)
            }
            .edgesIgnoringSafeArea(.bottom)
            .navigationBarTitle(
                Text("List").foregroundColor(Color("theme"))
            )
            .onAppear {
                if let data = UserDefaults.standard.object(forKey: "todos") as? Data {
                    let todolist = NSKeyedUnarchiver.unarchiveObject(with: data) as? [Todo] ?? []
                    for todo in todolist {
                        if !todo.checked {
                            self.main.todos.append(todo)
                        }
                    }
                    self.main.sort()
                } else {
                    self.main.todos = exampleTodos
                    self.main.sort()
                }
            }
        }
    }
}
#if DEBUG
struct TodoList_Previews: PreviewProvider {
    static var previews: some View {
        TodoList(main: Main())
    }
}
#endif
