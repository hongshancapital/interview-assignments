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
            
        }
    }
    
    init(app: AppInfo) {
        self.app = app;
    }
    
    private func updateCollectionState() {
        
    }
}

extension AppInfoViewModel: Identifiable {
    var id: String { app.id }
}
