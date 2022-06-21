//
//  RequestType.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import Foundation

protocol RequestType {
    
    var baseURL : URL { get }
    
    var path : String? { get }
    
    var method : Method { get }
    
    var param : [String : String]? { get }
}
