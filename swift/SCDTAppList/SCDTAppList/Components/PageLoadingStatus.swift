//
//  PageLoadingStatus.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/16.
//

import SwiftUI
import UIKit

enum PageLoadingStatus{
    case first      ///first load first page
    case refresh    ///refresh data if it exists
    case loading    ///loading data
    case stop       ///after the data is loaded successfully and stop
    case finish     ///all data loaded
    case empty      ///the data of the first page loaded is empty
    case error      ///an exception occurred while loading the first page of data
    
    public static func == (lhs: Self, rhs: Self) -> Bool {
        if case .first = lhs,
            case .first = rhs {
            return true
        }else if case .refresh = lhs,
            case .refresh = rhs {
            return true
        }else if case .loading = lhs,
            case .loading = rhs {
            return true
        }else if case .stop = lhs,
            case .stop = rhs {
            return true
        }else if case .finish = lhs,
            case .finish = rhs {
            return true
        }else if case .empty = lhs,
            case .empty = rhs {
            return true
        }else if case .error = lhs,
            case .error = rhs {
            return true
        }
        return false
    }
    
    
    
}
