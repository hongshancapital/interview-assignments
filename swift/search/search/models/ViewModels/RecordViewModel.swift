//
//  RecordViewModel.swift
//  search
//
//  Created by bc on 2020/9/9.
//  Copyright © 2020 sc. All rights reserved.
//

import Foundation

struct RecordViewModel: Identifiable, Codable, Equatable {
    static func == (lhs: RecordViewModel, rhs: RecordViewModel) -> Bool {
        lhs.id == rhs.id
    }


    var record: Record

    init(record: Record){
        self.record = record
    }

    var id: String { record.id }
    var type: String { record.type }
    var brand: String { record.brand }
    var model: String { record.model }
    var price: String { record.price }
    var status: String { record.status.displayText }

}
