//
//  MainApp.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import SwiftUI

@main
struct MainApp: App {
    
    init() {
        /// 启动本地服务
        LocalServerManager.shared.startLocalService()
    }
    
    var body: some Scene {
        WindowGroup {
            SearchListView()
        }
    }
}
