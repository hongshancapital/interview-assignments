//
//  ToDoListRow.swift
//  ToDoList
//
//  Created by berchan on 2021/12/19.
//

import SwiftUI

struct ToDoListRow: View {
    
    var content: String
    @State var inputText: String
    @State var isCompleted: Bool = false
    var completedAction: ((Bool) -> Void)?
    var contentChangedAction: ((String) -> Void)?
    
    @State private var isEditing: Bool = false

    var body: some View {
        HStack {
            Button {
                self.isCompleted = !isCompleted
                completedAction?(self.isCompleted)
            } label: {
                Image(systemName: self.isCompleted ? "circle.inset.filled" : "circle")
                    .padding(.leading, 10)
                    .tint(self.isCompleted ? Color.gray : Color.black)
            }
            .frame(width: 40)
            
            ZStack(alignment: .leading) {
                
                if self.isEditing && !self.isCompleted {
                    TextField("", text: $inputText) { isEditing in
                        self.isEditing = isEditing
                    } onCommit: {
                        contentChangedAction?(self.inputText)
                    }
                    .foregroundColor(Color.black)
                    .font(Font.system(size: 14))
                } else {
                    Text(self.content)
                        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
                        .font(Font.system(size: 14))
                        .onLongPressGesture(perform: {
                            self.isEditing = true
                        })
                    if self.isCompleted {
                        VStack {
                            Divider()
                                .padding(.trailing, 20)
                        }
                    }
                }
            }
            .foregroundColor(self.isCompleted ? Color.gray : Color.black)
        }
    }
}

struct ToDoListRow_Previews: PreviewProvider {
    static var previews: some View {
        ToDoListRow(content: "todo", inputText: "todo")
    }
}
