//
//  ContentView.swift
//  ToDo
//
//  Created by 童雨 on 2021/12/7.
//


import SwiftUI

struct ContentView: View {
    
    //数据源  默认有部分数据
    @State var dataSource = [
        GroupModel.init(modelArr: [
            ToDoModel.init(todoValueS: "Building Lists and Navigation", todoStateB: false),
            ToDoModel.init(todoValueS: "Creating and Combining Views", todoStateB: true),
            ToDoModel.init(todoValueS: "Handling User Input", todoStateB: true)
        ], name: "SwiftUI Essentials"),
        GroupModel.init(modelArr: [
            ToDoModel.init(todoValueS: "Animating Views and Transitions", todoStateB: false),
            ToDoModel.init(todoValueS: "Drawing Paths and Shapes", todoStateB: true),
        ], name: "Drawing and Animation"),
        GroupModel.init(modelArr: [
            ToDoModel.init(todoValueS: "Composing Complex Interfaces", todoStateB: false),
            ToDoModel.init(todoValueS: "Working with UI Controls", todoStateB: false),
        ], name: "App Design and Layout"),
        GroupModel.init(modelArr: [
            ToDoModel.init(todoValueS: "Interfacing with UIKit", todoStateB: false),
            ToDoModel.init(todoValueS: "Creating a watchOS App", todoStateB: false),
            ToDoModel.init(todoValueS: "Creating a macOS App", todoStateB: false)
        ], name: "Framework Integration")
    ]
    
    //判断当前是否是在添加分组
    @State var isAddGroup = false
    //添加分组输入框的焦点状态
    @FocusState var addGroupIsFirstResponder
    //添加分组输入框的内容
    @State var groupName = ""
    //搜索输入框的内容
    @State var searchStr = ""
    
    var body: some View {
        ZStack(alignment: .bottom){
            VStack(alignment: HorizontalAlignment.leading, spacing: 0){
                HStack{
                    Text("List")
                        .font(.system(size: 36, weight: .bold))
                        .padding(.leading, 16)
                        .foregroundColor(Color.init(hex: "FF3C3C3C"))
                    Spacer()
                    Text("Add Group")
                        .font(.system(size: 18, weight: .bold))
                        .padding(.trailing, 16)
                        .foregroundColor(Color.init(hex: "FF3C3C3C"))
                        .gesture(TapGesture()
                                    .onEnded {
                            //显示添加分组的输入框
                            if !isAddGroup {
                                UIApplication.shared.endEditing()
                                isAddGroup = true
                            }
                        })
                }
                TextField("Search todo", text: $searchStr)
                    .font(.system(size: 18, weight: .medium))
                    .foregroundColor(Color.init(hex: "FF3C3C3C"))
                    .frame(height: 36)
                    .multilineTextAlignment(.center)
                    .background(Color.white)
                    .cornerRadius(18)
                    .overlay(RoundedRectangle(cornerRadius: 18, style: .continuous)
                                .stroke(Color.init(hex: "B4B4B4"), style: StrokeStyle(lineWidth: 0.5)))
                    .padding(EdgeInsets(top: 24, leading: 16, bottom: 1, trailing: 16))
                //防止父视图的点击空白 收起键盘   以下同此
                    .gesture(TapGesture()
                                .onEnded {
                    })
                if isAddGroup {
                    TextField("Add new group", text: $groupName, onEditingChanged: { focused in
                        addGroupIsFirstResponder = focused
                        //当失去焦点时  如果分组名称不为空  就添加一个分组
                        if !focused{
                            isAddGroup = false
                            if groupName != "" {
                                dataSource.insert(GroupModel.init(
                                    modelArr:  [ToDoModel](),
                                    name: groupName
                                ), at: 0)
                                //添加完成后 清空输入内容 防止下次默认显示该内容
                                groupName = ""
                            }
                        }
                    })
                        .frame(height: 36)
                        .focused($addGroupIsFirstResponder, equals: true)
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(Color.init(hex: "FF3C3C3C"))
                        .padding(EdgeInsets(top: 24, leading: 16, bottom: 0, trailing: 16))
                        .gesture(TapGesture()
                                    .onEnded {
                        })
                        .onAppear{
                            //自动获取 焦点
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5){
                                addGroupIsFirstResponder = true
                            }
                        }
                }
                List{
                    ForEach(dataSource, id: \.id) { groupModel in
                        //如果该分组没有  包含 searchStr 的 todo 就不显示
                        if checkGroupModelWithSearch(model: groupModel) {
                            ToDoGroupView(groupModel: groupModel, searchStr: $searchStr)
                                .listRowInsets(EdgeInsets())
                                .listRowSeparator(.hidden)
                                .listRowBackground(Color.init(hex: "F5F5F5"))
                        }
                    }
                    //防止FooterView 遮挡List
                    Spacer()
                        .frame(height: 90)
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.init(hex: "F5F5F5"))
                }
                .listStyle(.plain)
            }
            .background(Color.init(hex: "F5F5F5"))
            //点击空白 收起键盘
            .gesture(TapGesture()
                        .onEnded {
                UIApplication.shared.endEditing()
            }
            )
            FooterView(dataSource: $dataSource)
        }
    }
    
    //判断当前分组 有没有todo 包含搜索内容
    func checkGroupModelWithSearch(model: GroupModel) -> Bool {
        var isShow = false
        if searchStr != "" {
            for todoData in model.todoModelArr {
                if todoData.todoValue.contains(searchStr){
                    isShow = true
                    break
                }
            }
        }else{
            isShow = true
        }
        
        return isShow
    }
    
}

//底部视图
struct FooterView:  View{
    //关联数据源刷新列表
    @Binding var dataSource: [GroupModel]
    //判断当前输入框状态
    @State var isAddToDo = false
    //add todo输入框内容
    @State var todoName = ""
    //键盘高度   页面其他地方的输入框获取焦点时  底部视图 不再上移
    @State var keyBoardH : CGFloat = 0
    //是否显示 分组 选择视图
    @State var isChangeGroup = false
    //当前选择分组的下标  默认第一组
    @State var groupIndex = 0
    
    var body: some View{
        ZStack(alignment: .bottomTrailing){
            HStack{
                TextField("Add new todo", text: $todoName,onEditingChanged: { focused in
                    //失去焦点时  当输入框内容 不为空  则新增一条todo
                    if !focused {
                        isAddToDo = false
                        //收起 分组选择 视图
                        if isChangeGroup {
                            withAnimation(.easeInOut(duration: 0.2)) {
                                isChangeGroup = false
                            }
                        }
                        if todoName != "" {
                            //修改 dataSource 触发页面刷新
                            var groupModel = dataSource[groupIndex];
                            var todoArr = groupModel.todoModelArr;
                            let groupName = groupModel.groupName;
                            todoArr.insert(ToDoModel.init(
                                todoValueS: todoName, todoStateB: false
                            ), at: 0)
                            groupModel = GroupModel.init(modelArr: todoArr, name: groupName)
                            dataSource.remove(at: groupIndex)
                            dataSource.insert(groupModel, at: groupIndex)
                            todoName = ""
                        }
                    }else{
                        isAddToDo = true
                    }
                }).onChange(of: todoName) { newValue in
                    //如果输入框内容正在变化  收起分组选择视图
                    if isChangeGroup {
                        withAnimation(.easeInOut(duration: 0.2)) {
                            isChangeGroup = false
                        }
                    }
                }
                .font(.system(size: 18, weight: .medium))
                .foregroundColor(Color.init(hex: "FF3C3C3C"))
                .frame(height: 44)
                .padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
                .background(Color.white)
                .cornerRadius(8)
                .overlay(RoundedRectangle(cornerRadius: 8, style: .continuous)
                            .stroke(isAddToDo ? Color.white : Color.init(hex: "B4B4B4"), style: StrokeStyle(lineWidth: 1, dash: [4])))
                if (isAddToDo) {
                    Button(action:{
                        //控制 显示/隐藏 分组选择视图
                        withAnimation(.easeInOut(duration: 0.2)) {
                            isChangeGroup.toggle()
                        }
                    }){
                        Text(dataSource[groupIndex].groupName)
                            .lineLimit(2)
                            .fixedSize(horizontal: false, vertical: true)
                            .multilineTextAlignment(.leading)
                            .font(.system(size: 10, weight: .semibold))
                            .foregroundColor(Color.init(hex: "FF3C3C3C"))
                            .frame(minWidth:24, maxWidth:70)
                        Image(isChangeGroup ? "viewShow" : "viewHidden")
                            .frame(width: 16, height: 10)
                    }
                    .padding(EdgeInsets(top: 16, leading: 8, bottom: 16, trailing: 16))
                    .background(Color.white)
                    .frame(height: 36)
                    .cornerRadius(18)
                }
            }
            .padding(EdgeInsets(top: 32, leading: 16, bottom: 16, trailing: 16))
            //渐变色
            .background(LinearGradient(gradient: Gradient(colors: [Color.init(hex: "F5F5F5").opacity(1),Color.init(hex: "F5F5F5").opacity(0.2)]), startPoint: .center, endPoint: .top))
            List{
                ForEach(dataSource.indices, id:\.self) { index in
                    HStack{
                        Text(dataSource[index].groupName)
                            .font(.system(size: 12, weight: .medium))
                            .foregroundColor(Color.init(hex: "FF3C3C3C"))
                            .frame(width: 200,height: 40)
                            .gesture(TapGesture()
                                        .onEnded {
                                //点击分组  收起视图  并切换当前选中分组
                                withAnimation(.easeInOut(duration: 0.2)) {
                                    isChangeGroup.toggle()
                                }
                                groupIndex = index
                            })
                    }
                    .listRowInsets(EdgeInsets())
                    .listRowSeparator(.hidden)
                    .background(index%2 == 1 ? Color.init(hex: "F5F5F5") : Color.white)
                }
            }
            .background(Color.white)
            .listStyle(.plain)
            .cornerRadius(8)
            .shadow(color: Color.black.opacity(0.5), radius: 6, x: 3, y: 3)
            .frame(width: isChangeGroup ? 200 : 0, height: isChangeGroup ? (dataSource.count > 5 ? 200 : CGFloat(dataSource.count) * 40) : 0)
            .offset(x: -56, y: isChangeGroup ?  -68 : -34)
            .opacity(isChangeGroup ? 1.0 : 0)
        }
        .offset(y : keyBoardH)
        .onAppear {
            //键盘抬起
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillShowNotification, object: nil, queue: OperationQueue.current) { (noti) in
                if !isAddToDo{
                    let value = noti.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as! CGRect
                    keyBoardH = value.height
                }
            }
            //键盘收回
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: OperationQueue.current) { (noti) in
                if !isAddToDo{
                    keyBoardH = 0
                }
            }
        }
    }
}

//分组 视图
struct ToDoGroupView: View{
    
    //关联 数据模型
    @ObservedObject var groupModel: GroupModel
    //关联搜索内容
    @Binding var searchStr: String
    
    var body: some View{
        VStack(alignment: HorizontalAlignment.leading){
            Text(groupModel.groupName)
                .font(.system(size: 20, weight: .bold))
                .foregroundColor(Color.init(hex: "FF3C3C3C"))
                .frame(height: 36, alignment: Alignment.center)
                .padding(EdgeInsets(top: 24, leading: 16, bottom: 0, trailing: 16))
            ForEach(groupModel.todoModelArr.indices, id:\.self) { index in
                //不包含搜索内容的 todo 不显示
                if groupModel.todoModelArr[index].todoValue.contains(searchStr) || searchStr == "" {
                    ToDoItemView(todoModel: groupModel.todoModelArr[index], todoModelArr: $groupModel.todoModelArr, elementIndx:index)
                }
            }
        }
    }
}

//todo 视图
struct ToDoItemView: View{
    
    //关联 数据模型
    @ObservedObject var todoModel: ToDoModel
    //关联todo 列表  用于分组视图的刷新
    @Binding var todoModelArr: [ToDoModel]
    //当前todo 下标
    var elementIndx: Int
    //当前 是否是编辑模式
    @State var editor = false
    //输入框 焦点状态
    @FocusState var _isFirstResponder
    //输入框内容
    @State var todoStr = ""
    
    var body: some View{
        HStack(){
            Image(todoModel.todoState ? "choose":"no_choose")
                .frame(width: 24, height: 24)
                .padding(.leading, 16)
            if editor {
                TextField("input", text: $todoStr, onEditingChanged: { focused in
                    //当失去焦点的时候  如果输入内容不为空  则 修改 todo
                    _isFirstResponder = focused
                    if !focused {
                        if todoStr != "" {
                            todoModel.todoValue = todoStr
                        }
                        editor = false
                    }
                }, onCommit: {
                    //当点击提交的时候  如果输入内容为空  则 删除 todo
                    if todoStr == ""{
                        todoModelArr.remove(at: elementIndx)
                    }
                })
                    .frame(height: 64)
                    .focused($_isFirstResponder, equals: true)
                    .font(.system(size: 18, weight: .medium))
                    .foregroundColor(Color.init(hex: "FF3C3C3C"))
                    .padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
                    .onAppear{
                        //挂载后 自动获取焦点
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5){
                            _isFirstResponder = true
                        }
                    }
            }else{
                Text(todoModel.todoValue)
                    .strikethrough(todoModel.todoState,color: Color.init(hex: "FFCCCCCC"))
                    .frame(minHeight: 64)
                    .font(.system(size: 18, weight: .medium))
                    .foregroundColor(todoModel.todoState ? Color.init(hex: "FFCCCCCC") : Color.init(hex: "FF3C3C3C"))
                    .padding(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: 16))
            }
            Spacer()
        }
        .frame(maxWidth: .infinity)
        .background(Color.white)
        .cornerRadius(8)
        .padding(EdgeInsets(top : 0, leading:16, bottom: 4, trailing:16))
        .shadow(color: Color.black.opacity(0.03), radius: 3, x: 3, y: 3)
        .gesture(TapGesture()
                    .onEnded {
            //点击 修改 todo 完成状态
            if !editor {
                UIApplication.shared.endEditing()
                todoModel.todoState = !todoModel.todoState
                todoModelArr.remove(at: elementIndx)
                if todoModel.todoState {
                    todoModelArr.append(todoModel)
                }else{
                    todoModelArr.insert(todoModel, at: 0)
                }
            }
        })
        .onLongPressGesture{
            //长按 进入编辑模式
            if !editor {
                todoStr = todoModel.todoValue
                UIApplication.shared.endEditing()
                editor = true
            }
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView.init()
//    }
//}

