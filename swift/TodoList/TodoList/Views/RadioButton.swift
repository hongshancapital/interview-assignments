//
//  RadioButton.swift
//  TodoList
//
//  Created by 边边 on 2021/12/11.
//

import Foundation
import SwiftUI

struct RadioButton: View {
    @Binding var checked: Bool
//    var btnHandle: ((Bool)->Void)

    var body: some View {
        Group{
            if checked {
                ZStack{
                    Circle()
                        .fill(Color.white)
                        .frame(width: 17, height: 17)
                        .overlay(Circle().stroke(Color.gray, lineWidth: 1))
                    Circle()
                        .fill(Color.gray)
                        .frame(width: 10, height: 10)
                }/*.onTapGesture {
                    self.checked = false
//                    self.btnHandle(false)
                }*/
            } else {
                Circle()
                    .fill(Color.white)
                    .frame(width: 17, height: 17)
                    .overlay(Circle().stroke(Color.gray, lineWidth: 1))
//                    .onTapGesture {
//                        self.checked = true
////                        self.btnHandle(true)
//                    }
            }
        }
    }
}
