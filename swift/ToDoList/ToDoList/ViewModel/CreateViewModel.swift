//
//  CreateViewModel.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/28.
//

import Foundation
import Combine
import SwiftUI

enum TodoField: Hashable {
    case modify, create
}

class CreateViewModel: ObservableObject {
    @Published var newTodoContent: String = ""
    @Published var newTodoTagId: Int64 = 1
    @Published var todoField: TodoField? = nil
    
    weak var modifyTodoModel: ToDoModel? = nil
}
