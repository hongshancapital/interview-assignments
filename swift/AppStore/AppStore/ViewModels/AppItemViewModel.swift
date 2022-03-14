//
//  AppItemViewModel.swift
//  AppStore
//
//  Created by Ma on 2022/3/13.
//

import Foundation

class AppItemViewModel: ObservableObject {
    @Published var app: AppItem
    
    init(app:AppItem) {
        self.app = app
    }
    
    enum Event {
        case like
        case unlike
    }
    
    func excute(_ event:Event) {
        switch event {
        case .like:
            AppDataCache.setFavorated(id: app.id, favorated: true)
            app.isFavorated = true
        case .unlike:
            AppDataCache.setFavorated(id: app.id, favorated: false)
            app.isFavorated = false
        }
    }
}
