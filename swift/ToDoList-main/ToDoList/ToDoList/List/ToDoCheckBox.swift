//
//  ToDoCheckBox.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/28.
//

import Foundation
import SwiftUI

struct TodoCheckBox: View {
    let check: Bool
    var body: some View {
        ZStack {
            Circle()
                .fill(Color("checkbg"))
                .aspectRatio(1.0, contentMode: .fit)
                .frame(width: 20, height: 20, alignment: .center)
            Circle()
                .fill(Color("listbg"))
                .aspectRatio(1.0, contentMode: .fit)
                .frame(width: 16, height: 16, alignment: .center)
            let _ = Log.d("TodoCheckBox update \(check)")
            if check {
                Circle()
                    .fill(Color("checkbg"))
                    .aspectRatio(1.0, contentMode: .fit)
                    .frame(width: 9, height: 9, alignment: .center)
            }
        }
    }
}
