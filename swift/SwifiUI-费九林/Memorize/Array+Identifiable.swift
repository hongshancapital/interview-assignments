//
//  Array+Identifiable.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/27.
//

import Foundation

extension Array where Element: Identifiable{
    
    func firstIndex(matching: Element) -> Int? {
        for index in 0..<self.count {
            if self[index].id == matching.id {
                return index
            }
        }
        
        return nil
    }
    
}
