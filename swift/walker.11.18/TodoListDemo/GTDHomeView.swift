//
//  ContentView.swift
//  TodoListDemo
//
//  Created by walker on 2021/11/17.
//

import SwiftUI

struct GTDHomeView: View {
    @ObservedObject var viewModel: GTDViewModel  // only one vm, give a simple name
    
    // constants
    let backgroundColor = Color(red: 227.0/255.0, green: 227.0/255.0, blue: 228.0/255.0)
    let fontSize = 12.0
    let fontColor = Color(red: 62.0/255.0, green: 68.0/255.0, blue: 63.0/255.0)
    let deactiveColor = Color(red: 186.0/255.0, green: 186.0/255.0, blue: 186.0/255.0)
    
    // states
    @State private var isAdding = false             // 添加任务
    @State private var groupName = ""               // 添加任务-组名
    @State private var groupName_input = ""         // 添加任务-组名-文本框
    @State private var showGroupChooser = false     // 添加任务-组名-显示弹窗
    @State private var editingId = ""               // 编辑任务-ID
    @State private var editingText = ""             // 编辑任务-内容
    @State private var offsetY: CGFloat = 0.0       // 底部菜单偏移(监测键盘弹出)
    
    // views
    var body: some View {
        
        ZStack(alignment: .bottom) {
            backgroundColor
            ScrollView(.vertical, showsIndicators: false) {
                VStack(alignment: .leading) {
                    Spacer(minLength: UIApplication.shared.windows.filter{$0.isKeyWindow}.first?.safeAreaInsets.top ?? 0.0)  // fix height for ignoring safe area (为了铺满屏幕的底色)
                    Text("List")
                        .bold()
                        .font(.system(.largeTitle))
                        .padding(.bottom, 10)
                    bodyView
                    Spacer(minLength: 120.0)
                }
                .padding()
                .foregroundColor(fontColor)
            }
            if(isAdding) {
                Color.black.opacity(0.0001) // fix Color.clear bug
                    .onTapGesture {
                        print("taptap")
                        // 响应关闭键盘事件的透明层
                        UIApplication.shared.endEditing()
                        isAdding = false
                        editingText = ""
                    }
            }
            addTaskView
                .offset(y:offsetY)
        }
        .ignoresSafeArea()
        .adaptsToKeyboard(offset: $offsetY)
    }
    
    var bodyView: some View {
        VStack(alignment: .leading) {
            // extract group from tasks
            ForEach(viewModel.tasks.map(\.group).unique(), id: \.self) { title in
                Text(title)
                    .font(.system(size: 12.0))
                    .fontWeight(.heavy)
                    .padding(.vertical, 10)
                VStack(alignment: .leading) {
                    // get task by group
                    // 用过滤+去重提取组名
                    // 用checked来排序
                    ForEach(viewModel.tasks.filter{$0.group == title}.sorted{!$0.checked && $1.checked}){ task in
                        HStack {
                            Image(systemName: task.checked ? "record.circle" : "circle")
                                .foregroundColor(task.checked ? deactiveColor : fontColor)
                                .onTapGesture {
                                    // 点击单选框切换完成状态
                                    withAnimation {
                                        viewModel.toggleTaskStatus(for: task.id)
                                    }
                                }
                            // 编辑ID与当前ID一致时，显示编辑界面
                            if task.id == editingId {
                                TextField("", text: $editingText, onCommit: {
                                    // 内容删完理解为删除当前记录，放到viewModel里处理
                                    // UI层只传数据
                                    withAnimation {
                                        viewModel.updateTaskContent(for: task.id, editingText)
                                    }
                                    editingId = ""
                                    editingText = ""
                                })
//                                    .keyboardType(UIKit.UIKeyboardType.webSearch)
                                    .font(.system(size: fontSize))
                            } else {
                                Text(task.content)
                                    .strikethrough(task.checked)
                                    .foregroundColor(task.checked ? deactiveColor : fontColor)
                                    .font(.system(size: fontSize))
                                    .fontWeight(.heavy)
                                    .onLongPressGesture(minimumDuration: 0.5) { pressing in
                                        //
                                    } perform: {
                                        //
                                        editingId = task.id
                                        editingText = task.content
                                    }
                            }
                            Spacer()
                        }
                        .frame(maxWidth: .infinity) // full length of a cell
                        .padding(EdgeInsets(top: 10, leading: 15, bottom: 10, trailing: 20))
                        .background(Color.white)
                        .cornerRadius(12.0)
                    }
                }
            }
        }
    }
    
    // the "Add new..." View
    var addTaskView: some View {
        Group {
            if(isAdding) {
                add_view_edit
            } else {
                add_view_button
            }
        }
        .padding(.horizontal, isAdding ? 10 : 30)
        .frame(maxWidth: .infinity, minHeight: 120.0)
        .background(backgroundColor)
        .opacity(isAdding ? 1.0 : 0.9)
    }
    
    // the "Add new..." Button
    var add_view_button: some View {
        Button {
            isAdding = true
        } label: {
            Text("Add new...")
                .foregroundColor(.gray)
                .fontWeight(.heavy)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding(.vertical, 20)
        .padding(.horizontal, 40)
        .background(Color.white)
        .cornerRadius(12.0)
        .overlay(
            RoundedRectangle(cornerRadius: 12.0)
                .strokeBorder(
                    style: StrokeStyle(
                        lineWidth: 1,
                        dash: [5]
                    )
                )
                .foregroundColor(.gray)
        )
    }
    
    // the "Add new..." Edit View
    var add_view_edit: some View {
        HStack {
            TextField("Add new...", text: $editingText, onCommit: {
                if(editingText.trimmingCharacters(in: .whitespaces).count > 0) {
                    withAnimation {
                        viewModel.addTask(group: groupName, content: editingText)
                    }
                    isAdding = false
                }
                editingText = ""
            })
//                .keyboardType(.webSearch)
                .padding()
                .background(Color.white)
                .cornerRadius(12.0)
                .foregroundColor(fontColor)
            Button {
                showGroupChooser = true
            } label: {
                HStack {
                    Text(groupName)
                        .font(.system(size: 10.0))
                        .foregroundColor(fontColor)
                        .onAppear {
                            groupName = viewModel.tasks.map(\.group).unique().first ?? ""
                        }
                    Image(systemName: "chevron.down")
                        .foregroundColor(.gray)
                }
                .padding(.vertical, 12)
                .padding(.horizontal, 10)
                .background(Color.white)
                .cornerRadius(15.0)
                .frame(maxWidth: 100.0, maxHeight: 50.0)
            }
        }
        .sheet(isPresented: $showGroupChooser) {
            List {
                ForEach(viewModel.tasks.map(\.group).unique(), id: \.self) { group in
                    Group {
                        Text(Image(systemName: group == groupName ? "checkmark.circle.fill" : "checkmark.circle")) + Text(" " + group)
                    }.onTapGesture {
                        groupName = group
                        showGroupChooser = false
                    }
                }
                TextField("Add new group...", text: $groupName_input)
            }
            // 添加一个关闭按钮的扩展
            .wrappedInNavigationViewToMakeDismissable {
                if groupName_input.count > 0 {
                    // 优先读取文本框里的值
                    groupName = groupName_input
                    groupName_input = ""
                }
                showGroupChooser = false
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        GTDHomeView(viewModel: GTDViewModel())
            .previewDevice("iPhone 13 Pro")
    }
}
