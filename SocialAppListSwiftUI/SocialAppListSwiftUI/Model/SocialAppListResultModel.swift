//
//  SocialAppListResultModel.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/25.
//

import Foundation

struct SocialAppListResultModel: Decodable {
    
    /// Data size
    let resultCount: Int
    
    /// data list
    let results: [SocialAppModel]

}
