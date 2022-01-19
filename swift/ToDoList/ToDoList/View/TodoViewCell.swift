//
//  TodoViewCell.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

struct TodoViewCell: View {
    @StateObject var mainData: MainData
    @StateObject var groupModel: GroupModel
    @StateObject var toDoModel: ToDoModel
    @State var isEdit: Bool = false
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 12.0)
                .foregroundColor(Color.white)
                .onTapGesture {
                    // 点击
                    UIImpactFeedbackGenerator(style: .soft).impactOccurred()
                    mainData.selectTodo(formGroupModel: groupModel, selectTodo: toDoModel)
                }
                .onLongPressGesture(perform: {
                    // 长按
                    UIImpactFeedbackGenerator(style: .light).impactOccurred()
                    DispatchQueue.main.async {
                        self.isEdit = true
                    }
                })
                .shadow(color: Color.black.opacity(isEdit ? 0.1 : 0.0), radius: 10, x: 0, y: 1)
                .scaleEffect(x: 1.0, y: isEdit ? 1.1 : 1.0, anchor: .center)
            
            HStack(spacing: 8) {
                Image(systemName: toDoModel.isCompleted ? "largecircle.fill.circle" : "circle")
                    .resizable()
                    .foregroundColor(Color("UncheckedColor"))
                    .frame(width: 22.0, height: 22.0)
                
                ZStack(alignment: .leading) {
                    CustomField(text: $toDoModel.title, isFirstResponder: $isEdit, shouldReturn: { textField in
                        if textField.text?.count == 0 {
                            // 删除事项
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
        .onReceive(NotificationCenter.default.publisher(for: UIApplication.keyboardWillHideNotification)) { _ in
            // 添加键盘通知 当滚动内容scrollview关闭键盘时 退出编辑
            withAnimation {
                DispatchQueue.main.async {
                    self.isEdit = false
                }
            }
        }
        .onChange(of: mainData.searching, perform: { newValue in
            // 当点击搜索后 退出编辑
            if newValue {
                DispatchQueue.main.async {
                    self.isEdit = false
                }
            }
        })
        .frame(width: screenSize.width - 36, height: 44)
    }
}
