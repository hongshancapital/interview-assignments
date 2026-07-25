//
//  SearchRequest.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation

class SearchRequest: NSObject {
    
    class func dataTaskPublisher(keyword: String, index: Int) -> URLSession.DataTaskPublisher {
        return Request(method: "GET",
                       headers: [:],
                       url: kApiHost + kApiSearch,
                       query: ["keyword": keyword, "index": "\(index)", "count": "10"],
                       body: [:])
            .dataTaskPublisher()
    }
    
}
