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
    let backgroundColor  = Color(red: 227.0/255.0, green: 227.0/255.0, blue: 228.0/255.0)
    let fontSize         = 12.0
    let fontColor        = Color(red: 62.0/255.0, green: 68.0/255.0, blue: 63.0/255.0)
    let deactiveColor    = Color(red: 186.0/255.0, green: 186.0/255.0, blue: 186.0/255.0)
    
    // states
    @State private var isAdding = false             // 添加任务
    @State private var groupName = ""               // 添加任务-组名
    @State private var groupName_input = ""         // 添加任务-组名-文本框
    @State private var showGroupChooser = false     // 添加任务-组名-显示弹窗
    @State private var editingId = -1               // 编辑任务-ID
    @State private var editingText = ""             // 编辑任务-内容
    @State private var keyboardHeight: CGFloat = 0.0// 屏幕上键盘高度
    
    // 文本框状态
    private enum Field: Int, Hashable {
        case add, edit
    }
    @FocusState private var focusedField: Field?
    @State private var searchText = ""
    // views
    var body: some View {
        ZStack(alignment: .bottom) {
            backgroundColor
        NavigationView {
                ScrollViewReader { reader in
                    ScrollView(axes: [.vertical], showsIndicators: false) { point in
                        if point.y > 100.0 {
                            // 模仿Reminders应用，列表往下拉就关闭键盘
                            endEditing()
                        }
                    } content: {
                        VStack(alignment: .leading) {
                            bodyView(scroll: reader)
                                .searchable(text: $searchText)
                            Spacer(minLength: 120.0)
                        }
                        .padding()
                        .foregroundColor(fontColor)
                    }
                }
                .background(backgroundColor)
                .navigationTitle(Text("List"))
            }
            if(isAdding) {
                Color.black.opacity(0.0001) // fix Color.clear has no touch event bug
                    .onTapGesture {
                        // 响应关闭键盘事件的透明层
                        endEditing()
                    }
            }
            addTaskView
                .opacity((!isAdding && (keyboardHeight > 100)) ? 0.0 : 1.0)  // 别的编辑状态下，隐藏这个“添加”
            .adaptsToKeyboard(offset: $keyboardHeight)  // 之前用它来监测键盘拉出，以改变浮层位置，改用navigationView后不需要了，用它来做一个标识给别的场景用
        }
        .preferredColorScheme(.light)
    }
    
    private var tasks: [GTDTaskModel] {
        if searchText.isEmpty {
            return viewModel.tasks
        } else {
            return viewModel.tasks.filter {$0.content.lowercased().contains(searchText.lowercased())}
        }
    }
    
    @GestureState private var dragStatus = 1.0
    
    @ViewBuilder
    func bodyView(scroll proxy: ScrollViewProxy) -> some View {
        // extract group from tasks
       VStack(alignment: .leading) {
            ForEach(tasks.map(\.group).unique(), id: \.self) { title in
                Text(title)
                    .font(.system(size: 12.0))
                    .fontWeight(.heavy)
                    .padding(.vertical, 10)
                // get task by group
                // 用过滤+去重提取组名
                // 用checked来排序
                ForEach(tasks.filter{$0.group == title}.sorted{!$0.checked && $1.checked}){ task in
                    taskView(task)
                    .id(task.id)
                    .frame(maxWidth: .infinity) // full length of a cell
                    .padding(EdgeInsets(top: 10, leading: 15, bottom: 10, trailing: 20))
                    .background(Color.white)
                    .cornerRadius(12.0)
                    .onTapGesture{} // 解决scroll与longpress的手势冲突
                    .gesture(LongPressGesture(minimumDuration: 0.3)
                                .sequenced(before: DragGesture(minimumDistance: 0, coordinateSpace: .global))
                                .onEnded { value in
                        switch value {
                        case .second(true, let drag?):
                            // 用drag手势记下长按的位置（用来判断是否该滚动视图以响应键盘拉起拉住下半截）
                            // 键盘origin.y 与 当前元素point.y的差即为需要上移的大概值
                            if drag.location.y > keyboardHeight {   // 先写死键盘高度
                                proxy.scrollTo(task.id, anchor: .center)
                            }
                            
                            editingId = task.id
                            editingText = task.content
                        default:
                            break
                        }
                    })
                }
            }
       }.frame(maxWidth: .infinity)
    }
    
    // 就是一行的内容
    // 这个taskView写到上面的方法体里时
    // 只要手势里出现了task的使用，在build期间就会吃光一台电脑的内存
    // 试了很多办法，虽然把代码提出来就能顺利编译了
    // 可见到目前的这一版SwiftUI为止，尽量不要有过长的代码，能分解的就分解
    @ViewBuilder
    func taskView(_ task: GTDTaskModel) -> some View {
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
                        // 改过名字的checked的任务就需要变回uncheck的状态了
                        // 但想知道是不是改动过，还得保留一份修改前的状态，徒增复杂度，暂不实现
//                        if(task.checked) {
//                            viewModel.toggleTaskStatus(for: task.id)
//                        }
                    }
                    endEditing()
                })
                    .focused($focusedField, equals: .edit)
                    .onAppear{
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.01) {
                            focusedField = .edit
                        }
                    }
                    .font(.system(size: fontSize))
            } else {
                Text(task.content)
                    .strikethrough(task.checked)
                    .foregroundColor(task.checked ? deactiveColor : fontColor)
                    .font(.system(size: fontSize))
                    .fontWeight(.heavy)
            }
            Spacer()
        }
    }
    
    // the "Add new..." View
    var addTaskView: some View {
        Group {
            if(isAdding) {
                addViewEdit
            } else {
                addViewButton
            }
        }
        .padding(.horizontal, isAdding ? 10 : 30)
        .frame(maxWidth: .infinity, minHeight: 100.0)
        .background(backgroundColor)
        .opacity(isAdding ? 1.0 : 0.9)
    }
    
    // the "Add new..." Button
    var addViewButton: some View {
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
    var addViewEdit: some View {
        HStack {
            TextField("Add new...", text: $editingText, onCommit: {
                if(editingText.trimmingCharacters(in: .whitespaces).count > 0) {
                    withAnimation {
                        viewModel.addTask(group: groupName, content: editingText)
                    }
                    isAdding = false
                }
                endEditing()
            })
                .focused($focusedField, equals: .add)
                .onAppear{
                    // 匪夷所思的bug，之前只要设了field就生效
                    // 跑着跑着就要延时来设才生效了...
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.01) {
                        focusedField = .add
                    }
                }
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
    
    // 退出编辑状态的统一方法
    func endEditing() {
        editingId = -1
        editingText = ""
        isAdding = false
        focusedField = nil
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        GTDHomeView(viewModel: GTDViewModel())
            .previewDevice("iPhone 13 Pro")
    }
}
