//
//  AssignmentApp.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//

import SwiftUI

@main
struct AssignmentApp: App {
    init() {
        //Toggle network authentication
        URLSession.shared.dataTask(with: URL(string: "https://baidu.com/")!) { _, _, _ in}
        DataProvider.copyDataFile()
    }
    
    var body: some Scene {
        WindowGroup {
            IndexView()
        }
    }
}
