//
//  BottomAddView.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

// 虚线框
struct DottedLineView: View {
    var cornerRadius = 22.0
    var lineColor = Color.secondary
    var body: some View {
        return RoundedRectangle(cornerRadius: cornerRadius)
            .stroke(style: StrokeStyle(lineWidth: 1, dash: [4]))
            .foregroundColor(lineColor)
    }
}

// 添加事项View
struct BottomAddView: View {
    @ObservedObject var mainData: MainData
    @State var addTextFieldEditing: Bool = false
    @State var selectGroupModel: GroupModel? = nil
    @State var offsetY: CGFloat = 22.0

    var body: some View {
        VStack {
            Spacer()
            HStack {
                // 输入框
                AddTextFieldView(mainData: mainData, selectGroupModel: $selectGroupModel, addTextFieldEditing: $addTextFieldEditing, offsetY: $offsetY)
                // 分组菜单
                if addTextFieldEditing {
                    MenuView(mainData: mainData, selectGroupModel: $selectGroupModel, offsetY: $offsetY)
                }
            }
            .frame(width: screenSize.width - 38, height: 50.0)
            .background(Color("BackgroundColor"))
            .cornerRadius(12.0)
            .shadow(color: Color.black.opacity(0.2), radius: 35.0, x: 0, y: 0)
        }
        .ignoresSafeArea(.keyboard, edges: addTextFieldEditing ? .top : .bottom)
        .onAppear {
            if mainData.groupArray.count > 0 {
                selectGroupModel = mainData.groupArray.first
            }
        }
    }
}

// 菜单
private struct MenuView: View {
    @ObservedObject var mainData: MainData
    @Binding var selectGroupModel: GroupModel?
    @Binding var offsetY: CGFloat

    var body: some View {
        Menu {
            ForEach(0 ..< mainData.groupArray.count + 1) { index in
                if index == mainData.groupArray.count {
                    Button {
                        TextFieldAlert(title: "添加分组", placeholder: "请输入分组名称") { value in
                            if value.count > 0 {
                                let addGroupModel = GroupModel(title: value, toDoList: [])
                                mainData.addGroup(groupModel: addGroupModel) { success in
                                    if !success {
                                        ErrorAlert()
                                    } else {
                                        self.selectGroupModel = addGroupModel
                                    }
                                }
                            }
                        }
                    } label: {
                        HStack {
                            Text("添加分组")
                            Image(systemName: "plus.circle")
                        }
                    }
                } else {
                    let groupModel = mainData.groupArray.reversed()[index]
                    Button {
                        self.selectGroupModel = groupModel
                    } label: {
                        Text(groupModel.title)
                            .multilineTextAlignment(.center)
                    }
                }
            }
        } label: {
            HStack(alignment: .center) {
                Text(selectGroupModel != nil ? selectGroupModel!.title : "请添加分组")
                    .multilineTextAlignment(.leading)
                    .foregroundColor(.black)
                    .font(.subheadline)
                    .padding(.leading, 8)
                Image(systemName: "chevron.down")
                    .foregroundColor(Color("CheckedColor"))
                    .padding(.trailing, 8.0)
            }
            .frame(width: 130, height: 44.0)
            .background(Color.white)
            .cornerRadius(22.0)
            .offset(y: offsetY)
        }
    }
}

// 添加事项输入框
private struct AddTextFieldView: View {
    @ObservedObject var mainData: MainData
    @Binding var selectGroupModel: GroupModel?
    @Binding var addTextFieldEditing: Bool
    @Binding var offsetY: CGFloat
    @State var addText: String = ""

    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 12.0)
                .foregroundColor(Color.white)
                .overlay(
                    !addTextFieldEditing ? DottedLineView(cornerRadius: 12.0, lineColor: Color.gray) : nil
                )
            HStack {
                TextField("Add new...", text: $addText) { editing in
                    if editing {
                        addTextFieldEditing = true
                    } else {
                        offsetY = 0
                        withAnimation {
                            addTextFieldEditing = false
                        }
                    }
                } onCommit: {
                    offsetY = 0
                    // 添加事项
                    if selectGroupModel != nil, addText.count > 0 {
                        mainData.addTodo(formGroupModel: selectGroupModel!, todoModel: ToDoModel(title: addText, isCompleted: false))
                        addText = ""
                    }
                    withAnimation {
                        addTextFieldEditing = false
                    }
                }
                .padding(.horizontal)
                .frame(height: 50.0)
                .cornerRadius(12.0)
            }
        }
        .frame(height: 50.0)
    }
}
