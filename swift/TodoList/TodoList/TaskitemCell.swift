//
//  TaskitemView.swift
//  TodoList
//
//  Created by Huanrong on 11/2/21.
//

import SwiftUI

struct TaskitemCell: View {
    var itemModel: ItemsModel
    var modelData: ModelData
    @State var text: String = ""
    @GestureState var isDetectingLongGesture = false
    
    var body: some View {
        HStack {
            Image(itemModel.done ? "done" : "undone").resizable().frame(width: 20, height: 20)
            
            if itemModel.edit {
                TextField("", text: $text).padding(5)
            } else {
                Text(itemModel.name).strikethrough(itemModel.done, color: Color.black)
            }
        }
        
        // Press
        .gesture(
            LongPressGesture(minimumDuration: 0.5)
                .onEnded {_ in
                    self.text = self.itemModel.name
                    self.modelData.edit(item: self.itemModel)
                }
        )
        
        // Tap
        .gesture(
            LongPressGesture(minimumDuration: 0.0)
                .onEnded {_ in
                    guard self.itemModel.edit == false else {
                        return
                    }
                    self.modelData.click(item: self.itemModel)
        })
    }
}

struct TaskitemCell_Previews: PreviewProvider {
    static var sections = ModelData().sections
    static var previews: some View {
        TaskitemCell(itemModel: sections[0].items[0], modelData: ModelData())
    }
}
