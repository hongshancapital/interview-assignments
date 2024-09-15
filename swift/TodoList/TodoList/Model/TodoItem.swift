//
//  TodoItem.swift
//  TodoList
//
//  Created by 边边 on 2021/12/11.
//

import Foundation
import SwiftUI

struct TodoItem {
    var id:Int
    @State var itemText:String
    @State var completed:Bool
}
