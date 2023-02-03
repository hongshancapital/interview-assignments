//
//  DataBase.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/3.
//

import Foundation


final class Database {
    private let Collect_KEY = "collect_key"
    
    func save(items: Set<Int>) {
        let array = Array(items)
        UserDefaults.standard.set(array, forKey: Collect_KEY)
    }
    
    func load() -> Set<Int> {
        let array = UserDefaults.standard.array(forKey: Collect_KEY) as? [Int] ?? [Int]()
        return Set(array)
        
    }
    
    func reset() {
        UserDefaults.standard.removeObject(forKey: Collect_KEY)
    }
}
