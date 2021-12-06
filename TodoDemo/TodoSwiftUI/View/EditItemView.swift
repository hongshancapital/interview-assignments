//
//  EditItemView.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import SwiftUI

struct EditItemView: View {
    
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var listViewModel: TodoListViewModel
    @State private var content = ""
    let item: TodoItem
    let group: TodoListGroup
    
    var body: some View {
        ScrollView {
            VStack {
                TextField("todo item content", text: $content)
                    .padding(.horizontal)
                    .frame(height: 55)
                    .background(Color.gray.opacity(0.3))
                    .cornerRadius(10)
                
                Button {
                    saveAction()
                } label: {
                    Text("保存")
                        .foregroundColor(.white)
                        .font(.headline)
                        .frame(height: 55)
                        .frame(maxWidth: .infinity)
                        .background(Color.accentColor)
                        .cornerRadius(10)
                }
            }.padding(14)
            
        }.onAppear(perform: {
            content = item.content
        })
        .navigationTitle("Edit an todo Item")
    }
    
    private func saveAction() {
        if content.trimmingCharacters(in: .whitespaces).count == 0 {
            listViewModel.remoteItem(id: item.id, in: group)
        }
        else {
            listViewModel.updateItemContent(item: item, content: content, in: group)
        }
        presentationMode.wrappedValue.dismiss()
    }

}

struct EditItemView_Previews: PreviewProvider {
    static var previews: some View {
        EditItemView(item: TodoItem(content: "abc", compeleted: false), group: TodoListGroup(todos: [], groupName: "test group"))
    }
}
