//
//  ViewModelState.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/10.
//

import Foundation

enum ViewModelState : String {
    case loading = "Loading"
    case loaded = "List"
    case noData = "No Data found"
    case editing = "Editing"
    case addingNew = "Add new..."
    case searching = "Searching..."
}
