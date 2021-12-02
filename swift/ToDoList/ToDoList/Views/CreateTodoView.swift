//
//  CreateTodoView.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/26.
//

import SwiftUI

struct CreateTodoView: View {
    
    @ObservedObject var viewModel: CreateViewModel
    @FocusState private var focusedField: TodoField?
    /// 简化处理，这里应该是有一个默认 tag 的逻辑，demo 来说就默认第一个吧
    @State private var tagName: String = "SwiftUI Essentials"
    @State private var showingOptions = false
    
    private let height = 40.0
    
    var body: some View {
        HStack {
            Spacer(minLength: 10)
            HStack {
                let prompt = Text("Add New ...")
                    .font(Font.system(size: 8, weight: .medium,
                                      design: .monospaced))
                TextField("", text: $viewModel.newTodoContent,
                          prompt: prompt)
                    .focused($focusedField, equals: .modify)
                    .focused($focusedField, equals: .create)
                    .frame(minHeight: height, alignment: .center)
                    .background(ColorPattern.todoCard.color)
                    .padding(EdgeInsets(top: 0, leading: 10,
                                        bottom: 0, trailing: 10))
                    
            }
            .background(ColorPattern.todoCard.color)
            .cornerRadius(10.0)
            
            
            if let _ = viewModel.todoField {
                HStack {
                    Text(tagName)
                        .lineLimit(2)
                        .truncationMode(.tail)
                        .font(Font.system(size: 5, weight: .medium,
                                          design: .monospaced))
                        .padding(EdgeInsets(top: 5, leading: 5,
                                            bottom: 5, trailing: 0))
                    Image("arrow.down")
                        .resizable()
                        .foregroundColor(Color.gray)
                        .frame(width: 10, height: 10, alignment: .center)
                }
                .background(ColorPattern.todoCard.color)
                .cornerRadius(25.0)
                .frame(width: 60, height: height, alignment: .center)
                .confirmationDialog("Modify Todo Tag",
                                    isPresented: $showingOptions,
                                    titleVisibility: .visible)
                {
                    let tagNames = justTestTagNames()
                    ForEach(0..<tagNames.count) { nameIndex in
                        Button(tagNames[nameIndex]) {
                            viewModel.newTodoTagId = Int64(nameIndex + 1)
                            tagName = tagNames[nameIndex]
                        }
                    }
                }
                .onTapGesture {
                    showingOptions = true
                }
            }
            
            Spacer(minLength: 10)
        }
        .animation(.easeInOut, value: viewModel.todoField)
        .background(ColorPattern.mainBackgroundAplha.color)
        .onChange(of: viewModel.todoField) { todoField in
            focusedField = todoField
        }
        .onChange(of: viewModel.modifyTodoModel) { todo in
            // 这里应该有一个内存中的缓存数据管理的工具，或者直接数据库查询等，demo 简单起见
            if let tagId = todo?.tagId {
                let tagDatas = createTestTagDatas()
                if let tn = tagDatas[tagId]?.name {
                    tagName = tn
                }
            }
        }
    }
}

struct CreateTodoView_Previews: PreviewProvider {
    static var previews: some View {
        CreateTodoView(viewModel: CreateViewModel())
    }
}
