//
//  Page.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation

struct Page {
    
    var total: Int
    var page: Int = 1
    var perPage: Int = 10
    
    var totalPage: Int {
        if total == 0 { return 0 }
        return (total-1)/perPage+1
    }
    var nextPage: Int { page+1 }
    var range: Range<Int>? {
        if total == 0 { return nil }
        return range(for: page)
    }
    var rangeOfAll: Range<Int>? {
        if total == 0 { return nil }
        return 0..<total
    }
    
    /// 通过序列索引（0..<total）求其所在页
    func page(of index: Int) -> Int? {
        if index<0 || index>total-1 { return nil }
        return index/perPage+1
    }
    
    /// 通过页数求范围
    func range(for somePage: Int) -> Range<Int>? {
        if somePage<1 || somePage>total { return nil }
        return (somePage-1)*perPage..<min(somePage*perPage, total)
    }
    
    /// 通过索引求范围
    func range(of index: Int) -> Range<Int>? {
        if let currentPage = page(of: index) {
            return range(for: currentPage)
        }
        return nil
    }
}
