//
//  WMTodoDetail.swift
//  WMSwiftUICode
//
//  Created by 王明民 on 2022/1/5.
//

import Foundation
import SwiftUI

struct WMTodoRow: View, Identifiable{

    var id = UUID()
    let todo : WMModel
    
    @State var message: String  = ""
    @EnvironmentObject var check: WMStateModel
    @EnvironmentObject var todoLists : WMModelData
    
    func wmRowTextColor() -> Color {
        check.select ? Color.gray : Color.black
    }

    var body: some View {
        
        HStack {
            WMCirclePoint(todo: todo)
                .padding(.leading, 15)
            
            ZStack (alignment: .leading) {
                Text(message)
                    .strikethrough(check.select, color: .gray)
                    .font(.system(size: 13))
                    .foregroundColor(Color.clear)
                
                TextField(
                    "something..." ,
                    text: $message,
                    onEditingChanged: { _ in print("changed") },
                    onCommit: {
                        print(message)
                        if message.count == 0 {
                            todoLists.deleteTodo(todo: todo)
                        } else {
                            todoLists.changeTheMessage(todo: todo, message)
                        }
                    }
                )
                    .font(.system(size: 13))
                    .foregroundColor(wmRowTextColor())
                    .padding(.leading, 0)
                    .allowsHitTesting(check.select==true ? false : true)

            }
            
            Spacer()
        }
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 10.0))

        
    }
    
    
}
