//
//  AddNewGroupView.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/14.
//

import SwiftUI

struct AddNewGroupView: View {
    @Environment(\.presentationMode) var presentationMode
    @StateObject var viewModel: ListViewViewModel
    @State private var addGroupContent: String = ""
    
    
    var body: some View {
        VStack {
            TextField("Add new group...", text: $addGroupContent, onCommit: onCommit)
                .frame(height: 30)
                .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
                .background(Color.gray).cornerRadius(6)
            Button("Save", action: onCommit)
        }
        .padding(EdgeInsets(top: 20, leading: 20, bottom: 10, trailing: 20))
    }
    
    func onCommit() {
        if addGroupContent.isEmpty {
            return
        }
        viewModel.addGroup(addGroupContent)
        addGroupContent = ""
        self.presentationMode.wrappedValue.dismiss()
    }
}
