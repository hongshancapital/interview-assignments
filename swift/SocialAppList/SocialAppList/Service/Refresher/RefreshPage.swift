//
//  RefreshPage.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import Foundation

struct RefreshPage {
    private(set) var firstValue: Int
    var value: Int
    
    init(value: Int) {
        self.firstValue = value >= 0 ? value : 1
        self.value = self.firstValue
    }

    func isInvalidMoreData(page: Int) -> Bool {
        return !isValidMoreData(page: page)
    }

    func isValidMoreData(page: Int) -> Bool {
        return page - value == 1
    }
}
