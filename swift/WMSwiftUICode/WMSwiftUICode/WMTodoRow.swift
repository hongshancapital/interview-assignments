//
//  WMTodoDetail.swift
//  WMSwiftUICode
//
//  Created by wangmm on 2022/1/5.
//

import Foundation
import SwiftUI

struct WMTodoRow: View, Identifiable{

    var id = UUID()
    let todo : WMModel
    
    @State var message: String  = ""
    @EnvironmentObject var check: WMStateModel
    @EnvironmentObject var todoLists : WMModelData

    func wmRowLineColor() -> Color {
        check.select ? Color.gray : Color.clear
    }
    
    func wmRowTextColor() -> Color {
        check.select ? Color.gray : Color.black
    }

    var body: some View {
        
        HStack {
            WMCirclePoint(todo: todo)
                .padding(.leading, 15)
            
            ZStack (alignment: .leading) {
                Text(message)
                    .font(.system(size: 13))
                    .foregroundColor(Color.clear)
                    .background(
                        Rectangle()
                            .fill(wmRowLineColor())
                            .frame(height: 1, alignment: .leading)
                    )
                
                TextField(
                    "something..." ,
                    text: $message,
                    onEditingChanged: { _ in print("changed") },
                    onCommit: {
                        print(message)
                        if message.count == 0 {
                            todoLists.deleteTodo(todo: todo)
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
