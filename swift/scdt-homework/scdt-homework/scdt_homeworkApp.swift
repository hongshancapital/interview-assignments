//
//  scdt_homeworkApp.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/25.
//

import SwiftUI

@main
struct scdt_homeworkApp: App {
    
  //  @Namespace var animation
    @StateObject var vm = ViewModel()
    
    var body: some Scene {
        WindowGroup {
            // MARK: - 点击主页的搜索（按钮）时，显示搜索详情页
            if vm.isSearch {
                SearchView().environmentObject(vm)
                    .transition(AnyTransition.opacity.animation(.easeInOut(duration: 0.4)))
            } else {
                HomeView().environmentObject(vm)
                    .transition(AnyTransition.opacity.animation(.easeInOut(duration: 0.4)))
            }
        }
    }
}
