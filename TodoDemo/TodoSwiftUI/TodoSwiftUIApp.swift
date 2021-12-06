//
//  TodoSwiftUIApp.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/3.
//

import SwiftUI


/*
 checklist
 
 done:
 分组显示Todo
 点击 Add new 添加新todo
 长按 todo， 进入编辑状态
 清空内容return 删除todo
 unchecked todo 始终在 checked todo的上方
 添加todo 可选分组
 
todo:
UI原型 1：1 (需要花点时间研究下ui层面的api）
添加分组
Search
 
已知bug：
长按进入编辑页面 对于新增的item会有问题。 NavigationLink 的这种用法还不太适应。 (fixed)
 
ps:
 之前没接触过swiftUI，12月3号拿到的需求，研究了下，12月5号边看边写大概花了5个小时。
 主要 resource https://www.hackingwithswift.com/100/swiftui
 */

@main
struct TodoSwiftUIApp: App {
    
    @StateObject var listViewModel: TodoListViewModel = TodoListViewModel()
//    
//    init() {
//        UITableView.appearance().backgroundColor = .clear
//        UITableView.appearance().separatorStyle = .none
//        UITableViewCell.appearance().selectionStyle = .none
//    }
    
    var body: some Scene {
        WindowGroup {
            NavigationView{
                ListView()
            }
            .navigationViewStyle(StackNavigationViewStyle())
            .environmentObject(listViewModel)
        }
    }
}
