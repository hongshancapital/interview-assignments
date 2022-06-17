//
//  AppInfoViewModel.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import Foundation

class AppInfoViewModel: ObservableObject {
    var app: AppInfo
    @Published var isCollected = false {
        didSet {
            updateCollectionState()
        }
        
    }
    
    init(app: AppInfo) {
        self.app = app;
        self.isCollected = UserDefaults.standard.string(forKey: collectUserDefaultKey) != nil
    }
    
    private func updateCollectionState() {
        let value = isCollected ? "\(app.trackId)" : nil
        UserDefaults.standard.setValue(value, forKey: collectUserDefaultKey)
        UserDefaults.standard.synchronize()
    }
    
    var collectUserDefaultKey: String {
        return "my_apps_user_defaultKey_collected" + "\(app.trackId)"
    }
    
}

extension AppInfoViewModel: Identifiable {
    var id: String { app.id }
}
