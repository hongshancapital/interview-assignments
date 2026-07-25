//
//  TodoItem.swift
//  TODOListDemo
//
//  Created by 盼李 on 13.12.21.
//

import SwiftUI

class Todo: NSObject, Identifiable, NSCoding {
    var title: String = ""
    var checked: Bool = false
    var i: Int = 0
    
    init(title: String) {
        self.title = title
      
    }
    
    required init(coder aDecoder: NSCoder) {
        self.title = aDecoder.decodeObject(forKey: "title") as? String ?? ""
        self.checked = aDecoder.decodeBool(forKey: "checked")
    }
    
    func encode(with coder: NSCoder) {
        coder.encode(self.title, forKey: "title")
        coder.encode(self.checked, forKey: "checked")
    }
}

var emptyTodo: Todo = Todo(title: "")
struct TodoItem: View {
    @ObservedObject var main: Main
    @Binding var todoIndex: Int
    @State var checked: Bool = false
    var body: some View {
        HStack {
            Button(action: {
                self.main.todos[self.todoIndex].checked.toggle()
                self.checked = self.main.todos[self.todoIndex].checked
                do {
                    let archivedData = try NSKeyedArchiver.archivedData(withRootObject: self.main.todos, requiringSecureCoding: false)
                    UserDefaults.standard.set(archivedData, forKey: "todos")
                } catch {
                    print("error")
                }
            }) {
                HStack {
                    Spacer()
                        .frame(width: 12)
                    VStack {
                        Spacer()
                        Image(systemName: self.checked ? "checkmark.square.fill" : "square")
                            .resizable()
                            .frame(width: 24, height: 24)
                            .foregroundColor(.gray)
                        Spacer()
                    }
                    Spacer()
                        .frame(width: 14)
                }
            }
            Button(action: {
                editingMode = true
                editingTodo = self.main.todos[self.todoIndex]
                editingIndex = self.todoIndex
                self.main.detailsTitle = editingTodo.title
            
                self.main.detailsShowing = true
                detailsShouldUpdateTitle = true
            }) {
                HStack {
                  
                    Spacer().frame(width: 10)
                    VStack {
                      
                        HStack {
                            Text(main.todos[todoIndex].title)
                                .font(.headline)
                            Spacer()
                        }
                        .foregroundColor(Color("todoItemTitle"))
                        Spacer().frame(height: 4)
                        .foregroundColor(Color("todoItemSubTitle"))
                        Spacer()
                            .frame(height: 12)
                    }
                }
            }
        .onAppear {
                self.checked = self.main.todos[self.todoIndex].checked
            }
        }.background(Color(checked ? "todoItem-bg-checked" : "todoItem-bg"))
            .animation(.spring())
    }
}

#if DEBUG
struct TodoItem_Previews: PreviewProvider {
    static var previews: some View {
        TodoItem(main: Main(), todoIndex: .constant(0))
    }
}
#endif
