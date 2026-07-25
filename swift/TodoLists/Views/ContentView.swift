//
//  ContentView.swift
//  TodoList
//
//  Created by Chr1s on 2021/10/28.
//

import SwiftUI
import CoreData

struct ContentView: View {
    
    @StateObject var vm = ViewModel()
    
    /// 添加时选择的分组
    @State var choosedGroup: ToDoListGroup?
    
    /// 要添加的是否为分组
    /// - true : 添加分组
    /// - false: 添加事项
    @State var isAddingGroup: Bool = false
    @State var newTodo: String = ""
    @State var isButtonShow: Bool = false
 
    var body: some View {
        
        NavigationView {
        
            ZStack {
                
                Color.secondary.opacity(0.4).edgesIgnoringSafeArea(.all)
                
                VStack {
                    
                    ScrollView(.vertical, showsIndicators: false) {
                        
                        VStack(alignment: .leading) {
                            
                            ForEach(vm.todoGroups) { group in
                                DisplayTodoGroup(group: group)
                            }
                        }
                        .padding(.horizontal, 20)
                        .padding(.top, 20)
                        .blur(radius: isButtonShow ? 1 : 0)
                    }
                    .searchable(text: $vm.searchText, prompt: "Search todo...")
                }
                    
                AddItemOrGroupRow()
            }
            .navigationTitle("List")
        }
    }
}

// MARK: - Extension for Add Item/Group TextField and Button
//         1、点击 TextField 时，显示右侧菜单按钮
//         2、菜单按钮分为"Add New Group"添加新分组项和其他已有分组项
//         3、选择"Add New Group"是，在 TextField 中输入并确认，是添加新的分组
//         4、选择其他已有分组项，在 TextField 中输入并确认，是在此分组下添加新的事项
extension ContentView {
    
    func AddItemOrGroupRow() -> some View {
        VStack {
            Spacer()
            HStack {
                TextField((self.choosedGroup == nil) ? "Add Group..." : "Add Item...", text: $newTodo, onEditingChanged: { _ in
                    withAnimation(.easeInOut) {
                        isButtonShow = true
                    }
                }, onCommit: commitHandler)
                .autocapitalization(.none)
                .disableAutocorrection(true)
                .padding()
                .background(Color(UIColor.systemBackground))
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .background(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(Color.gray, style: isButtonShow ? StrokeStyle(lineWidth: 0) : StrokeStyle(lineWidth: 2, dash: [5]))
                )
               
                if self.isButtonShow {
                    menuButton()
                        .shadow(color: Color.gray.opacity(0.3), radius: 1, x: 1, y: 1)
                }
            }
            .frame(height: 60)
            .background(
                Rectangle()
                    .frame(width: UIScreen.main.bounds.width, height: 80)
                    .foregroundColor(isButtonShow ? Color(UIColor.systemGray4) : Color.clear)
            )
            .padding(.horizontal)
        }
        /// 忽略底部安全区时，如果弹出软键盘，则TextField不会显示
        /// 通过TextField是否获取焦点，设置忽略顶部或底部安全区，来控制TextFiled是否显示
        .offset(y: isButtonShow ? 0 : -20)
        .edgesIgnoringSafeArea(isButtonShow ? .top : .bottom)
        .frame(maxWidth: .infinity)
    }
    
    func commitHandler() {
        DispatchQueue.main.async {
            withAnimation(.easeInOut) {
                isButtonShow = false
                if !newTodo.isEmpty {
                    if let group = self.choosedGroup {
                        /// 添加Todo事项
                        vm.addItemSubject.send((group, newTodo))
                    } else {
                        /// 添加Todo分组
                        vm.addGroupSubject.send(newTodo)
                    }
                    newTodo = ""
                }
            }
        }
    }
    
    /// 菜单按钮
    func menuButton() -> some View {
        Menu {
            /// "Add New Group" Menu
            Section {
                Button {
                    self.choosedGroup = nil
                } label: {
                    Label("Add New Group...", systemImage: "plus.circle")
                }
            }
            /// Exist Groups Menu
            Section {
                ForEach(vm.todoGroups.reversed()) { group in
                    Button(group.wrappedGroup) {
                        self.choosedGroup = group
                    }
                }
            }
          
        } label: {
            HStack(spacing: 1.0) {
                Group {
                    if let group = self.choosedGroup {
                        Text(group.wrappedGroup)
                    } else {
                        Text("Add New Group")
                    }
                }
                .frame(width: 50)
                .fixedSize(horizontal: true, vertical: true)
                .font(.system(size: 10, weight: .bold))
                .lineLimit(2)
                .multilineTextAlignment(.leading)
                .foregroundColor(.primary)

                Image(systemName: "arrow.up.circle")
                    .foregroundColor(.secondary)
            }
            .frame(height: 45)
            .padding(.horizontal)
            .background(Color(UIColor.systemBackground))
            .clipShape(RoundedRectangle(cornerRadius: 22))
        }
    }
}

extension ContentView {
    
    /// 显示单个分组和分组内所有事项
    func DisplayTodoGroup(group: ToDoListGroup) -> some View {
        VStack {
            HStack {
                Text(group.wrappedGroup).bold()
                Spacer()
            }

            ForEach(group.filterItem(text: vm.searchText)) { item in
                CustomListCell(item: item)
                    .environmentObject(vm)
            }
        }.padding(.top, 10)
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().preferredColorScheme(.light).environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
        
        ContentView().preferredColorScheme(.dark).environment(\.managedObjectContext, PersistenceController.preview.container.viewContext)
    }
}
