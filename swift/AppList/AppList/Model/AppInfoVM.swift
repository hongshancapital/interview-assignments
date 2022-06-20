//
//  AppInfoVM.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/10.
//

import Foundation

class AppInfoVM: ObservableObject {
    @Published var appInfo: AppInfo?
    @Published var isFavorited: Bool = false
    
    init(appInfo: AppInfo, isFavorited: Bool) {
        self.appInfo = appInfo
        self.isFavorited = isFavorited
    }
}
