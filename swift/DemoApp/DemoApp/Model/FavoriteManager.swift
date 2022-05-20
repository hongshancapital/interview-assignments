//
//  FavoriteManager.swift
//  DemoApp
//
//  Created by liang on 2022/5/20.
//

import Foundation

class FavoriteManager {
    static let shared = FavoriteManager()
    private var favoriteById: [String: String]
    private let key = "com.userdefault.favorite"
    private init() {
        if let favoriteDictionary = UserDefaults.standard.dictionary(forKey: key) as? [String: String] {
            favoriteById = favoriteDictionary
        } else {
            favoriteById = [:]
        }
    }
    
    func setFavorite(isFavorite: Bool, by appId: Int) {
        
        favoriteById[String(appId)] = String(isFavorite)
        save()
    }
    
    func isFavorite(by appId: Int)-> Bool {
        let number = favoriteById[String(appId)] ?? String(false)
        return Bool(number)!
    }
    
    func save() {
        UserDefaults.standard.set(favoriteById, forKey: key)
    }
}
