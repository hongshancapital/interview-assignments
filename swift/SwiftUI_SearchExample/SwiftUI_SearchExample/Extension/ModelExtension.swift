//
//  ModelExtension.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation

extension Encodable {
    
    // 扩展出一个转换JSON的方法
    func JSONValue() -> Any? {
        guard let data = try? JSONEncoder().encode(self) else {
            return nil
        }
        let json = try? JSONSerialization.jsonObject(with: data, options: .fragmentsAllowed)
        return json
    }
    
}
