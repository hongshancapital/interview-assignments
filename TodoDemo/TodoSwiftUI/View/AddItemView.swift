//
//  AddItemView.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import SwiftUI

struct AddItemView: View {
    @Environment(\.presentationMode) var presentationMode
    @EnvironmentObject var listViewModel: TodoListViewModel
    @State private var content: String = ""
    @State private var selectedGroupIndex: Int = 0
    
    var body: some View {
        ScrollView {
            VStack {
                TextField("todo item content", text: $content)
                    .padding(.horizontal)
                    .frame(height: 55)
                    .background(Color.gray.opacity(0.3))
                    .cornerRadius(10)
                
                Picker("Please choose a color", selection: $selectedGroupIndex) {
                    ForEach(listViewModel.groups.indices, id: \.self) { idx in
                        Text(listViewModel.groups[idx].groupName)
                    }
                }
                .frame(maxWidth: .infinity)
                .frame(height: 55)
                .background(Color.orange)
                .foregroundColor(.white)
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
            
        }
        .navigationTitle("Add an todo Item")
    }
    
    private func saveAction() {
        guard content.count > 0 else {
            return
        }
        let group = listViewModel.groups[selectedGroupIndex]
        listViewModel.addTodoItem(content: content, to: group)
        presentationMode.wrappedValue.dismiss()
    }
}

struct AddItemView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            AddItemView()
        }
    }
}
