//
//  StockModel.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/25.
//

import Foundation

// MARK: - StockInfoElement
struct StockInfoElement: Codable, Hashable {
    
    var id: Int = 0
    var category: String = ""
    var content: [Content] = []
    
    init(id: Int = 0, category: String = "", content: [Content] = []) {
        self.id = id
        self.category = category
        self.content = content
    }

    // MARK: - 转换为Ambassador的JSONResponse需要的
    //         [String: Any]类型JSON数据
    var dictionary: [String: Any] {
        
        var cont: [[String: Any]] = []
        for c in content {
            cont.append(c.dictionary)
        }
        
        return ["id": id,
                "category": category,
                "content": cont]
    }
}

// MARK: - Content
struct Content: Codable, Hashable {
    var name: String = ""
    var type: String = ""
    var price: Double = 0.0
    var author: String = ""
    var frozen: Int = 0
    
    init(name: String = "", type: String = "", price: Double = 0.0, author: String = "", frozen: Int = 0) {
        self.name = name
        self.type = type
        self.price = price
        self.author = author
        self.frozen = frozen
    }
    
    // MARK: - 转换为Ambassador的JSONResponse需要的
    //         [String: Any]类型JSON数据
    var dictionary: [String: Any] {
        return ["name": name,
                "type": type,
                "price": price,
                "author": author,
                "frozen": frozen]
    }
}

typealias StockInfo = [StockInfoElement]


// MARK: - Network Error
enum NetworkError: Error {
    case unknown
    case badURL
    case badResponse
    case decodeError
}

