//
//  File.swift
//  APPList
//
//  Created by three on 2023/4/11.
//

import Foundation

class LocalStorage {
    private let Favourite_Items_Key = "Favourite_Items_Key"
    
    func saveFavs(items: Set<Int>) {
        let array = Array(items)
        UserDefaults.standard.set(array, forKey: Favourite_Items_Key)
    }
    
    func loadFavs() -> Set<Int> {
        let array = UserDefaults.standard.array(forKey: Favourite_Items_Key) as? [Int] ?? [Int]()
        return Set(array)
    }
}
