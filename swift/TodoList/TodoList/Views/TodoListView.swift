//
//  TodoListView.swift
//  TodoList
//
//  Created by 边边 on 2021/12/11.
//

import SwiftUI

struct TodoListView: View {
    @State private var text = ""
    @State private var completed = false
    
    var body: some View {
        let sections = todoSectionTitles.map({$0.key}).sorted()
        NavigationView {
            VStack {
                List {
                    ForEach(sections.indices, id:\.self) { i in
                        Section(header: Text(sections[i])) {
                            ForEach(todoSectionTitles[sections[i]]!.indices, id:\.self) { j in
                                TodoListRow(item: todoSectionTitles[sections[i]]![j]).onTapGesture {
                                    var values = todoSectionTitles[sections[i]]!
                                    values[j] = TodoItem(id: 0, itemText: todoSectionTitles[sections[i]]![j].itemText, completed: !todoSectionTitles[sections[i]]![j].completed)
                                    todoSectionTitles.updateValue(values, forKey: sections[i])
                                    self.completed.toggle()
                                }
                            }
                        }
                    }
                }
                .listStyle(GroupedListStyle())
                
                HStack {
                    Spacer()
                    TextField("  Add new...", text: $text, onCommit: {
                        print(text)
                        self.addItem(with: text)
                        self.text = ""
                    }).foregroundColor(.gray)
                        .frame(width: UIScreen.main.bounds.width - 60, height: 45, alignment: .leading)
                        .background(Color.white)
                        .cornerRadius(5)
                        .overlay(
                            RoundedRectangle(cornerRadius: 8)
                                .strokeBorder(
                                    style: StrokeStyle(
                                        lineWidth: 1,
                                        dash: [3]
                                    )
                                )
                                .foregroundColor(.gray)
                        )
                    Spacer()
                }
                .padding()
            }
            .background(Color.clear)
            .navigationBarTitle(Text("TodoList"))
        }
    }
    
    func addItem(with text:String) {
        var value = todoSectionTitles["SwiftUI Essential"]
        let addItem = TodoItem(id: 0, itemText: text, completed: false)
        value!.append(addItem)
        todoSectionTitles.updateValue(value!, forKey: "SwiftUI Essential")
    }
    
}

struct TodoListView_Previews: PreviewProvider {
    static var previews: some View {
        TodoListView()
    }
}
