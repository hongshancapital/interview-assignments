//
//  ToDoListRow.swift
//  ToDoList
//
//  Created by berchan on 2021/12/19.
//

import SwiftUI

struct ToDoListRow: View {
    
    var content: String
    @Binding var isEditing: Bool
    @State var inputText: String
    @State var isCompleted: Bool = false
    var completedAction: ((Bool) -> Void)?
    var contentChangedAction: ((String) -> Void)?
        
    @FocusState private var isFocus: Bool

    var body: some View {
        HStack {
            Image(systemName: self.isCompleted ? "circle.inset.filled" : "circle")
                .padding(.leading, 15)
                .tint(self.isCompleted ? Color.gray : Color.black)
            
            ZStack(alignment: .leading) {
                
                if self.isEditing && !self.isCompleted {
                    TextField("", text: $inputText) { isEditing in
                        self.isEditing = isEditing
                    } onCommit: {
                        self.isFocus = false
                        contentChangedAction?(self.inputText)
                    }
                    .focused($isFocus, equals: true)
                    .foregroundColor(Color.black)
                    .font(Font.system(size: 14))
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1){
                            self.isFocus = true
                        }
                    }
                } else {
                    Text(self.content)
                        .font(Font.system(size: 14))
                        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
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
        .onTapGesture {
            if self.isEditing {
                return
            }
            
            self.isCompleted = !isCompleted
            completedAction?(self.isCompleted)
        }
        .onLongPressGesture(perform: {
            if !self.isCompleted {
                self.isEditing = true
            }
        })
    }
}

struct ToDoListRow_Previews: PreviewProvider {
    static var previews: some View {
        ToDoListRow(content: "todo", isEditing: .constant(false), inputText: "todo")
    }
}
