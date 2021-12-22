//
//  ContentView.swift
//  ToDoList
//
//  Created by berchan on 2021/12/18.
//

import SwiftUI
import CoreData

struct ContentView: View {
    @EnvironmentObject private var model: TodoListViewModel
    
    @State private var isPresented: Bool = false
    @State private var inputText: String?
    
    var listView: some View {
        List {
            ForEach(0..<model.groups.count, id: \.self) { section in
                let group = model.groups[section]
                Section(group.name) {
                    ForEach(group.items) { item in
                        ToDoListRow(content: item.content ?? "", inputText: item.content ?? "", isCompleted: item.isFinished, completedAction: { isCompleted in
                            item.isFinished = isCompleted
                            self.updateItem(item: item)
                        }) { content in
                            if content.count > 0 {
                                self.updateItem(item: item)
                            } else {
                                self.deleteItem(item: item)
                            }
                        }
                    }
                    .onDelete { indexSet in
                        if let index = indexSet.first, index < group.items.count {
                            self.deleteItem(item: group.items[index])
                        }
                    }
                    .background(Color.white)
                    .frame(height:40)
                    .cornerRadius(8.0)
                    .padding(.bottom, 10)
                    .listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets())
                    .listRowBackground(Color.clear)
                }
            }
        }
    }
    
    var inputView: some View {
        VStack {
            Spacer()
            if model.groupNames.count > 0 {
                ToDoListInputView(groups: model.groupNames, commitAction: { inputText, groupName in
                    if inputText.count <= 0 {
                        print("inputText can't be empty")
                        return
                    }
                    self.addItem(input: inputText, groupName: groupName)
                })
            }
        }
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                listView
                inputView
            }
            .toolbar {
                ToolbarItem {
                    Button(action: {
                        if self.isPresented {
                            return
                        }
                        
                        self.isPresented = true
                    }, label: {
                        Label("Add Group", systemImage: "plus")
                            .tint(Color.black)
                    })
                    
                }
            }
            .textFieldAlert(isPresented: $isPresented, content: { () -> TextFieldAlert in
                TextFieldAlert(title: "请输入分组名称", text: self.$inputText, isPresented: self.$isPresented) {
                    self.addGroup(name: self.inputText ?? "")
                } cancelAction: {
                    
                }
            })
            .background(Color.init(red: 0.97, green: 0.97, blue: 0.97))
            .listStyle(.insetGrouped)
            .navigationTitle("List")
            .onAppear {
                model.fetchData()
            }
        }
    }

    // MARK: private
    private func addGroup(name: String) {
        if name.count <= 0 {
            return
        }
        
        do {
            try model.addGroup(name: name)
        } catch  {
            let nsError = error as NSError
            print("Unresolved error \(nsError), \(nsError.userInfo)")
        }
    }
    
    private func addItem(input: String, groupName: String) {
        withAnimation {
            do {
                try model.addItem(input, groupName: groupName)
            } catch {
                let nsError = error as NSError
                print("Unresolved error \(nsError), \(nsError.userInfo)")
            }
        }
    }

    private func deleteItem(item: ToDoItem) {
        withAnimation {
            do {
                try model.deleteItem(item: item)
            } catch {
                let nsError = error as NSError
                print("Unresolved error \(nsError), \(nsError.userInfo)")
            }
        }
    }
    
    private func updateItem(item: ToDoItem) {
        withAnimation {
            do {
                try model.updateItem(item: item)
            } catch {
                let nsError = error as NSError
                print("Unresolved error \(nsError), \(nsError.userInfo)")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
    }
}
