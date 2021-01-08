//
//  Brands.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import Foundation
import SwiftUI

struct Brands : Codable, Identifiable {
    
    // MARK: Nested Types
    struct Brand : Codable, Identifiable {
        let id: Int
        let name: String
        let series: [Series]
    }
    
    struct Series : Codable, Identifiable {
        let id: Int
        let name: String
        let items: [Item]
    }
    
    struct Item : Codable, Identifiable {
        let id: Int
        let name: String
        let status: Status
        let price: Decimal
    }
    
    enum Status: Int, Codable {
        case available, stockout
        
        var text: String {
            switch self {
            case .available: return NSLocalizedString("In-stock", comment: "")
            case .stockout: return NSLocalizedString("Out-of-stock", comment: "")
            }
        }
        
        var color: Color {
            switch self {
            case .available: return .blue
            case .stockout: return .darkGray
            }
        }
    }
    
    let id: Int
    let brands: [Brand]
}
