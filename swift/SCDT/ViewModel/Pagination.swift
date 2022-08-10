//
//  Pagination.swift
//  SCDT
//
//  Created by Zhao Sam on 2022/8/6.
//

import Foundation

class Pagination {
    var offset: Int = 0
    var size: Int
    var hasMore: Bool = true
    
    init(size plength: Int) {
        size = max(plength, 0)
    }
    
    func reset() {
        self.offset = 0
        self.hasMore = true
    }
    
    func nextPage() {
        if hasMore {
            offset = offset + size
        }
    }
}
