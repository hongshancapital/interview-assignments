//
// Homework
// ContentView.swift
//
// Created by wuyikai on 2022/4/27.
// 

import SwiftUI

struct RootView: View {
    
    init() {
        UITableView.appearance().sectionHeaderHeight = 0
    }
    
    var body: some View {
        NavigationView {
            AppListView()
        }
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView()
    }
}
