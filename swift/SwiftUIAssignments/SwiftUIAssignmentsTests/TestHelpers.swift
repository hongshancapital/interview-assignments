//
//  TestHelpers.swift
//  SwiftUIAssignmentsTests
//
//  Created by zcj on 2023/6/7.
//

import Foundation

extension Result {
    var isSuccess: Bool {
        guard case .success = self else { return false }
        return true
    }

    var isFailure: Bool {
        !isSuccess
    }

    var success: Success? {
        guard case let .success(value) = self else { return nil }
        return value
    }

    var failure: Failure? {
        guard case let .failure(error) = self else { return nil }
        return error
    }
}

