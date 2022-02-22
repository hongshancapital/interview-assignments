//
//  Extensions.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation

extension String {
    var data: Data? {
        return self.data(using: .utf8)
    }
    
    var jsonObject: Any? {
        return data?.jsonObject
    }
}

extension Array {
    var data: Data? {
        return try? JSONSerialization.data(withJSONObject: self, options: .prettyPrinted)
    }
    
    var string: String? {
        guard let data = data else { return nil }
        return String(data: data, encoding: .utf8)
    }
}

extension Dictionary {
    var data: Data? {
        return try? JSONSerialization.data(withJSONObject: self, options: .fragmentsAllowed)
    }
    
    var string: String? {
        guard let data = data else { return nil }
        return String(data: data, encoding: .utf8)
    }
}


extension Data {
    var jsonObject: Any? {
        return try? JSONSerialization.jsonObject(with: self, options: .fragmentsAllowed)
    }
    
    var string: String? {
        return try? JSONSerialization.jsonObject(with: self, options: .fragmentsAllowed) as? String
    }
}

extension RandomAccessCollection where Self.Element: Identifiable {
    public func isLastItem<Item: Identifiable>(_ item: Item) -> Bool {
        guard !isEmpty else {
            return false
        }
        
        guard let itemIndex = lastIndex(where: { AnyHashable($0.id) == AnyHashable(item.id) }) else {
            return false
        }
        
        let distance = self.distance(from: itemIndex, to: endIndex)
        return distance == 1
    }
}

