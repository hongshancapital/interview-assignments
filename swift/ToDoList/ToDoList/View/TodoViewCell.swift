//
//  TodoViewCell.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

struct TodoViewCell: View {
    @ObservedObject var mainData : MainData
    @ObservedObject var groupModel: GroupModel
    @ObservedObject var toDoModel : ToDoModel
    @State var isEdit : Bool = false
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 12.0)
                .foregroundColor(Color.white)
                .onTapGesture {
                    UIImpactFeedbackGenerator(style: .soft).impactOccurred()
                    mainData.selectTodo(formGroupModel: groupModel, selectTodo: toDoModel)
                }
                .onLongPressGesture(perform: {
                    UIImpactFeedbackGenerator(style: .light).impactOccurred()
                    self.isEdit = true
                })
                .shadow(color: Color.black.opacity(isEdit ? 0.1 : 0.0), radius: 10, x: 0, y: 1)
                .scaleEffect(x: 1.0, y: isEdit ? 1.1 : 1.0, anchor: .center)
            
            HStack(spacing: 8) {
                Image(systemName: toDoModel.isCompleted ? "largecircle.fill.circle" : "circle")
                    .resizable()
                    .foregroundColor(Color("UncheckedColor"))
                    .frame(width: 22.0, height: 22.0)
                
                ZStack(alignment: .leading) {
                    CustomField(text: $toDoModel.title, isFirstResponder: $isEdit,shouldReturn: { textField in
                        if textField.text?.count == 0 {
                            mainData.removeTodo(formGroupModel: groupModel, removeTodo: toDoModel)
                        }
                    })
                        .opacity(isEdit ? 1.0 : 0.0)
                        .font(.system(size: 16))
                        .textFieldStyle(.automatic)
                        .multilineTextAlignment(.leading)
                        .foregroundColor(toDoModel.isCompleted ? Color("UncheckedColor") : Color("CheckedColor"))
                    
                    
                    Text(toDoModel.title)
                        .strikethrough(toDoModel.isCompleted)
                        .foregroundColor(toDoModel.isCompleted ? Color("UncheckedColor") : Color("CheckedColor"))
                        .font(.system(size: 16))
                        .opacity(isEdit ? 0.0 : 1.0)
                    
                }
                Spacer()
            }
            .padding(.horizontal, 12.0)
            .frame(height: 44.0)
        }
        .frame(width: screenSize.width - 36,height : 44)
    }
    
}
