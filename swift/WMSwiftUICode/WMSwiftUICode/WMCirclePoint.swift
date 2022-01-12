/*
See LICENSE folder for this sample’s licensing information.

Abstract:
A view that clips an image to a circle and adds a stroke and shadow.
*/

import SwiftUI

struct WMCirclePoint: View {
    let todo : WMModel
    @EnvironmentObject var check: WMStateModel
    @EnvironmentObject var todoLists : WMModelData

    func changeSelect() {
        withAnimation (.linear(duration: 0.5)) {
            check.select.toggle()
            todoLists.changeTodoCheck(todo: todo)
        }

    }
    
    var body: some View {
        Circle()
            .fill(.white)
            .frame(width: 15)
            .overlay {
                let circle = Circle()
                    .stroke(.gray, lineWidth: 2)
                if (check.select) {
                    circle
                        .overlay(Circle().fill(.gray).padding(.all,3))
                }
                circle
            }
            .onTapGesture {
                changeSelect()
            }
    }
}
