//
//  CoreDataError.swift
//  TodoList_IOS15
//
//  Created by Chr1s on 2021/11/5.
//

import Foundation

enum CoreDataError: Error, CustomStringConvertible {

    case addError(error: Error)
    case deleteError(error: Error)
    case updateError(error: Error)
    case unknown

    var description: String {
        switch self {
        case .addError(let error):
            return "Add Failed: \(error.localizedDescription)"
        case .deleteError(let error):
            return "Delete Failed: \(error.localizedDescription)"
        case .updateError(let error):
            return "Update Failed: \(error.localizedDescription)"
        case .unknown:
            return "_unknown error_"
        }
    }
}
