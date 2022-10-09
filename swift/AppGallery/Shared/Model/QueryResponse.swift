//
//  QueryResponse.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

struct QueryResponse<T: Decodable>: Decodable {
    
    let resultCount: Int
    
    let results: [T]
    
}
