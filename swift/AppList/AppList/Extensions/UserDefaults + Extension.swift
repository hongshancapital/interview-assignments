//
//  UserDefaults + Extension.swift
//  demo
//
//  Created by shu li on 2023/4/7.
//

import Foundation

extension UniqueKey where Self: RawRepresentable, Self.RawValue == String {
    func set(_ value: Any?) {
        UserDefaults.standard.set(value, forKey: uniqueKey)
    }
    
    var stringArrayValue: [String]? {
        UserDefaults.standard.stringArray(forKey: uniqueKey)
    }
}

extension UserDefaults {
    enum CardList: String, UniqueKey {
        case likedData
    }
}
