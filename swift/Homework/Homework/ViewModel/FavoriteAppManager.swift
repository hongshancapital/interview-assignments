//
// Homework
// FavoriteAppManager.swift
//
// Created by wuyikai on 2022/4/30.
// 
// 

import Foundation

class FavoriteAppManager: ObservableObject {
    @Published var favoriteAppIds = Set<AppInfo.ID>()
    
    static let sharedInstance = FavoriteAppManager()
    private func `init`() {}
    
    func toggleFavorite(appID: AppInfo.ID) {
        if favoriteAppIds.contains(appID) {
            favoriteAppIds.remove(appID)
        } else {
            favoriteAppIds.insert(appID)
        }
    }
    
    func isFavorite(app: AppInfo) -> Bool {
        favoriteAppIds.contains(app.id)
    }
}
