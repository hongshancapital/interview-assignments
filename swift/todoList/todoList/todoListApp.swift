//
//  todoListApp.swift
//  todoList
//
//  Created by 邓志武(平安科技云技术服务团队咨询服务组) on 2021/12/6.
//

import SwiftUI

@main
struct todoListApp: App {
    @StateObject var listViewModel: ListViewModel = .init()
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ListView()
            }
            .environmentObject(listViewModel)
        }
    }
}
