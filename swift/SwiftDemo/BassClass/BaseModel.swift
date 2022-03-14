//
//  BaseModel.swift
//  SwiftDemo
//
//  Created by AYX on 2022/3/12.
//

import UIKit
import HandyJSON

class BaseModel<T: HandyJSON>: HandyJSON {
    var resultCount: String?
    var results: T?
    var dataList: [T] = []
    required init() {
        
    }
    
    func mapping(mapper: HelpingMapper) {
        mapper <<<
            self.dataList <-- "results"
    }
}
