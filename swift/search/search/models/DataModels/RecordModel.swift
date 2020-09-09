//
//  RecordModel.swift
//  search
//
//  Created by bc on 2020/9/9.
//  Copyright © 2020 sc. All rights reserved.
//

import Foundation

struct Record: Codable {
    enum MT: Int, Codable{
        case instock = 1, outofstock = 2
        var displayText:String{
            switch self {
            case .instock:
                return "In-stock"
            case .outofstock:
                return "Out-of-stock"
            }
        }
    }

    var type: String
    var brand: String
    var model: String
    var id: String
    var price: String
    var status: MT


}

#if DEBUG
extension Record{
    static func sampleString() -> String {
        return "[{\"id\":\"4410163C-5DED-4ABA-9C25-630F2B066F95\",\"type\":\"Vacuum\",\"brand\":\"Dyson\",\"model\":\"V11\",\"status\":1,\"price\":\"599.99\"},{\"id\":\"B8830104-5C7E-43FD-89C4-1FFBAFD27CA2\",\"type\":\"Vacuum\",\"brand\":\"Dyson\",\"model\":\"V10\",\"status\":2,\"price\":\"399.99\"},{\"id\":\"2323ED35-BDB1-4013-9820-08A1BEB5201C\",\"type\":\"Vacuum\",\"brand\":\"Dyson\",\"model\":\"V13\",\"status\":1,\"price\":\"599.99\"},{\"id\":\"0FC58C34-F695-4A06-A10F-9F63A96E7757\",\"type\":\"Hair Dryer\",\"brand\":\"Dyson\",\"model\":\"Supersonic\",\"status\":1,\"price\":\"399.99\"},{\"id\":\"34BE777D-614C-4C42-BC4F-003C46D26635\",\"type\":\"Hair Dryer\",\"brand\":\"Dyson\",\"model\":\"Supersonic Neo\",\"status\":1,\"price\":\"199.99\"}]"
    }
}
#endif
