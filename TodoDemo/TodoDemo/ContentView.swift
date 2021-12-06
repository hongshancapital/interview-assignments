//
//  ContentView.swift
//  TodoDemo
//
//  Created by 钟逊超 on 12/5/21.
//

import SwiftUI

struct CheckBox: View {
    @State var checked = false
    private let onCheckedChanged: (Bool) -> Void
    
    public init(checked: Bool, onCheckedChanged: @escaping (Bool) -> Void = { _ in }) {
        self.checked = checked
        self.onCheckedChanged = onCheckedChanged
    }
    
    var body: some View {
        Button(action: {
            withAnimation {
                self.checked = !self.checked
                self.onCheckedChanged(self.checked)
            }
        }) {
            Image(checked ? "checkbox_select" : "checkbox_unselect")
                .resizable()
                .frame(width: 20, height: 20)
        }
    }
}

struct TodoSubtaskItemModel {
    var isChecked: Bool = false
    @State var subtaskDes: String = ""
}

/// TODO事件的子任务
struct TodoSubtaskItem: View {
    
    @State var model: TodoSubtaskItemModel = TodoSubtaskItemModel()

    var body: some View {
        HStack {
            Spacer().frame(width: 30, height: 30)
                .background(Color(hex: 0xf7f7f7))
            
            HStack {
                Spacer().frame(width: 10, height: 30)
                CheckBox(checked: model.isChecked) { check in
                    model.isChecked = check
                }
                Text(model.subtaskDes).strikethrough(model.isChecked)
            }
            .frame(maxWidth: .infinity, minHeight: 50, alignment: .leading)
            .background(Color.white)
            .cornerRadius(5)

            Spacer().frame(width: 30, height: 30)
                .background(Color(hex: 0xf7f7f7))
        }
    }
}

struct TodoTaskItemModel {
    var title: String = ""
    var subtasks: [TodoSubtaskItemModel] = []
}
/// TODO事件，可拆分为多个子任务。
struct TodoTaskItem: View {
    
    @Binding var model: TodoTaskItemModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Spacer().frame(width: 30, height: 20)
            Text(model.title)
                .padding(.leading, 30)
            
            ForEach(0 ..< model.subtasks.count, id:\.self) { index in
                TodoSubtaskItem(model: model.subtasks[index])
            }
        }
    }
}

struct DefaultTodoList {
    static let list = [
        TodoTaskItemModel(title: "SwiftUI Essentials",
                          subtasks: [
                            TodoSubtaskItemModel(isChecked: false, subtaskDes: "Building Lists and Navigation"),
                            TodoSubtaskItemModel(isChecked: false, subtaskDes: "Creating and Combining Views"),
                            TodoSubtaskItemModel(isChecked: false, subtaskDes: "Handling User Input")
                          ]),
        TodoTaskItemModel(title: "Drawing and Animation",
                          subtasks: [
                            TodoSubtaskItemModel(isChecked: false, subtaskDes: "Animating Views and Transitions")
                          ]),
        TodoTaskItemModel(title: "Framework Integration",
                          subtasks: [
                            TodoSubtaskItemModel(isChecked: false, subtaskDes: "Interfacing with UIKit")
                          ])
    ]
}


class TodoListViewModel: ObservableObject {
    @Published var items: [TodoTaskItemModel] = DefaultTodoList.list
}

struct ContentView: View {
    
    @State var text = ""
    
    @ObservedObject var viewModel: TodoListViewModel = TodoListViewModel()
    
    @State var isShowingInput: Bool = false
    
    @State var editItemIndex: Int = 0
    
    var body: some View {
        VStack {
            ScrollView {
                Text("List")
                    .font(.title)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.leading, 30)
                
                ForEach(0 ..< viewModel.items.count, id:\.self) { index in
                    TodoTaskItem(model: $viewModel.items[index])
                }
            }.background(Color(hex: 0xf7f7f7))
            
            if isShowingInput {
                InputTextField(placeholder: "Add new...",
                               text: $text,
                               onEditingChanged: { change in
                    print("text:\(self.text)")
                },
                               onCommit: {
                    self.isShowingInput = false
                    
                    let newItem = TodoSubtaskItemModel(isChecked: false, subtaskDes: self.text)
                    var editItem = viewModel.items[editItemIndex]
                    editItem.subtasks.append(newItem)
                    viewModel.items.remove(at: editItemIndex)
                    viewModel.items.insert(editItem, at: editItemIndex)
                },
                               menus: viewModel.items.map { $0.title }) { index in
                    editItemIndex = index
                }
            } else {
                Button(action: {
                    self.isShowingInput = true
                }) {
                    Text("Add new...")
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: TodoListViewModel())
    }
}
