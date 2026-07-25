//
//  TodoItem.swift
//  TODOListDemo
//
//  Created by 盼李 on 13.12.21.
//

import SwiftUI

struct TodoDetails: View {
    @ObservedObject var main: Main
    var body: some View {
        VStack {
            Spacer().frame(height: 20)
            HStack {
                Button (action: {
                    UIApplication.shared.keyWindow?.endEditing(true)
                    self.main.detailsShowing = false
                }) {
                    Text("取消").padding()
                }
                Spacer()
                Button(action: {
                    UIApplication.shared.keyWindow?.endEditing(true)
                    if editingMode {
                        self.main.todos[editingIndex].title = self.main.detailsTitle
                     
                    } else {
                        let newTodo = Todo(title: self.main.detailsTitle)
                        self.main.todos.append(newTodo)
                    }
                    self.main.sort()
                    do {
                        let archivedData = try NSKeyedArchiver.archivedData(withRootObject: self.main.todos, requiringSecureCoding: false)
                        UserDefaults.standard.set(archivedData, forKey: "todos")
                    } catch {
                        print("error")
                    }
                    self.main.detailsShowing = false
                }) {
                    Text(editingMode ? "完成" : "添加").padding()
                }.disabled(main.detailsTitle == "")
            }
            SATextField(tag: 0, text: editingTodo.title, placeholder: "What's in your mind? ", changeHandler: {
                (newString) in
                self.main.detailsTitle = newString
            }) {
                
            }
            .padding(8)
            .foregroundColor(.white)
            .padding()
            Spacer()
        }
    .padding()
    .background(Color("todoDetails-bg"))
        .edgesIgnoringSafeArea(.all)
    }
}

#if DEBUG
struct TodoDetails_Previews: PreviewProvider {
    static var previews: some View {
        TodoDetails(main: Main())
    }
}
#endif
