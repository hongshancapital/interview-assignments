//
//  ToDoListInputView.swift
//  ToDoList
//
//  Created by berchan on 2021/12/18.
//

import SwiftUI

struct ToDoListInputView: View {
    
    var groups: [String]
    var commitAction: ((String, String) -> Void)?
    
    @State private var groupText: String = ""
    @State private var inputText: String = ""
    @State private var isEditing: Bool = false
    @State private var isPresented: Bool = false
    
    private let height: CGFloat = 40
    private let radius: CGFloat = 8
    private var background: Color {
        Color.white
    }
    
    var body: some View {
        HStack {
            TextField("Add new", text: $inputText) { isEditing in
                self.isEditing = isEditing
            } onCommit: {
                commitAction?(self.inputText, self.groupText.count > 0 ? self.groupText : self.groups.first ?? "")
                self.inputText = ""
            }
            .frame(height: height)
            .font(Font.system(size: 14))
            .background(background)
            .cornerRadius(radius)
            
            if self.isEditing {
                Button {
                    self.isPresented = true
                    if self.groupText.count <= 0 {
                        self.groupText = self.groups.first ?? ""
                    }
                } label: {
                    Label(self.groupText.count > 0 ? self.groupText : self.groups.first ?? "", systemImage: "chevron.down")
                        .padding(.horizontal, 5)
                        .tint(Color.black)
                }
                .frame(height: height)
                .font(Font.system(size: 13))
                .background(background)
                .cornerRadius(radius)
            }
        }
        .foregroundColor(Color.black)
        .padding(.horizontal,  self.isEditing ? 20 : 35)
        .confirmationDialog("请选择分组信息", isPresented: $isPresented, presenting: self.groups) { groups in
            ForEach(groups, id: \.self) {group in
                VStack {
                    Button(group) {
                        self.groupText = group
                    }
                }
            }
            
        }
    }
}

struct ToDoListInputView_Previews: PreviewProvider {
    
    static var previews: some View {
        ToDoListInputView(groups: ["one", "two"])
    }
}
