//
//  StoreApp.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/1.
//

import SwiftUI

@main
struct StoreApp: App {
    
    @StateObject var storeApp = StoreListViewModel()
    
    var body: some Scene {
        WindowGroup {
            StoreList(viewModel: storeApp)
        }
    }
}
