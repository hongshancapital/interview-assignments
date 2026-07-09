//
//  MockData.swift
//  SwiftUIAssignments
//
//  Created by zcj on 2023/6/5.
//

struct MockData {
    /// total count
    var total: Int
    ///  appModels
    var appModels: [AppModel]
}

extension MockData: Decodable {

    enum CodingKeys: String, CodingKey {
        case total = "resultCount"
        case appModels = "results"
    }
}
