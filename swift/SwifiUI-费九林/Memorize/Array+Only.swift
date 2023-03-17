//
//  Array+Only.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/27.
//

import Foundation

extension Array {
    var only: Element? {
        count == 1 ? first : nil
    }
    
}
