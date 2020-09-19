//
//  Server.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import Foundation

typealias CompletionHandler<T: Decodable> = (Result<T, ServerError>) -> Void
typealias ActionClosure = () -> Void

struct ServerError : Error {
    let message: String
}

class Server {
    static func excute<T : Decodable>(completion: CompletionHandler<T>?){
        DispatchQueue.global().async {
            guard let url = Bundle.main.url(forResource: "Brand", withExtension: "json") else { completion?(.failure(ServerError(message: "Request Failed!"))); return  }
            guard let data = try? Data(contentsOf: url), let result = T.decode(from: data) else { completion?(.failure(ServerError(message: "Request Failed!"))); return  }
            completion?(.success(result))
        }
    }
    
    static func fetchBrand(completion: CompletionHandler<Brands>?) {
        Server.excute { completion?($0) }
    }
}
